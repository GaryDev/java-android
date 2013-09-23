package org.pub.pwdgen.vo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Password implements Comparable<Password> {
	
	private String value;
	private Date timeStamp;
	
	private DateFormat formater = null;
	
	public Password() {
		formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getTimeStamp() {
		return formater.format(timeStamp);
	}
	
	public void setTimeStamp(String timeStamp) {
		try {
			this.timeStamp = formater.parse(timeStamp);
		} catch (ParseException e) {
			this.timeStamp = null;
		};
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public int compareTo(Password another) {
		return another.getTimeStamp().compareTo(this.getTimeStamp());
	}

	
}
