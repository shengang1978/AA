package com.divx.service.model;

import java.util.Date;

public class Share {
	private int id;
	private User user;
	private String title;
	private String desc;
	private String snapshotUrl;
	private MediaBaseType.eContentType contentType;
	private String smileUrl;
	private int mediaId;
	private int groupId;
	private Date date;
	private eStatus status;
	private int likeCount;
	private int commentCount;
	private int likePoint;	//点赞给的Point总数
	private int lessonId;
	public enum eStatus
	{
		publishing,	// publishing the media (transcoding and uploading the asset)
		done		// done.		
	}
	public int getShareId() {
		return id;
	}
	public void setShareId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getDescription() {
		return desc;
	}
	public void setDescription(String description) {
		this.desc = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSnapshotUrl() {
		return snapshotUrl;
	}
	public void setSnapshotUrl(String snapshotUrl) {
		this.snapshotUrl = snapshotUrl;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public eStatus getStatus() {
		return status;
	}
	public void setStatus(eStatus status) {
		this.status = status;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public MediaBaseType.eContentType getContentType() {
		return contentType;
	}
	public void setContentType(MediaBaseType.eContentType contentType) {
		this.contentType = contentType;
	}

	public String getSmileUrl() {
		return smileUrl;
	}
	public void setSmileUrl(String smileUrl) {
		this.smileUrl = smileUrl;
	}
	public int getLikePoint() {
		return likePoint;
	}
	public void setLikePoint(int likePoint) {
		this.likePoint = likePoint;
	}
	public int getLessonId() {
		return lessonId;
	}
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}

}
