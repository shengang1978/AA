package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DownloadCountOption")
public class DownloadCountOption {
	private int mediaId;

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

}
