package com.divx.service.model.edu;



import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BookOption")
public class BookOption {
	private int mediaId;
	private String filePath;
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
