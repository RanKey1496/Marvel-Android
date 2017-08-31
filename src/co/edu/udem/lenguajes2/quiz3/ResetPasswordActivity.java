package co.edu.udem.lenguajes2.quiz3;

import java.sql.SQLException;

import co.edu.udem.lenguajes2.quiz3.models.Users;

import com.j256.ormlite.dao.Dao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends DBActivity implements OnClickListener{

	EditText txtPass, txtReplyPass;
	Button btnReset;
	String userName = "";
	
	Dao<Users, Integer> userDao;
	
	Users user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);
		
		userName = getIntent().getStringExtra("userName");
		user = new Users();
		
		txtPass = (EditText) findViewById(R.id.txtResetPassword);
		txtReplyPass = (EditText) findViewById(R.id.txtReplyResetPassword);
		btnReset = (Button) findViewById(R.id.btnReset);
		btnReset.setOnClickListener(this);
		
		try {
			userDao = getDBHelper().getUserDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if (passwordEqual(txtPass, txtReplyPass)) {
			user.setName(userName);
			user.setPassword(txtPass.getText().toString());
			
			try {
				userDao.update(user);
				
				Toast.makeText(this, "Password reset", Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean passwordEqual(EditText pass1 , EditText pass2){
		if (pass1.getText().toString().equals(pass2.getText().toString())) {
			return true;
		}
		else {
			Toast.makeText(ResetPasswordActivity.this, "Passwords dont match", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}