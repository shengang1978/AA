package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ServiceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceResponse")
public class ServiceResponse {
	private int code;
	
	public int getResponseCode()
	{
		return code;
	}
	
	public void setResponseCode(int nCode)
	{
		code = nCode;
	}
	
	private String message;
	
	public String getResponseMessage()
	{
		return message;
	}
	
	public void setResponseMessage(String msg)
	{
		message = msg;
	}
}