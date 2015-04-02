package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RegisterDeviceOption")
public class RegisterDeviceOption {
	private String deviceGuid;
	private String username;
	private String nickname;
	private String photourl;
	private String deviceType;
	public String getDeviceGuid() {
		return deviceGuid;
	}
	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
}
