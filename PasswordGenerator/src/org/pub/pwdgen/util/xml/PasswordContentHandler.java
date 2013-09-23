package org.pub.pwdgen.util.xml;

import java.util.ArrayList;
import java.util.List;

import org.pub.pwdgen.vo.Password;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class PasswordContentHandler extends DefaultHandler {
	
	private List<Password> passwords;
	private Password password = null;
	
	
	public List<Password> getPasswordList() {
		return passwords;
	}
	
	@Override
	public void startDocument() throws SAXException {
		passwords = new ArrayList<Password>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if("password".equals(localName)) {
			password = new Password();
			password.setValue(attributes.getValue("value"));
			password.setTimeStamp(attributes.getValue("timestamp"));
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if("password".equals(localName) && password != null) {
			passwords.add(password);
			password = null;
		}
	}

}
