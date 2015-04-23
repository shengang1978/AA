package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SysMessage")
public class SysMessage {
	public enum eSysMessageType
	{
		ToAll,
		ToPerson,
		ToGroup
	}
	
	private eSysMessageType messageType;
	private String content;
	private int targetId;
	private int senderId;
	//private int senderId;
	private int messageCategory;
	public eSysMessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(eSysMessageType messageType) {
		this.messageType = messageType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
//	public int getSenderId() {
//		return senderId;
//	}
//	public void setSenderId(int senderId) {
//		this.senderId = senderId;
//	}
	public int getMessageCategory() {
		return messageCategory;
	}
	public void setMessageCategory(int messageCategory) {
		this.messageCategory = messageCategory;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
}
