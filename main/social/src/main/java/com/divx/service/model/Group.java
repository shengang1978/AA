package com.divx.service.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Group")
public class Group {
	private int id;
	private String title;
	private String description;
	private Date createDate;
	private String photoUrl;
	private int userCount;
	private int assetCount;
	
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return id;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String desc)
	{
		description = desc;
	}
	public String getDescription()
	{
		return description;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public int getUserCount() {
		return userCount;
	}
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	public int getAssetCount() {
		return assetCount;
	}
	public void setAssetCount(int assetCount) {
		this.assetCount = assetCount;
	}
}
