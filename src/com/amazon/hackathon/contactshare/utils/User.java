package com.amazon.hackathon.contactshare.utils;

public class User {
	
	private String username;
	private String imageUrl;
	private String userId;
	/*
	public User(String username,String imageUrl){
		this.username = username;
		this.imageUrl = imageUrl;
	}*/
	
	public User(String username,String imageUrl,String userId){
		this.username = username;
		this.imageUrl = imageUrl;
		this.userId = userId;
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
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
