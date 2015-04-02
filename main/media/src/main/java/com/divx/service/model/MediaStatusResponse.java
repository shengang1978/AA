package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "MediaStatusResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MediaStatusResponse")
public class MediaStatusResponse extends ServiceResponse {
	private MediaStatus status;

	public MediaStatus getStatus() {
		return status;
	}

	public void setStatus(MediaStatus status) {
		this.status = status;
	}
}
