package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

public class StoryPlayStat {
	private int mediaId;
	private String title;
	private String desc;
	private String snapshoturl;
	private String date;
	private User user;
	private int playCount;
	
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getPlayCount() {
		return playCount;
	}
	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}
	public String getSnapshoturl() {
		return snapshoturl;
	}
	public void setSnapshoturl(String snapshoturl) {
		this.snapshoturl = snapshoturl;
	}
}
