package co.edu.udem.lenguajes2.quiz3;

import java.sql.SQLException;
import java.util.List;

import co.edu.udem.lenguajes2.quiz3.adapters.UserFavoritesAdapter;
import co.edu.udem.lenguajes2.quiz3.models.Characters;
import co.edu.udem.lenguajes2.quiz3.models.Favorities;

import com.j256.ormlite.dao.Dao;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FavoritiesActivity extends DBActivity implements OnItemClickListener{

	String userName = "";
	ListView lvFavorites;
	Dao<Favorities, Integer> favoritesDao;
	List<Characters> listFavorities;
	List<Favorities> aux;
	UserFavoritesAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);
				
		userName = getIntent().getStringExtra("userName");
		
		lvFavorites = (ListView) findViewById(R.id.lvFavorites);
		lvFavorites.setOnItemClickListener(this);
		
		try {
			favoritesDao = getDBHelper().getFavoritiesDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		loadUser();
	}

	private void loadUser(){
		try {
			aux = favoritesDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
			Toast.makeText(FavoritiesActivity.this, "Error while load data", Toast.LENGTH_SHORT).show();
		}
		
		adapter = new UserFavoritesAdapter(this, R.layout.activity_favorites, aux);
		adapter.notifyDataSetChanged();
		lvFavorites.setAdapter(adapter);
		
		Toast.makeText(FavoritiesActivity.this, String.valueOf(aux.size()), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Characters selectedCharacter = listFavorities.get(position);
		String name = selectedCharacter.getName();
		int Id = selectedCharacter.getId();
		String description = selectedCharacter.getDescription();
		String webUrl = selectedCharacter.getWebUrl();
		String imgUrl = selectedCharacter.getImgurl();
		
		Intent intent = new Intent(this, CharacterDetailActivity.class);
		intent.putExtra("name", name);
		intent.putExtra("id", Id);
		intent.putExtra("description", description);
		intent.putExtra("webUrl", webUrl);
		intent.putExtra("imgUrl", imgUrl);
		startActivity(intent);
	}

}
