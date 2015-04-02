package com.divx.service.model;

public class MediaStatus {
	private int mediaId;
	private MediaBaseType.eMediaStatus status;
	private int percent;
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public int getStatus() {
		return status.ordinal();
	}
	public void setStatus(int status) {
		this.status = MediaBaseType.eMediaStatus.values()[status];
	}
	
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
}
