package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FileUpload")
public class FileUpload {
	
	private int totalSize;
	private int uploadPos;
	private int userId;
	private int mediaId;
	private MediaBaseType.eContentType contentType;
	private MediaBaseType.eFileType	fileType;
	private String filename;
	private int lessonId;
	private String contentSettings;
	
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getUploadPos() {
		return uploadPos;
	}
	public void setUploadPos(int uploadPos) {
		this.uploadPos = uploadPos;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public MediaBaseType.eContentType getContentType() {
		return contentType;
	}
	public void setContentType(MediaBaseType.eContentType contentType) {
		this.contentType = contentType;
	}
	public MediaBaseType.eFileType getFileType() {
		return fileType;
	}
	public void setFileType(MediaBaseType.eFileType fileType) {
		this.fileType = fileType;
	}
	public int getLessonId() {
		return lessonId;
	}
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	public String getContentSettings() {
		return contentSettings;
	}
	public void setContentSettings(String contentSettings) {
		this.contentSettings = contentSettings;
	}
}
