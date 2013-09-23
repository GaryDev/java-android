package org.pub.pwdgen.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.pub.pwdgen.vo.Password;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class PasswordXmlParserImpl implements PasswordXmlParser {

	@Override
	public List<Password> readXML(InputStream in) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		XMLReader reader = parser.getXMLReader();
		PasswordContentHandler handler = new PasswordContentHandler(); 
		reader.setContentHandler(handler);
		reader.parse(new InputSource(in));
		return handler.getPasswordList();
	}

	@Override
	public void writeXML(List<Password> passwords, OutputStream out) {
		XmlSerializer serializer = Xml.newSerializer();
		try {
			serializer.setOutput(out, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag(null, "passwords");
			if(passwords != null && passwords.size() > 0) {
				for (Password password : passwords) {
					serializer.startTag(null, "password");
					serializer.attribute(null, "value", password.getValue());
					serializer.attribute(null, "timestamp", password.getTimeStamp());
					serializer.endTag(null, "password");
				}
			}
			serializer.endTag(null, "passwords");
			serializer.endDocument();
			out.flush();
			out.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
