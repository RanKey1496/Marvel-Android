package co.edu.udem.lenguajes2.quiz3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener{

	Button btnCharacters, btnFavorites, btnSearch;
	
	String userName = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		userName = getIntent().getStringExtra("userName");
		
		btnCharacters = (Button) findViewById(R.id.btnCharacters);
		btnFavorites = (Button) findViewById(R.id.btnFavorites);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		
		btnCharacters.setOnClickListener(this);
		btnFavorites.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnCharacters:
			{
				Intent intent = new Intent(this, CharactersActivity.class);
				intent.putExtra("userName", userName);
				startActivity(intent);
				break;
			}
			case R.id.btnFavorites:
			{
				Intent intent = new Intent(this, FavoritiesActivity.class);
				intent.putExtra("userName", userName);
				startActivity(intent);
				break;
			}
			case R.id.btnSearch:
			{
				Intent intent = new Intent(this, SearchActivity.class);
				intent.putExtra("userName", userName);
				startActivity(intent);
				break;
			}
		}
	}
}
