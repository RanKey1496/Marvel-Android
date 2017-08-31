package co.edu.udem.lenguajes2.quiz3;

import java.sql.SQLException;
import java.util.List;

import co.edu.udem.lenguajes2.quiz3.models.Users;

import com.j256.ormlite.dao.Dao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends DBActivity implements OnClickListener{
	
	EditText txtUser, txtPassword, txtReplyPassword;
	Button btnRegister, btnCancel;
	
	List<Users> listUsers;
	Dao<Users, Integer> userDao;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		try {
			userDao = getDBHelper().getUserDao();
			loadUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		txtUser = (EditText) findViewById(R.id.txtRegisterUser);
		txtPassword = (EditText) findViewById(R.id.txtRegisterPassword);
		txtReplyPassword = (EditText) findViewById(R.id.txtRegisterReplyPassword);
		
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		
		btnRegister.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}
	
	private void loadUser(){
		try {
			listUsers = userDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean userExits(String user){
		
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).getName().toLowerCase().equals(user.toLowerCase())) {
				Toast.makeText(RegisterActivity.this, "User already exits", Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;
	}
	
	private boolean passwordEqual(EditText pass1 , EditText pass2){
		if (pass1.getText().toString().equals(pass2.getText().toString())) {
			return true;
		}
		else {
			Toast.makeText(RegisterActivity.this, "Passwords dont match", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	private boolean passwordContains(EditText password){
		boolean Lower = false, Upper = false, Number = false;
		char[] pass = password.getText().toString().toCharArray();
		
		for (int i = 0; i < pass.length; i++) {
			if (Character.isLowerCase(pass[i])) {
				Lower = true;
			}
			if (Character.isUpperCase(pass[i])) {
				Upper = true;
			}
			if (Character.isDigit(pass[i])) {
				Number = true;
			}
		}
		
		if (Number && Lower && Upper) {
			return true;
		}
		else {
			Toast.makeText(RegisterActivity.this, "Password must have Number, Lower and Upper character",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnRegister:
			{
				if (txtUser.getText().toString().length() >= 5) {
					if (passwordEqual(txtPassword, txtReplyPassword)) {
						if (passwordContains(txtPassword)) {
							if (txtPassword.getText().length() >= 8) {
								if (!userExits(txtUser.getText().toString())) {
									Users user = new Users();
									user.setName(txtUser.getText().toString());
									String pass = Helper.md5(txtPassword.getText().toString());
									user.setPassword(pass);
									try {
										userDao.create(user);
										listUsers = userDao.queryForAll();
										Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
										txtUser.setText("");
										txtPassword.setText("");
										txtReplyPassword.setText("");
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							}
							else {
								Toast.makeText(RegisterActivity.this, "The password must be 8 characters", Toast.LENGTH_LONG).show();
							}
						}
					}
				}
				else {
					Toast.makeText(RegisterActivity.this, "The user must be 5 characters", Toast.LENGTH_LONG).show();
				}
				break;
			}	
			case R.id.btnCancel:
			{
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
				break;
			}
		}
	}
}