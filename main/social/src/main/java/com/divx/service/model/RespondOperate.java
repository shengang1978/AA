package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.divx.service.model.QueryOption.QueryType;
@XmlRootElement(name = "RespondOperate")
public class RespondOperate {
	public enum RespondType
	{
		approve, 
		refuse
	}
	private RespondType respondType;
	private int requestId;
	public RespondType getRespondType() {
		return respondType;
	}
	public void setRespondType(RespondType respondType) {
		this.respondType = respondType;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
}
