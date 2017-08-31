package co.edu.ude.lenguajes2.quiz3.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import co.edu.udem.lenguajes2.quiz3.models.Characters;
import co.edu.udem.lenguajes2.quiz3.models.Favorities;
import co.edu.udem.lenguajes2.quiz3.models.Users;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper{

	 private static final String DATABASE_NAME = "Marvel.db";
	 private static final int DATABASE_VERSION = 1;
	 
	 private Dao<Users, Integer> UsersDao;
	 private Dao<Characters, Integer> CharactersDao;
	 private Dao<Favorities, Integer> FavoritiesDao;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		
		try {
			TableUtils.createTableIfNotExists(connectionSource, Users.class);
			TableUtils.createTableIfNotExists(connectionSource, Characters.class);
			TableUtils.createTableIfNotExists(connectionSource, Favorities.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}


	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, 
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Users.class, true);
			TableUtils.dropTable(connectionSource, Characters.class, true);
			TableUtils.dropTable(connectionSource, Favorities.class, true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Dao<Users, Integer> getUserDao() throws SQLException{
		
		if(UsersDao==null){
			UsersDao = getDao(Users.class);
		}
		return UsersDao;
	}
	
	public Dao<Characters, Integer> getCharacterDao() throws SQLException{
		
		if(CharactersDao==null){
			CharactersDao = getDao(Characters.class);
		}
		return CharactersDao;
	}
	
	public Dao<Favorities, Integer> getFavoritiesDao() throws SQLException{
		
		if(FavoritiesDao==null){
			FavoritiesDao = getDao(Favorities.class);
		}
		return FavoritiesDao;
	}
}
