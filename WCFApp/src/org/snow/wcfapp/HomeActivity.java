package org.snow.wcfapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView view = new TextView(this);
		view.setText(((WCFApp)getApplicationContext()).getSessionId());
		setContentView(view);
	}

}
