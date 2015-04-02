package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ResponseOption")
public class ResponseOption {
	private List<Integer> messageIds;

	public List<Integer> getMessageIds() {
		return messageIds;
	}

	public void setMessageIds(List<Integer> messageIds) {
		this.messageIds = messageIds;
	}
}
