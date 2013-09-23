package org.pub.pwdgen.util.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.pub.pwdgen.vo.Password;

public interface PasswordXmlParser {
	
	public List<Password> readXML(InputStream in) throws Exception;
	
	public void writeXML(List<Password> passwords, OutputStream out);

}