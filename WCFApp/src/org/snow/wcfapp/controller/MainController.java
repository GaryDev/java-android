package org.snow.wcfapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;
import org.snow.wcfapp.soap.object.KvmListString;
import org.snow.wcfapp.soap.response.LoginResponse;
import org.snow.wcfapp.soap.util.WebServiceUtil;

public class MainController {
	
	public static String openSession(String userCode, String computerName) {
		String sessionId = null;
		
		List<String> nameList = new ArrayList<String>();
		nameList.add("LoginId");
		nameList.add("ComputerName");
		
		List<String> valueList = new ArrayList<String>();
		valueList.add(userCode);
		valueList.add(computerName);
		
		SoapObject result = WebServiceUtil.call("OpenSession", new KvmListString(valueList, nameList));
		if(WebServiceUtil.validateResult(result)) {
			sessionId = result.getPropertyAsString(0);
		}
		return sessionId;
	}
	
	public static LoginResponse doLogin(String userCode, String password) {
		LoginResponse resp = new LoginResponse();
		
		String sessionId = openSession(userCode, null);
		if(sessionId != null) {
			resp.setSessionId(sessionId);
			
			List<String> nameList = new ArrayList<String>();
			nameList.add("UserName");
			nameList.add("Password");
			
			List<String> valueList = new ArrayList<String>();
			valueList.add(userCode);
			valueList.add(password);
			
			SoapObject result = WebServiceUtil.call("LoginOperator", new KvmListString(valueList, nameList), sessionId);
			if(WebServiceUtil.validateResult(result)) {
				SoapObject item = (SoapObject) result.getProperty(0);
				resp.setStaffId(String.valueOf(item.getPropertySafely("StaffId", null)));
				resp.setStaffCode(String.valueOf(item.getPropertySafely("StaffCode", null)));
				resp.setStaffName(String.valueOf(item.getPropertySafely("StaffName", null)));
			}
		}
		return resp;
	}

}
