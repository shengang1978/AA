package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "CreateMediaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateMediaResponse")
public class CreateMediaResponse extends ServiceResponse {
	private int mediaId;
	private String sign;
	private boolean isTransfered;

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public boolean isTransfered() {
		return isTransfered;
	}

	public void setTransfered(boolean isTransfered) {
		this.isTransfered = isTransfered;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
