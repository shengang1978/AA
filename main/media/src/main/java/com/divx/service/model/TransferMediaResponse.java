package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "TransferMediaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferMediaResponse")
public class TransferMediaResponse extends ServiceResponse {
	private int mediaId;

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
}
