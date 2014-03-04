package org.snow.wcfapp;

import org.snow.wcfapp.controller.MainController;
import org.snow.wcfapp.soap.response.LoginResponse;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EditText txtID;
	private EditText txtPwd;
	private TextView lblInfo;
	private Button btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtID = (EditText) findViewById(R.id.txtUserID);
		txtPwd = (EditText) findViewById(R.id.txtPwd);
		lblInfo = (TextView) findViewById(R.id.lblInfo);
		
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String userCode = txtID.getText().toString();
				String password = txtPwd.getText().toString();
				new LoginAsyncTask().execute(userCode, password);
			}
		});
	}
	
	private void UpdateView(LoginResponse result) {
		if(result != null) {
			this.setTitle(result.getSessionId());
			String info = String.format("Staff: %s/%s/%s", 
					result.getStaffId(), result.getStaffCode(), result.getStaffName());
			lblInfo.setText(info);
		}
	}
	
	private class LoginAsyncTask extends AsyncTask<String, Integer, LoginResponse> {
		@Override
		protected LoginResponse doInBackground(String... params) {
			return MainController.doLogin(params[0], params[1]);
		}
		
		@Override
		protected void onPostExecute(LoginResponse result) {
			UpdateView(result);
		}
	}

}
