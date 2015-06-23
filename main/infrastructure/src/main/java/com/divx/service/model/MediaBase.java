package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MediaBase")
public class MediaBase {
	private int mediaId;
	private String title;
	private String desc;
	private String keywords;
	private String smileUrl;
	private MediaBaseType.eContentType contentType;
	private int parentId;	//parent media
							// 0: no parent
	private String sign;
	private boolean isPublic;
	private int downloadCount;
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public MediaBaseType.eContentType getContentType() {
		return contentType;
	}
	public void setContentType(MediaBaseType.eContentType contentType) {
		this.contentType = contentType;
	}
	public String getSmileUrl() {
		return smileUrl;
	}
	public void setSmileUrl(String smileUrl) {
		this.smileUrl = smileUrl;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public int getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	
}
