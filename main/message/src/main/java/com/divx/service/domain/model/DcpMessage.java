package com.divx.service.domain.model;

// Generated 2014-11-3 18:28:48 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpMessage generated by hbm2java
 */
public class DcpMessage implements java.io.Serializable {

	private Integer messageId;
	private int messagetype;
	private String content;
	private int status;
	private Date datecreated;
	private int senderId;
	private Boolean deleted;
	private int messageCategory;
	private boolean issysmessage;

	public DcpMessage() {
	}

	public DcpMessage(int messagetype, int status, Date datecreated,
			int senderId, int messageCategory, boolean issysmessage) {
		this.messagetype = messagetype;
		this.status = status;
		this.datecreated = datecreated;
		this.senderId = senderId;
		this.messageCategory = messageCategory;
		this.issysmessage = issysmessage;
	}

	public DcpMessage(int messagetype, String content, int status,
			Date datecreated, int senderId, Boolean deleted,
			int messageCategory, boolean issysmessage) {
		this.messagetype = messagetype;
		this.content = content;
		this.status = status;
		this.datecreated = datecreated;
		this.senderId = senderId;
		this.deleted = deleted;
		this.messageCategory = messageCategory;
		this.issysmessage = issysmessage;
	}

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public int getMessagetype() {
		return this.messagetype;
	}

	public void setMessagetype(int messagetype) {
		this.messagetype = messagetype;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public int getMessageCategory() {
		return this.messageCategory;
	}

	public void setMessageCategory(int messageCategory) {
		this.messageCategory = messageCategory;
	}

	public boolean isIssysmessage() {
		return this.issysmessage;
	}

	public void setIssysmessage(boolean issysmessage) {
		this.issysmessage = issysmessage;
	}

}
