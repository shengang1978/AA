package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CommentAsset")
public class CommentAsset {
	private int id;
	private String title;
	private String desc;
	private String snapshotUrl;
	private Integer assetTypeId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return desc;
	}
	public void setDescription(String description) {
		this.desc = description;
	}
	public String getSnapshotUrl() {
		return snapshotUrl;
	}
	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}
	public Integer getAssetTypeId() {
		return assetTypeId;
	}
	public void setAssetTypeId(Integer assetTypeId) {
		this.assetTypeId = assetTypeId;
	}
}
