package co.edu.udem.lenguajes2.quiz3.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Users {
	
	public static final String NAME="userName";
	public static final String PASSWORD="password";
	
	@DatabaseField(id=true, columnName=NAME)
	private String userName;
	@DatabaseField(columnName=PASSWORD)
	private String password;
	
	public String getName() {
		return userName;
	}
	public void setName(String name) {
		this.userName = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}