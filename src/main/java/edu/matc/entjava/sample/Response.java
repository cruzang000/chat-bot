package edu.matc.entjava.sample;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response{

	@JsonProperty("distance")
	private double distance;

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("rating")
	private double rating;

	@JsonProperty("coordinates")
	private Coordinates coordinates;

	@JsonProperty("review_count")
	private int reviewCount;

	@JsonProperty("transactions")
	private List<Object> transactions;

	@JsonProperty("url")
	private String url;

	@JsonProperty("display_phone")
	private String displayPhone;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("price")
	private String price;

	@JsonProperty("name")
	private String name;

	@JsonProperty("alias")
	private String alias;

	@JsonProperty("location")
	private Location location;

	@JsonProperty("id")
	private String id;

	@JsonProperty("categories")
	private List<CategoriesItem> categories;

	@JsonProperty("is_closed")
	private boolean isClosed;

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setRating(double rating){
		this.rating = rating;
	}

	public double getRating(){
		return rating;
	}

	public void setCoordinates(Coordinates coordinates){
		this.coordinates = coordinates;
	}

	public Coordinates getCoordinates(){
		return coordinates;
	}

	public void setReviewCount(int reviewCount){
		this.reviewCount = reviewCount;
	}

	public int getReviewCount(){
		return reviewCount;
	}

	public void setTransactions(List<Object> transactions){
		this.transactions = transactions;
	}

	public List<Object> getTransactions(){
		return transactions;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setDisplayPhone(String displayPhone){
		this.displayPhone = displayPhone;
	}

	public String getDisplayPhone(){
		return displayPhone;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getAlias(){
		return alias;
	}

	public void setLocation(Location location){
		this.location = location;
	}

	public Location getLocation(){
		return location;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setCategories(List<CategoriesItem> categories){
		this.categories = categories;
	}

	public List<CategoriesItem> getCategories(){
		return categories;
	}

	public void setIsClosed(boolean isClosed){
		this.isClosed = isClosed;
	}

	public boolean isIsClosed(){
		return isClosed;
	}
}