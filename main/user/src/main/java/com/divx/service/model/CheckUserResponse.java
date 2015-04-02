package com.divx.service.model;

//import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CheckUserResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckUserResponse")
public class CheckUserResponse extends ServiceResponse {
	
	private int userId;
	private String username;
	private String token;
//	private Date expireDate;
	private int ownId;
	private int deviceType;
	private String deviceGuid;
	
	private String deviceUniqueId;
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
//	public Date getExpireDate() {
//		return expireDate;
//	}
//	public void setExpireDate(Date expireDate) {
//		this.expireDate = expireDate;
//	}
	public int getOwnId() {
		return ownId;
	}
	public void setOwnId(int ownId) {
		this.ownId = ownId;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceGuid() {
		return deviceGuid;
	}
	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}
	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}
	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}
}
