package org.pub.pwdgen.util;

import java.util.ArrayList;
import java.util.List;

import org.pub.pwdgen.db.PasswordProviderMetaData.PasswordTableMetaData;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class PasswordRepositorySQL implements PasswordRepository {

	private ContentResolver resolver;
	
	public PasswordRepositorySQL(Context context) {
		this.resolver = context.getContentResolver();
	}
	
	@Override
	public String readPassword() {
		List<String> passwordList = new ArrayList<String>();
		
		Cursor c = resolver.query(PasswordTableMetaData.CONTENT_URI, 
				new String[]{PasswordTableMetaData.PASSWORD_VALUE}, 
				null, 
				null, 
				null);
		int indexPasswordValue = c.getColumnIndex(PasswordTableMetaData.PASSWORD_VALUE);
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			String value = c.getString(indexPasswordValue);
			passwordList.add(value);
		}
		c.close();
		
		if(passwordList.size() > 0) {
			return passwordList.get(0);
		}
		
		return null;
	}

	@Override
	public void savePassword(String newPass) {
		ContentValues contents = new ContentValues();
		contents.put(PasswordTableMetaData.PASSWORD_VALUE, newPass);
		resolver.insert(PasswordTableMetaData.CONTENT_URI, contents);
	}

}
