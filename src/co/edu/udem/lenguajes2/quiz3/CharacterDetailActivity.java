package co.edu.udem.lenguajes2.quiz3;

import com.loopj.android.http.AsyncHttpClient;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterDetailActivity extends DBActivity{

	AsyncHttpClient httpClient;
	
	TextView txtId, txtName, txtDescription, txtUrl;
	ImageView imgDetail;
	
	String name = "";
	int id = 0;
	String description = "";
	String webPage = "";
	String imgUrl = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		name = getIntent().getStringExtra("name");
		id = getIntent().getIntExtra("id", -1);
		description = getIntent().getStringExtra("description");
		webPage = getIntent().getStringExtra("webUrl");
		imgUrl = getIntent().getStringExtra("imgUrl");
		
		txtId = (TextView) findViewById(R.id.txtDetailId);
		txtName = (TextView) findViewById(R.id.txtDetailName);
		txtDescription = (TextView) findViewById(R.id.txtDetailDescription);
		txtUrl = (TextView) findViewById(R.id.txtDetailURL);
		imgDetail = (ImageView) findViewById(R.id.imgDetail);
		
		txtId.setText("Id: "+String.valueOf(id));
		txtName.setText("Name: "+name);
		try {
			txtDescription.setText("Description: "+description);
		} catch (Exception e) {
			txtDescription.setText("Description not available");
		}
		txtUrl.setText("More detail at: "+webPage);
		Picasso.with(CharacterDetailActivity.this).load(imgUrl).resize(516, 516).into(imgDetail);
	}
}
