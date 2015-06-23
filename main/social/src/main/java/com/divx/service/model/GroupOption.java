package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GroupOption")
public class GroupOption {
	private String title;
	private String desc;

	public String getDescription() {
		return desc;
	}
	public void setDescription(String description) {
		this.desc = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
