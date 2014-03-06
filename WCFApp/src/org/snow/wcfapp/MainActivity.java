package org.snow.wcfapp;

import org.snow.wcfapp.controller.MainController;
import org.snow.wcfapp.soap.response.LoginResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	private EditText txtID;
	private EditText txtPwd;
	private Button btnLogin;
	
	private Intent mMainTabIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtID = (EditText) findViewById(R.id.txtUserID);
		txtPwd = (EditText) findViewById(R.id.txtPwd);
		
		txtID.setText("001009");
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userCode = txtID.getText().toString();
				String password = txtPwd.getText().toString();
				new LoginAsyncTask().execute(userCode, password);
			}
		});
		
		mMainTabIntent = new Intent(this, MainTabActivity.class);
	}
	
	private void UpdateView(LoginResponse result) {
		if(result != null) {
			WCFApp app = (WCFApp) getApplicationContext();
			app.setSessionId(result.getSessionId());
			mMainTabIntent.putExtra("StaffName", result.getStaffName());
			this.startActivity(mMainTabIntent);
		}
	}
	
	private class LoginAsyncTask extends AsyncTask<String, Integer, LoginResponse> {
		
		private ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(MainActivity.this, "Connnecting...", "Please wait...");
		}
		
		@Override
		protected LoginResponse doInBackground(String... params) {
			return MainController.doLogin(params[0], params[1]);
		}
		
		@Override
		protected void onPostExecute(LoginResponse result) {
			dialog.dismiss();
			UpdateView(result);
		}
	}

}
