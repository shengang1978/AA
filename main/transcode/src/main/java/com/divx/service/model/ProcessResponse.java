package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ProcessResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessResponse")
public class ProcessResponse extends ServiceResponse{
	public enum Status
	{
		transcoding,
		uploading,
		aborted,
		pending,
		finished,
		paused,
		error,
		unkonwn
	}
	
	private int status;
	private int percent;
	private String smilUrl;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public String getSmilUrl() {
		return smilUrl;
	}
	public void setSmilUrl(String smilUrl) {
		this.smilUrl = smilUrl;
	}
}
