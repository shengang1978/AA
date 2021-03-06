package com.divx.service.domain.model;

// Generated 2015-6-23 12:26:37 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpShareUser generated by hbm2java
 */
public class DcpShareUser implements java.io.Serializable {

	private Integer id;
	private DcpShare dcpShare;
	private Integer userId;
	private Integer friendId;
	private Integer groupId;
	private Date createDate;
	private Date modifyDate;
	private Boolean status;

	public DcpShareUser() {
	}

	public DcpShareUser(DcpShare dcpShare) {
		this.dcpShare = dcpShare;
	}

	public DcpShareUser(DcpShare dcpShare, Integer userId, Integer friendId,
			Integer groupId, Date createDate, Date modifyDate, Boolean status) {
		this.dcpShare = dcpShare;
		this.userId = userId;
		this.friendId = friendId;
		this.groupId = groupId;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DcpShare getDcpShare() {
		return this.dcpShare;
	}

	public void setDcpShare(DcpShare dcpShare) {
		this.dcpShare = dcpShare;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFriendId() {
		return this.friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
