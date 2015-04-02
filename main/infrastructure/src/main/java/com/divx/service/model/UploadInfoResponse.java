package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "UploadInfoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UploadInfoResponse")
public class UploadInfoResponse extends ServiceResponse {
	private Upload uploadInfo;
	public Upload getUploadInfo() {
		return uploadInfo;
	}

	public void setUploadInfo(Upload uploadInfo) {
		this.uploadInfo = uploadInfo;
	}
	
}
