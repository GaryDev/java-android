package org.snow.wcfapp.soap.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.ksoap2.HeaderProperty;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.snow.wcfapp.soap.object.KvmListBase;
import org.xmlpull.v1.XmlPullParserException;

public class RetailService implements SoapService {
	
	protected static final String NAMESPACE = "http://snow.org/retailservice/2014/02/27/";
	protected static final String URL = "http://10.0.2.2:82/service.svc";
	protected static final String SOAP_ACTION = NAMESPACE + "RetailService/";
	
	private KvmSerializable request;
	private String methodName;
	private String sessionId;
	
	public RetailService() {
		
	}
	
	public RetailService(String methodName, KvmSerializable request) {
		this.methodName = methodName;
		this.request = request;
	}
	
	public RetailService(String methodName, KvmSerializable request, String sessionId) {
		this(methodName, request);
		this.sessionId = sessionId;
	}

	public void setRequest(KvmSerializable request) {
		this.request = request;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public SoapObject loadResult() {
		SoapObject soap = new SoapObject(NAMESPACE, methodName);
		
		PropertyInfo propertyInfo = new PropertyInfo();
		propertyInfo.setName("request");
		propertyInfo.setValue(this.request);
		propertyInfo.setType(this.request.getClass());
		soap.addProperty(propertyInfo);
		
		SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelop.dotNet = true;
		envelop.setOutputSoapObject(soap);
		envelop.addMapping(KvmListBase.NAMESPACE, methodName + "Input", this.request.getClass());
		
		List<HeaderProperty> header = new ArrayList<HeaderProperty>();
		if(sessionId != null) {
			header.add(new HeaderProperty("SessionId", sessionId));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
			header.add(new HeaderProperty("ClientTime", dateFormat.format(new Date())));
		}
		
		HttpTransportSE transport = new HttpTransportSE(URL);
		transport.debug = true;
		
		try {
			transport.call(SOAP_ACTION + methodName, envelop, header);
			SoapObject result = (SoapObject) envelop.getResponse();
			return result;
		} catch (HttpResponseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
