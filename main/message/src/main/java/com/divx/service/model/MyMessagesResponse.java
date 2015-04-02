package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
public class MyMessagesResponse extends ServiceResponse{
	private List<MyMessage>	myMessages;

	public List<MyMessage> getMyMessages() {
		return myMessages;
	}

	public void setMyMessages(List<MyMessage> myMessages) {
		this.myMessages = myMessages;
	}
}
