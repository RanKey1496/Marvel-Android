package co.edu.udem.lenguajes2.quiz3.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import co.edu.udem.lenguajes2.quiz3.R;
import co.edu.udem.lenguajes2.quiz3.models.Favorities;

import com.squareup.picasso.Picasso;

public class UserFavoritesAdapter extends ArrayAdapter<Favorities> {

	Context context;
	int resource;
	List<Favorities> FavortiesList;
	
	ViewHolder holder;

	public UserFavoritesAdapter(Context context, int resource, List<Favorities> FavortiesList) {
		super(context, resource, FavortiesList);

		this.context = context;
		this.resource = resource;
		this.FavortiesList = FavortiesList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			
			holder = new ViewHolder();
		
			convertView = View.inflate(context, resource, null);
			
			holder.txtCharacter = (TextView)convertView.findViewById(R.id.txtFavoritelist);	
			holder.txtComics = (TextView)convertView.findViewById(R.id.txtFavoriteComicList);	
			holder.imgCharacter = (ImageView)convertView.findViewById(R.id.imgFavorteList);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Favorities favorite = FavortiesList.get(position);
		
		if(favorite!=null){
			holder.txtCharacter.setText(favorite.getUserName().toString());
			holder.txtComics.setText("Comic: " + String.valueOf(favorite.getComics()));
			Picasso.with(context).load(favorite.getImgUrl()).resize(128, 110).placeholder(R.drawable.image_not_available).into(holder.imgCharacter);
		}
		return convertView;
	}
	
	public class ViewHolder{
		
		public TextView txtCharacter;
		public TextView txtComics;
		public ImageView imgCharacter;
	}
	
}