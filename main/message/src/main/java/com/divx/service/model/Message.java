package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Message")
public class Message {
	public enum eMessageType
	{
		ToPerson,		//Message to person/friend
		ToGroup,		//Message to the group
		ToMyFriends,	//Message to all my friends
	}
	
	private eMessageType messageType;
	private int	targetId;
	private String	content;
//	private int messageCategory;
	//private int senderId;
	public eMessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(eMessageType messageType) {
		this.messageType = messageType;
	}
	public int getTargetId() {
		return targetId;
	}
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
//	public int getMessageCategory() {
//		return messageCategory;
//	}
//	public void setMessageCategory(int messageCategory) {
//		this.messageCategory = messageCategory;
//	}

//	public int getSenderId() {
//		return senderId;
//	}
//	public void setSenderId(int senderId) {
//		this.senderId = senderId;
//	}
}
