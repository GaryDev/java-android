package org.snow.wcfapp;

import android.app.Application;

public class WCFApp extends Application {
	
	private String SessionId;

	public String getSessionId() {
		return SessionId;
	}

	public void setSessionId(String sessionId) {
		SessionId = sessionId;
	}

}
