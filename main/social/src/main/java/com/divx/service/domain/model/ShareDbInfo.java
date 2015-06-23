package com.divx.service.domain.model;

public class ShareDbInfo {
	public ShareDbInfo(){}
	public ShareDbInfo(DcpShare share, DcpShareUser user, int commentCount, int likeCount, int likePoint)
	{
		dcpShare = share;
		dcpShareUser = user;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
		this.likePoint = likePoint;
	}
	private DcpShare dcpShare;
	private DcpShareUser dcpShareUser;
	private int likePoint;
	private int likeCount;
	private int commentCount;
	public DcpShare getDcpShare() {
		return dcpShare;
	}
	public void setDcpShare(DcpShare dcpShare) {
		this.dcpShare = dcpShare;
	}
	public int getLikePoint() {
		return likePoint;
	}
	public void setLikePoint(int likePoint) {
		this.likePoint = likePoint;
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
	public DcpShareUser getDcpShareUser() {
		return dcpShareUser;
	}
	public void setDcpShareUser(DcpShareUser dcpShareUser) {
		this.dcpShareUser = dcpShareUser;
	}
	
	
}
