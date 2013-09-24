package org.pub.pwdgen.db;

import java.util.HashMap;
import java.util.Map;

import org.pub.pwdgen.db.PasswordProviderMetaData.PasswordTableMetaData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class PasswordProvider extends ContentProvider {
	
	private static final String TAG = "PasswordProvider";
	
	private static Map<String, String> mPasswordProjectionMap;
	
	static {
		mPasswordProjectionMap = new HashMap<String, String>();
		mPasswordProjectionMap.put(PasswordTableMetaData._ID, PasswordTableMetaData._ID);
		mPasswordProjectionMap.put(PasswordTableMetaData.PASSWORD_VALUE, PasswordTableMetaData.PASSWORD_VALUE);
		mPasswordProjectionMap.put(PasswordTableMetaData.PASSWORD_TIMESTAMP, PasswordTableMetaData.PASSWORD_TIMESTAMP);
	}
	
	private static final UriMatcher uUriMatcher;
	private static final int COLLECTION_URI_INDICATOR = 1;
	private static final int SINGLE_URI_INDICATOR = 2;
	
	static {
		uUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uUriMatcher.addURI(PasswordProviderMetaData.AUTHORITY, "password-history", COLLECTION_URI_INDICATOR);
		uUriMatcher.addURI(PasswordProviderMetaData.AUTHORITY, "password-history/#", SINGLE_URI_INDICATOR);
	}
	
	private static class DataBaseHelper extends SQLiteOpenHelper {
		
		public DataBaseHelper(Context context) {
			super(context, PasswordProviderMetaData.DATABASE_NAME, null,
					PasswordProviderMetaData.DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "DataBaseHelper.onCreate called");
			db.execSQL("CREATE TABLE " + PasswordTableMetaData.TABLE_NAME + " ( " 
					+ PasswordTableMetaData._ID + " INTEGER PRIMARY KEY, "
					+ PasswordTableMetaData.PASSWORD_VALUE + " TEXT, "
					+ PasswordTableMetaData.PASSWORD_TIMESTAMP + " TimeStamp NOT NULL DEFAULT (datetime('now', 'localtime'))"
					+ ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d(TAG, "DataBaseHelper.onUpgrade called");
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data.");
			db.execSQL("DROP TABLE IF EXISTS " + PasswordTableMetaData.TABLE_NAME);
			onCreate(db);
		}
		
	}
	
	private DataBaseHelper dbHelper;

	@Override
	public boolean onCreate() {
		Log.d(TAG, "PasswordProvider.onCreate called");
		dbHelper = new DataBaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		switch (uUriMatcher.match(uri)) {
		case COLLECTION_URI_INDICATOR:
			qb.setTables(PasswordTableMetaData.TABLE_NAME);
			qb.setProjectionMap(mPasswordProjectionMap);
			break;
		case SINGLE_URI_INDICATOR:
			qb.setTables(PasswordTableMetaData.TABLE_NAME);
			qb.setProjectionMap(mPasswordProjectionMap);
			qb.appendWhere(PasswordTableMetaData._ID + "=" + uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		String orderBy = TextUtils.isEmpty(sortOrder) ? PasswordTableMetaData.DEFAULT_SORT_ORDER : sortOrder;
		
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (uUriMatcher.match(uri)) {
		case COLLECTION_URI_INDICATOR:
			return PasswordTableMetaData.CONTENT_TYPE;
		case SINGLE_URI_INDICATOR:
			return PasswordTableMetaData.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		if(uUriMatcher.match(uri) != COLLECTION_URI_INDICATOR) {
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		ContentValues contents = values == null ? new ContentValues() : new ContentValues(values);
		if(!contents.containsKey(PasswordTableMetaData.PASSWORD_VALUE)) {
			throw new SQLException("Password value is required.");
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insert(PasswordTableMetaData.TABLE_NAME, PasswordTableMetaData.PASSWORD_VALUE, contents);
		if(rowId > 0) {
			Uri insertedUri = ContentUris.withAppendedId(PasswordTableMetaData.CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(insertedUri, null);
			return insertedUri;
		}
		
		throw new SQLException("Fail to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (uUriMatcher.match(uri)) {
		case COLLECTION_URI_INDICATOR:
			count = db.delete(PasswordTableMetaData.TABLE_NAME, selection,
					selectionArgs);
			break;
		case SINGLE_URI_INDICATOR:
			String rowId = uri.getPathSegments().get(1);
			count = db.delete(
					PasswordTableMetaData.TABLE_NAME,
					PasswordTableMetaData._ID
							+ "="
							+ rowId
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (uUriMatcher.match(uri)) {
		case COLLECTION_URI_INDICATOR:
			count = db.update(PasswordTableMetaData.TABLE_NAME, values,
					selection, selectionArgs);
			break;
		case SINGLE_URI_INDICATOR:
			String rowId = uri.getPathSegments().get(1);
			count = db.update(
					PasswordTableMetaData.TABLE_NAME,
					values,
					PasswordTableMetaData._ID
							+ "="
							+ rowId
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
