package org.pub.pwdgen.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class PasswordProviderMetaData {

	public static final String AUTHORITY = "org.pub.pwdgen.db.PasswordProvider";
	
	public static final String DATABASE_NAME = "password.db";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME_PASSWORD = "password_history";
	
	private PasswordProviderMetaData() {
	}
	
	public static final class PasswordTableMetaData implements BaseColumns {
		
		private PasswordTableMetaData() {
		}
		
		public static final String TABLE_NAME = TABLE_NAME_PASSWORD;
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/password-history");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.pwdgen.password";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.pwdgen.password";
		
		public static final String DEFAULT_SORT_ORDER = "timestamp DESC";
		
		public static final String PASSWORD_VALUE = "password_value";
		
		public static final String PASSWORD_TIMESTAMP = "timestamp";
	}
	
	

}
