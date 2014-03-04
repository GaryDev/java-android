package org.snow.wcfapp.soap.util;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.SoapObject;
import org.snow.wcfapp.soap.service.RetailService;

public class WebServiceUtil {
	
	public static SoapObject call(String methodName, KvmSerializable request) {
		return call(methodName, request, null);
	}
	
	public static SoapObject call(String methodName, KvmSerializable request, String sessionId) {
		RetailService service = new RetailService(methodName, request, sessionId);
		SoapObject result = null;
		try{
			result = service.loadResult();
		} catch (Exception e) {
			return null;
		}
		return result;
	}
	
	public static boolean validateResult(SoapObject result) {
		if(result != null) {
			if(result.getPropertyCount() > 0) {
				return true;
			}
		}
		return false;
	}

}
