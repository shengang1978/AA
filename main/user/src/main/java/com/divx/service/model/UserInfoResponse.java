package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "UserInfoResponse")
public class UserInfoResponse extends ServiceResponse {
	private int userId;
	private String username;
	private String email;
	private String mobile;
	private String nickname;
	private String photourl;
	private Organization organization;
	public enum RegisterType
	{
		username,
		email,
		mobile
	}
	private RegisterType registerType;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public RegisterType getRegisterType() {
		return registerType;
	}
	public void setRegisterType(RegisterType registerType) {
		this.registerType = registerType;
	}
}
