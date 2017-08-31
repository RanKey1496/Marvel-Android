package co.edu.udem.lenguajes2.quiz3;

import java.sql.SQLException;
import java.util.List;

import co.edu.udem.lenguajes2.quiz3.models.Users;

import com.j256.ormlite.dao.Dao;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends DBActivity implements OnClickListener{

	Button btnRegister, btnJoin;
	EditText txtUser, txtPassword;
	
	List<Users> listUsers;
	Dao<Users, Integer> userDao;
	String userName = "", password = "";
	
	int cont = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try {
			userDao = getDBHelper().getUserDao();
			loadUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		txtUser = (EditText) findViewById(R.id.txtUser);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		btnRegister = (Button) findViewById(R.id.btnMainRegister);
		btnJoin = (Button) findViewById(R.id.btnMainLogin);
		btnJoin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadUser(){
		try {
			listUsers = userDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.btnMainLogin:
			{
				if (userExtis()) {
					if (cont < 3) {
						if (isCorrect()) {
							Users user = new Users();
							user.setName(userName);
							user.setPassword(password);
							try {
								userDao.createOrUpdate(user);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							loadUser();
							Intent intent = new Intent(this, MenuActivity.class);
							intent.putExtra("userName", userName);
							startActivity(intent);
						}
						else {
							cont++;
						}
					}
					else {
						Intent intent = new Intent(this, ResetPasswordActivity.class);
						intent.putExtra("userName", userName);
						startActivity(intent);
					}
				}
				
				break;
			}
			case R.id.btnMainRegister:
			{
				Intent intent = new Intent(this, RegisterActivity.class);
				startActivityForResult(intent, 2);
				break;
			}
		}
	}
	
	private boolean isCorrect(){
		String pass = Helper.md5(txtPassword.getText().toString());
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).getName().equals(txtUser.getText().toString())) {
				if (listUsers.get(i).getPassword().equals(pass)) {
					userName = listUsers.get(i).getName();
					password = listUsers.get(i).getPassword();
					return true;
				}
			}
		}
		
		Toast.makeText(MainActivity.this, "User or Password are incorrect", Toast.LENGTH_LONG).show();
		txtPassword.setText("");
		return false;
	}

	private boolean userExtis(){
		for (int i = 0; i < listUsers.size(); i++) {
			if (listUsers.get(i).getName().equals(txtUser.getText().toString())) {
				return true;
			}
		}
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
        case 2:
            if (resultCode == RESULT_OK) {
                txtUser.setText("");
                txtPassword.setText("");
                try {
        			userDao = getDBHelper().getUserDao();
        			loadUser();
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
            }
           break;
        }
   }
}