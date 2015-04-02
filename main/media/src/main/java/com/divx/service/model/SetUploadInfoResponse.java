package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PublishResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublishResponse")
public class SetUploadInfoResponse extends ServiceResponse {
	private int publishResponseCode;
	private String publishResponseMessage;
	public int getPublishResponseCode() {
		return publishResponseCode;
	}
	public void setPublishResponseCode(int publishResponseCode) {
		this.publishResponseCode = publishResponseCode;
	}
	public String getPublishResponseMessage() {
		return publishResponseMessage;
	}
	public void setPublishResponseMessage(String publishResponseMessage) {
		this.publishResponseMessage = publishResponseMessage;
	}
	
	
}
