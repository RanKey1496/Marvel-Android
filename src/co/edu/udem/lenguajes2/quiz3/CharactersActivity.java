package co.edu.udem.lenguajes2.quiz3;

import java.sql.SQLException;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.udem.lenguajes2.quiz3.adapters.CharacterAdapter;
import co.edu.udem.lenguajes2.quiz3.models.Characters;

import com.j256.ormlite.dao.Dao;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CharactersActivity extends DBActivity implements OnItemClickListener, OnClickListener, OnItemLongClickListener{

	AsyncHttpClient httpClient;
	
	List<Characters> charactersList;
	
	ListView lvCharacters;
	Button btnMoar;
	
	Dao<Characters, Integer> charactersDao;
	CharacterAdapter adapter;
	
	int cont = 20;
	String userName = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_characters);
		
		userName = getIntent().getStringExtra("userName");
		
		lvCharacters = (ListView)findViewById(R.id.lvCharacters);
		lvCharacters.setOnItemClickListener(this);
		lvCharacters.setOnItemLongClickListener(this);
		btnMoar = (Button) findViewById(R.id.btnMoar);
		btnMoar.setOnClickListener(this);
		
		httpClient = new AsyncHttpClient();
		
		try {
			charactersDao = getDBHelper().getCharacterDao();
			loadData();			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		loadList();
	}

	private void loadList() {
		try {
			charactersList = charactersDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		adapter = new CharacterAdapter(CharactersActivity.this, R.layout.character_list, charactersList);
		adapter.notifyDataSetChanged();
		
		lvCharacters.setAdapter(adapter);
	}
	
	private void loadData() {
		
		String ts = String.valueOf(System.currentTimeMillis());
		
		String publicKey= getString(R.string.public_key);
		
		String privateKey= getString(R.string.private_key);
		
		String hash = Helper.md5(ts+privateKey+publicKey);
		
		String base_url = getString(R.string.base_url);
		
		String comicsEndpoint = base_url + "/v1/public/characters"; 
		
		RequestParams params = new RequestParams();
		
		params.put("ts", ts);
		params.put("apikey", publicKey);
		params.put("hash", hash);
		params.put("limit", cont);
		
		httpClient.get(comicsEndpoint, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int status, Header[] headers, byte[] response) {
				String responseString = new String(response);
				
				try {
					JSONObject jsonResponse = new JSONObject(responseString);

					String responseStatus = jsonResponse.getString("status");
					
					if(responseStatus.equals("Ok")){
					
						JSONObject jsonData = jsonResponse.getJSONObject("data");
						
						JSONArray jsonResult = jsonData.getJSONArray("results");
						
						for (int i = 0; i <jsonResult.length();i++) {
							JSONObject current = jsonResult.getJSONObject(i);
							JSONObject numbercomics = current.getJSONObject("comics");
							JSONArray urls = current.getJSONArray("urls");
							JSONObject detail = urls.getJSONObject(0);
							int comics = numbercomics.getInt("available");
							
							Characters character = new Characters();
							
							character.setId(current.getInt("id"));
							character.setName(current.getString("name"));
							character.setComics(comics);
							character.setDescription(current.getString("description"));
							character.setWebUrl(detail.getString("url"));
							
							String imgUrl;
							try {
								JSONObject thumbnail = current.getJSONObject("thumbnail");
								imgUrl = thumbnail.getString("path");
								imgUrl += "."+thumbnail.getString("extension");
							} catch (Exception e) {
								e.printStackTrace();
								imgUrl = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg";
							}
							
							character.setImgurl(imgUrl);
							
							try {
								charactersDao.createOrUpdate(character);
							} catch (SQLException e) {
								e.printStackTrace();
							}	
						}
						loadList();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int status, Header[] headers, byte[] response, Throwable t) {
				
				Toast.makeText(CharactersActivity.this, "Error while API proccess: " + t.getMessage()
						, Toast.LENGTH_SHORT).show();				
			}
		} );
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Characters selectedCharacter = charactersList.get(position);
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

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnMoar) {
			cont += 20;
			loadData();
			loadList();
			Toast.makeText(this, String.valueOf(cont), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		Characters selectedCharacter = charactersList.get(position);
		String name = selectedCharacter.getName();
		String imgurl = selectedCharacter.getImgurl();
		int comics = selectedCharacter.getComics();
		int _id = selectedCharacter.getId();
		String description = selectedCharacter.getDescription();
		String webUrl = selectedCharacter.getWebUrl();
		
		Intent intent = new Intent(this, AddFavoritesActivity.class);
		intent.putExtra("_id", _id);
		intent.putExtra("name", name);
		intent.putExtra("userName", userName);
		intent.putExtra("comics", comics);
		intent.putExtra("imgUrl", imgurl);
		intent.putExtra("description", description);
		intent.putExtra("webUrl", webUrl);
		startActivity(intent);
		
		return true;
	}
}
