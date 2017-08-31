package co.edu.udem.lenguajes2.quiz3.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Favorities {
	
	public static final String USERNAME="userName";
	public static final String CHARACTERNAME="name";
	public static final String ID="_id";
	public static final String IMGURL="imgUrl";
	public static final String NUMBEROFCOMICS="comics";
	public static final String DESCRIPTION="description";
	public static final String WEBURL="url";
	
	@DatabaseField(columnName="aux", generatedId=true)
	private int aux;
	@DatabaseField(columnName=ID)
	private int id;
	@DatabaseField(columnName=CHARACTERNAME)
	private String name;
	@DatabaseField(columnName=USERNAME)
	private String userName;
	@DatabaseField(columnName=IMGURL)
	private String imgUrl;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public int getComics() {
		return comics;
	}
	public void setComics(int comics) {
		this.comics = comics;
	}
	public int getAux() {
		return aux;
	}
	public void setAux(int aux) {
		this.aux = aux;
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