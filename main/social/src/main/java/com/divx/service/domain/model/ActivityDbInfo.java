package com.divx.service.domain.model;

public class ActivityDbInfo {
	public ActivityDbInfo(){}
	public ActivityDbInfo(int shareId, int mediaId, int likePoint, int likeCount, int commentCount)
	{
		this.shareId = shareId;
		this.mediaId = mediaId;
		this.likeCount = likeCount;
		this.likePoint = likePoint;
		this.commentCount = commentCount;
	}
	
	private int mediaId;
	private int shareId;
	private int likeCount;
	private int commentCount;
	private int likePoint;
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public int getShareId() {
		return shareId;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
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
	public int getLikePoint() {
		return likePoint;
	}
	public void setLikePoint(int likePoint) {
		this.likePoint = likePoint;
	}
	
}
