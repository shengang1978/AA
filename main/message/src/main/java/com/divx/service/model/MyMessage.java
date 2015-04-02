package com.divx.service.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MyMessage")
public class MyMessage {

	private int messageId;
	private int messageCategory;	// infrastructure->MessageCategory
	
	private String content;
	private Date date;
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getMessageCategory() {
		return messageCategory;
	}
	public void setMessageCategory(int messageCategory) {
		this.messageCategory = messageCategory;
	}
	
}
