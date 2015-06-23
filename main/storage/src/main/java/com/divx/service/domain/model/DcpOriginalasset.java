package com.divx.service.domain.model;

// Generated 2015-5-22 20:38:14 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpOriginalasset generated by hbm2java
 */
public class DcpOriginalasset implements java.io.Serializable {

	private Integer originalassetId;
	private int mediaId;
	private String fileurl;
	private Date datecreated;
	private Date datemodified;
	private int totalsize;
	private int uploadpos;
	private int status;
	private boolean completed;
	private boolean deleted;
	private String filename;
	private int attempts;
	private boolean processed;
	private String errorMessage;
	private Integer contenttype;
	private String sharejson;
	private String v2gjson;
	private Integer filetype;
	private String contentSettings;
	private Integer lessonid;

	public DcpOriginalasset() {
	}

	public DcpOriginalasset(int mediaId, String fileurl, Date datecreated,
			Date datemodified, int totalsize, int uploadpos, int status,
			boolean completed, boolean deleted, int attempts, boolean processed) {
		this.mediaId = mediaId;
		this.fileurl = fileurl;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.totalsize = totalsize;
		this.uploadpos = uploadpos;
		this.status = status;
		this.completed = completed;
		this.deleted = deleted;
		this.attempts = attempts;
		this.processed = processed;
	}

	public DcpOriginalasset(int mediaId, String fileurl, Date datecreated,
			Date datemodified, int totalsize, int uploadpos, int status,
			boolean completed, boolean deleted, String filename, int attempts,
			boolean processed, String errorMessage, Integer contenttype,
			String sharejson, String v2gjson, Integer filetype,
			String contentSettings, Integer lessonid) {
		this.mediaId = mediaId;
		this.fileurl = fileurl;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.totalsize = totalsize;
		this.uploadpos = uploadpos;
		this.status = status;
		this.completed = completed;
		this.deleted = deleted;
		this.filename = filename;
		this.attempts = attempts;
		this.processed = processed;
		this.errorMessage = errorMessage;
		this.contenttype = contenttype;
		this.sharejson = sharejson;
		this.v2gjson = v2gjson;
		this.filetype = filetype;
		this.contentSettings = contentSettings;
		this.lessonid = lessonid;
	}

	public Integer getOriginalassetId() {
		return this.originalassetId;
	}

	public void setOriginalassetId(Integer originalassetId) {
		this.originalassetId = originalassetId;
	}

	public int getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public String getFileurl() {
		return this.fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
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

	public int getTotalsize() {
		return this.totalsize;
	}

	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}

	public int getUploadpos() {
		return this.uploadpos;
	}

	public void setUploadpos(int uploadpos) {
		this.uploadpos = uploadpos;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isCompleted() {
		return this.completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getAttempts() {
		return this.attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public boolean isProcessed() {
		return this.processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getContenttype() {
		return this.contenttype;
	}

	public void setContenttype(Integer contenttype) {
		this.contenttype = contenttype;
	}

	public String getSharejson() {
		return this.sharejson;
	}

	public void setSharejson(String sharejson) {
		this.sharejson = sharejson;
	}

	public String getV2gjson() {
		return this.v2gjson;
	}

	public void setV2gjson(String v2gjson) {
		this.v2gjson = v2gjson;
	}

	public Integer getFiletype() {
		return this.filetype;
	}

	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}

	public String getContentSettings() {
		return this.contentSettings;
	}

	public void setContentSettings(String contentSettings) {
		this.contentSettings = contentSettings;
	}

	public Integer getLessonid() {
		return this.lessonid;
	}

	public void setLessonid(Integer lessonid) {
		this.lessonid = lessonid;
	}

}
