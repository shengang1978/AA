package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserOption")
public class UserOption {
	public enum OptionType
	{
		username,
		email,
		mobile
	}
	private OptionType type;
	private String value;
	private String password;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public OptionType getType() {
		return type;
	}
	public void setType(OptionType type) {
		this.type = type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
