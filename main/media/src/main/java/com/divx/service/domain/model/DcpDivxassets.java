package com.divx.service.domain.model;

// Generated 2015-6-1 16:15:49 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpDivxassets generated by hbm2java
 */
public class DcpDivxassets implements java.io.Serializable {

	private Integer assetsId;
	private int mediaId;
	private int originalassetId;
	private String location;
	private Date datecreated;
	private Date datemodified;
	private int videoformat;
	private int videosolution;
	private boolean canstreaming;
	private boolean candownload;
	private int status;

	public DcpDivxassets() {
	}

	public DcpDivxassets(int mediaId, int originalassetId, Date datecreated,
			Date datemodified, int videoformat, int videosolution,
			boolean canstreaming, boolean candownload, int status) {
		this.mediaId = mediaId;
		this.originalassetId = originalassetId;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.videoformat = videoformat;
		this.videosolution = videosolution;
		this.canstreaming = canstreaming;
		this.candownload = candownload;
		this.status = status;
	}

	public DcpDivxassets(int mediaId, int originalassetId, String location,
			Date datecreated, Date datemodified, int videoformat,
			int videosolution, boolean canstreaming, boolean candownload,
			int status) {
		this.mediaId = mediaId;
		this.originalassetId = originalassetId;
		this.location = location;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.videoformat = videoformat;
		this.videosolution = videosolution;
		this.canstreaming = canstreaming;
		this.candownload = candownload;
		this.status = status;
	}

	public Integer getAssetsId() {
		return this.assetsId;
	}

	public void setAssetsId(Integer assetsId) {
		this.assetsId = assetsId;
	}

	public int getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public int getOriginalassetId() {
		return this.originalassetId;
	}

	public void setOriginalassetId(int originalassetId) {
		this.originalassetId = originalassetId;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDatemodified() {
		return this.datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}

	public int getVideoformat() {
		return this.videoformat;
	}

	public void setVideoformat(int videoformat) {
		this.videoformat = videoformat;
	}

	public int getVideosolution() {
		return this.videosolution;
	}

	public void setVideosolution(int videosolution) {
		this.videosolution = videosolution;
	}

	public boolean isCanstreaming() {
		return this.canstreaming;
	}

	public void setCanstreaming(boolean canstreaming) {
		this.canstreaming = canstreaming;
	}

	public boolean isCandownload() {
		return this.candownload;
	}

	public void setCandownload(boolean candownload) {
		this.candownload = candownload;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
