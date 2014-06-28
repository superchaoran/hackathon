package com.amazon.hackathon.contactshare.utils;

public class User {
	
	private String username;
	private String imageUrl;
	private String id;
	public User(String username,String imageUrl){
		this.username = username;
		this.imageUrl = imageUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
