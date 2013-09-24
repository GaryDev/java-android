package org.pub.pwdgen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.pub.pwdgen.util.xml.PasswordXmlParser;
import org.pub.pwdgen.util.xml.PasswordXmlParserImpl;
import org.pub.pwdgen.vo.Password;


public class PasswordRepositoryXML implements PasswordRepository {
	
	private PasswordXmlParser xmlParser;
	private String path;
	
	public PasswordRepositoryXML(String path) {
		xmlParser = new PasswordXmlParserImpl();
		this.path = path;
	}

	public void savePassword(String newPass) {
		List<Password> passwords = this.getPasswordList(false);
		if(passwords == null) {
			passwords = new ArrayList<Password>();
		}
		Password password = new Password();
		password.setValue(newPass);
		password.setTimeStamp(new Date());
		passwords.add(password);
		try {
			OutputStream outputStream = new FileOutputStream(getStorageFile());
			xmlParser.writeXML(passwords, outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String readPassword() {
		List<Password> passwords = this.getPasswordList();
		if(passwords != null && passwords.size() > 0) {
			return passwords.get(0).getValue();
		}
		return null;
	}
	
	public List<Password> getPasswordList() {
		return getPasswordList(true);
	}
	
	public List<Password> getPasswordList(boolean needSort) {
		List<Password> passwords = null;
		try {
			InputStream inputStream = new FileInputStream(getStorageFile());
			passwords = xmlParser.readXML(inputStream);
			if(needSort) {
				if(passwords != null && passwords.size() > 1) {
					Collections.sort(passwords);
				}
			}			
		} catch (Exception e) {

		}
		return passwords;
	}
	
	public String[] getPasswordHistory(int tops) {
		String[] latestPass = new String[tops];
		List<Password> passwords = this.getPasswordList();
		if(passwords != null && passwords.size() > 0) {
			int loops = tops < passwords.size() ? tops : passwords.size();
			for (int i = 0; i < loops; i++) {
				latestPass[i] = passwords.get(i).getValue();
			}
		}
		return latestPass;
	}
	
	private File getStorageFile() {
		try {
			File dir = new File(path + "/org/pub/pwdgen");
			if(!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(path + "/org/pub/pwdgen/passwords.xml");
			if(!file.exists()) {
				file.createNewFile();
			}
			return file;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
