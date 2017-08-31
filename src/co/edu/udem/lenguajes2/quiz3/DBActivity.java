package co.edu.udem.lenguajes2.quiz3;

import android.app.Activity;

import co.edu.ude.lenguajes2.quiz3.database.DBHelper;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public abstract class DBActivity extends Activity{
	
	private DBHelper dbHelper;
	
	protected DBHelper getDBHelper(){
		if(dbHelper == null){
			dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
		}		
		return dbHelper;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();		
		if(dbHelper!=null){		
			OpenHelperManager.releaseHelper();
			dbHelper= null;
		}		
	}
}