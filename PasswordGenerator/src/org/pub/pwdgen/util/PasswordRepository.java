package org.pub.pwdgen.util;

public interface PasswordRepository {
	
	public String readPassword();
	
	public void savePassword(String newPass);

}
