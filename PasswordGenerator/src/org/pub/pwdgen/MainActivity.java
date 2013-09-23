package org.pub.pwdgen;

import org.pub.pwdgen.util.PasswordFactory;
import org.pub.pwdgen.util.PasswordRepository;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView txtPassword;
	private Button btnGenerate;
	private Button btnSave;
	
	private PasswordFactory passwordFactory;
	private PasswordRepository passwordRepository;
	
	private String newPassword = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtPassword = (TextView) findViewById(R.id.txtPassword);
		
		passwordFactory = new PasswordFactory();
		passwordRepository = new PasswordRepository(Environment.getExternalStorageDirectory().getPath());
		
		loadCurrentPassword();
		
		btnGenerate = (Button) findViewById(R.id.btnGenerator);
		btnGenerate.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				newPassword = passwordFactory.generatePassword();
				txtPassword.setText(newPassword);
				btnSave.setEnabled(true);
			}
		});
		
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				passwordRepository.savePassword(newPassword);
				btnSave.setEnabled(false);
			}
		});
	}
	
	private void loadCurrentPassword() {
		newPassword = passwordRepository.readPassword();
		if(newPassword == null) {
			newPassword = passwordFactory.generatePassword();
		} else {
			btnSave.setEnabled(false);
		}
		txtPassword.setText(newPassword);
	}

}
