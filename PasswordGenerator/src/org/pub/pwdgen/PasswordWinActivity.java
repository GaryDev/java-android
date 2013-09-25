package org.pub.pwdgen;

import java.util.ArrayList;
import java.util.List;

import org.pub.pwdgen.util.PasswordFactory;
import org.pub.pwdgen.util.PasswordRepository;
import org.pub.pwdgen.util.PasswordRepositorySQL;
import org.pub.pwdgen.vo.PasswordPolicy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordWinActivity extends Activity {

	private Spinner spPasswordLength; 
	private CheckBox cbxUpper;
	private CheckBox cbxLower;
	private CheckBox cbxDigit;
	private CheckBox cbxNonAlpha;
	private TextView txtPassword;
	private Button btnGenerate;
	private Button btnSave;	
	
	private PasswordRepository passwordRepository;
	
	private String newPassword = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_win);
				
		//passwordRepository = new PasswordRepositoryXML(Environment.getExternalStorageDirectory().getPath());
		passwordRepository = new PasswordRepositorySQL(this);
		
		txtPassword = (TextView) findViewById(R.id.txtPassword);
		addItemOnSpinner();
		addListenerOnButton();
		loadCurrentPassword();		
	}
	
	private void addItemOnSpinner() {
		spPasswordLength = (Spinner) findViewById(R.id.spinnerPwdLen);
		String[] lens = new String[] {"8", "9", "10", "11", "12"};
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lens);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spPasswordLength.setAdapter(dataAdapter);
	}
	
	private void addListenerOnButton() {
		btnGenerate = (Button) findViewById(R.id.btnGenerator);
		btnGenerate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PasswordPolicy policy = this.getSelectedPolicy(v);
				if(policy != null) {
					PasswordFactory passwordFactory = new PasswordFactory(policy);
					newPassword = passwordFactory.generatePassword();
					txtPassword.setText(newPassword);
					btnSave.setEnabled(true);
				}
			}
			
			private PasswordPolicy getSelectedPolicy(View v) {
				String length = spPasswordLength.getSelectedItem().toString();
				List<Integer> categories = this.getSelectedCategory();
				if(categories.size() < 3) {
					Toast.makeText(v.getContext(), "Please check at least 3 password categories.", Toast.LENGTH_SHORT).show();
					return null;
				}
				PasswordPolicy policy = new PasswordPolicy();
				policy.setPasswordLength(Integer.valueOf(length));
				policy.setPasswordCategory(categories);
				return policy;
			}
			
			private List<Integer> getSelectedCategory() {
				cbxUpper = (CheckBox) findViewById(R.id.cbxUpper);
				cbxLower = (CheckBox) findViewById(R.id.cbxLower);
				cbxDigit = (CheckBox) findViewById(R.id.cbxDigit);
				cbxNonAlpha = (CheckBox) findViewById(R.id.cbxNonAlpha);
				
				List<Integer> categories = new ArrayList<Integer>();
				if(cbxUpper.isChecked()) {
					categories.add(0);
				}
				if(cbxLower.isChecked()) {
					categories.add(1);
				}
				if(cbxDigit.isChecked()) {
					categories.add(2);
				}
				if(cbxNonAlpha.isChecked()) {
					categories.add(3);
				}
				return categories;
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
		txtPassword.setText(newPassword);
		btnSave.setEnabled(false);
	}

}
