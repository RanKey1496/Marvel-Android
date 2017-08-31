package co.edu.udem.lenguajes2.quiz3;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import co.edu.udem.lenguajes2.quiz3.adapters.CharacterAdapter;
import co.edu.udem.lenguajes2.quiz3.models.Characters;

public class SearchActivity extends DBActivity implements OnClickListener, OnCheckedChangeListener, OnItemClickListener, OnItemLongClickListener{
	
	AsyncHttpClient httpClient;
	
	EditText txtOption;
	RadioButton rbName, rbFirst;
	ListView lvSearch;
	Button btnSearch;
	RadioGroup rg;
	
	List<Characters> listCharacters = new ArrayList<Characters>();
	CharacterAdapter adapter;
	
	String userName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		userName = getIntent().getStringExtra("userName");
		
		txtOption = (EditText) findViewById(R.id.txtOption);
		rbFirst = (RadioButton) findViewById(R.id.rbLetter);
		rbName = (RadioButton) findViewById(R.id.rbName);
		btnSearch = (Button) findViewById(R.id.btnSearch2);
		lvSearch = (ListView) findViewById(R.id.lvSearch);
		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(this);
		btnSearch.setOnClickListener(this);
		
		httpClient = new AsyncHttpClient();
		lvSearch.setOnItemClickListener(this);
		lvSearch.setOnItemLongClickListener(this);
	}
	
	private void loadData(String param, String value) {
		
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
		params.put(param, value);
		params.put("limit", 50);
		
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
							
							listCharacters.add(character);	
						}
						loadList();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int status, Header[] headers, byte[] response, Throwable t) {
				
				Toast.makeText(SearchActivity.this, "Error while API proccess: " + t.getMessage()
						, Toast.LENGTH_SHORT).show();				
			}
		} );
	}
	
	private void loadList() {
		
		adapter = new CharacterAdapter(SearchActivity.this, R.layout.character_list, listCharacters);
		adapter.notifyDataSetChanged();
		
		lvSearch.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSearch2) {
			if (rbFirst.isChecked()) {
				listCharacters.clear();
				loadData("nameStartsWith", txtOption.getText().toString());
			}
			if (rbName.isChecked()) {
				listCharacters.clear();
				loadData("name", txtOption.getText().toString());
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == R.id.rbName) {
			txtOption.setFilters(new InputFilter[] {new InputFilter.LengthFilter(100)});
		}
		if (checkedId == R.id.rbLetter) {
			txtOption.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
		}
	}

	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		Characters selectedCharacter = listCharacters.get(position);
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
		intent.putExtra("description", description);
		intent.putExtra("comics", comics);
		intent.putExtra("imgUrl", imgurl);
		intent.putExtra("webUrl", webUrl);
		startActivity(intent);
		
		return true;
	}
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Characters selectedCharacter = listCharacters.get(position);
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