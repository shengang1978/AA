package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserOption")
public class UsernameOption {
	public enum OptionType
	{
		username,
		email,
		mobile
	}
	private OptionType type;
	private String value;
	
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
	
	
	
}
