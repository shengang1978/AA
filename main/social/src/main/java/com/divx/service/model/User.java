package com.divx.service.model;

public class User
{
	private int userId;
	private String username;
	private String nickname;
	private String photoUrl;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/*private String role;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}*/
	
}
