package co.edu.udem.lenguajes2.quiz3.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Characters {
	
	public static final String ID="_id";
	public static final String CHARACTERNAME="characterName";
	public static final String CHARACTERIMGURL="imgCharacterUrl";
	public static final String NUMBEROFCOMICS="comics";
	public static final String DESCRIPTION="description";
	public static final String WEBURL="url";
	
	@DatabaseField(columnName=ID, generatedId=true, allowGeneratedIdInsert=true)
	private int id;
	@DatabaseField(columnName=CHARACTERNAME)
	private String name;
	@DatabaseField(columnName=CHARACTERIMGURL)
	private String imgurl;
	@DatabaseField(columnName=NUMBEROFCOMICS)
	private int comics;
	@DatabaseField(columnName=DESCRIPTION)
	private String description;
	@DatabaseField(columnName=WEBURL)
	private String webUrl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public int getComics() {
		return comics;
	}
	public void setComics(int comics) {
		this.comics = comics;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	
}