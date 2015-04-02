package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RegisterOption")
public class RegisterOption {
	public enum eRegisterType
	{
		username,
		email,
		mobile
	}
	private eRegisterType registerType;
	private String username;
	private String password;
	private String repassword;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public eRegisterType getRegisterType() {
		return registerType;
	}
	public void setRegisterType(eRegisterType registerType) {
		this.registerType = registerType;
	}
}
