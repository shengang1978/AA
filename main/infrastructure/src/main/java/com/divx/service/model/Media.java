package com.divx.service.model;

import java.util.Date;

//import java.util.Date;

public class Media extends MediaBase{
	private int userId;
	private String username;
	private String nickname;

	private MediaBaseType.eMediaStatus status;
	private String createDate;
	private String snapshotUrl;
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
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSnapshotUrl() {
		return snapshotUrl;
	}
	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}
	public MediaBaseType.eMediaStatus getStatus() {
		return status;
	}
	public void setStatus(MediaBaseType.eMediaStatus status) {
		this.status = status;
	}
	
	public void setStatus(int nStatus)
	{
		this.status = MediaBaseType.eMediaStatus.values()[nStatus];
	}
	
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
