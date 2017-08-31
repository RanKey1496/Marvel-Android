package co.edu.udem.lenguajes2.quiz3;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import co.edu.udem.lenguajes2.quiz3.models.Favorities;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddFavoritesActivity extends DBActivity implements OnClickListener{

	Button btnAdd, btnCancel;
	ImageView imgCharacter;
	TextView txtAdd;
	
	String name = "";
	String userName = "";
	String imgUrl = "";
	int comics = 0;
	int _id = 0;
	String description = "";
	String webUrl = "";
	
	Dao<Favorities, Integer> favoriteDao;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addfavorites);
		
		name = getIntent().getStringExtra("name");
		userName = getIntent().getStringExtra("userName");
		imgUrl = getIntent().getStringExtra("imgUrl");
		_id = getIntent().getIntExtra("_id", -1);
		comics = getIntent().getIntExtra("comics", -1);
		description = getIntent().getStringExtra("description");
		webUrl = getIntent().getStringExtra("webUrl");
		
		imgCharacter = (ImageView) findViewById(R.id.imgAdd);
		txtAdd = (TextView) findViewById(R.id.txtAreYouSure);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnCancel = (Button) findViewById(R.id.btnAddCancel);
		btnAdd.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		Picasso.with(this).load(imgUrl).resize(612, 612).into(imgCharacter);
		txtAdd.setText("You want to add "+name+" to the favorites list?");
		
		try {
			favoriteDao = getDBHelper().getFavoritiesDao();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnAdd:
			{
				Favorities favorite = new Favorities();
				favorite.setName(name);
				favorite.setUserName(userName);
				favorite.setId(_id);
				favorite.setImgUrl(imgUrl);
				favorite.setComics(comics);
				favorite.setDescription(description);
				favorite.setWebUrl(webUrl);
				try {
					favoriteDao.createIfNotExists(favorite);
					Toast.makeText(AddFavoritesActivity.this, "Character added to favorites"
							, Toast.LENGTH_SHORT).show();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case R.id.btnAddCancel:
			{
				finish();
				break;
			}
		}
	}
	
}