package com.divx.service.model;

import java.util.Date;

//import java.util.Date;

public class Media extends MediaBase{
	private int userId;

	//private Date expireDate;
	private MediaBaseType.eMediaStatus status;
	private Date createDate;
	private String snapshotUrl;
	// Yingyueguan elements
	private String recordUrl;
	private String picBookUrl;
	private String configUrl;
	
	public String getRecordUrl() {
		return recordUrl;
	}
	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}
	public String getPicBookUrl() {
		return picBookUrl;
	}
	public void setPicBookUrl(String picBookUrl) {
		this.picBookUrl = picBookUrl;
	}
	public String getConfigUrl() {
		return configUrl;
	}
	public void setConfigUrl(String configUrl) {
		this.configUrl = configUrl;
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
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
