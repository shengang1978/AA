package org.androidpn.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Notification")
public class Notification {
	private int messageId;
	private List<String> deviceTokens;
	private boolean isBroadcast;
	private String data;
	public boolean getIsBroadcast()
	{
		return isBroadcast; 
	}
	public void setIsBroadcast(boolean isBroadcast) {
		this.isBroadcast = isBroadcast;
	}
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public List<String> getDeviceTokens() {
		return deviceTokens;
	}
	public void setDeviceTokens(List<String> deviceTokens) {
		this.deviceTokens = deviceTokens;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
