package org.pub.pwdgen.vo;

import java.util.List;

public class PasswordPolicy {
	
	private Integer passwordLength;
	private List<Integer> passwordCategory;
	
	public Integer getPasswordLength() {
		return passwordLength;
	}
	public void setPasswordLength(Integer passwordLength) {
		this.passwordLength = passwordLength;
	}
	public List<Integer> getPasswordCategory() {
		return passwordCategory;
	}
	public void setPasswordCategory(List<Integer> passwordCategory) {
		this.passwordCategory = passwordCategory;
	}
	
}
