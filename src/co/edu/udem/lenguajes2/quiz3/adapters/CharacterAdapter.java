package co.edu.udem.lenguajes2.quiz3.adapters;

import java.util.List;

import com.squareup.picasso.Picasso;

import co.edu.udem.lenguajes2.quiz3.R;
import co.edu.udem.lenguajes2.quiz3.models.Characters;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterAdapter extends ArrayAdapter<Characters> {

	Context context;
	int resource;
	List<Characters> CharacterList;
	
	ViewHolder holder;

	public CharacterAdapter(Context context, int resource, List<Characters> CharacterList) {
		super(context, resource, CharacterList);

		this.context = context;
		this.resource = resource;
		this.CharacterList = CharacterList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			
			holder = new ViewHolder();
		
			convertView = View.inflate(context, resource, null);
			
			holder.txtCharacter = (TextView)convertView.findViewById(co.edu.udem.lenguajes2.quiz3.R.id.txtCharacterlist);	
			holder.txtComics = (TextView)convertView.findViewById(co.edu.udem.lenguajes2.quiz3.R.id.txtComicList);	
			holder.imgCharacter = (ImageView)convertView.findViewById(co.edu.udem.lenguajes2.quiz3.R.id.imgCharacterList);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		Characters character = CharacterList.get(position);
		
		if(character!=null){
			holder.txtCharacter.setText(String.valueOf(character.getName()));
			holder.txtComics.setText("Comic: " + String.valueOf(character.getComics()));
			Picasso.with(context).load(character.getImgurl()).resize(128, 110).placeholder(R.drawable.image_not_available).into(holder.imgCharacter);
		}
		
		return convertView;
	}
	
	public class ViewHolder{
		
		public TextView txtCharacter;
		public TextView txtComics;
		public ImageView imgCharacter;
	}
	
}
