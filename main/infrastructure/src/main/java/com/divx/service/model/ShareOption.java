package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ShareOption")
public class ShareOption {
	public enum ShareType
	{
		all,	//Share to all.
		friend,	//Share to friend
		group	//Share in group
	}
	
	private int mediaId;
	private ShareType shareType;
	private int destId;
	
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	
	public ShareType getShareType() {
		return shareType;
	}
	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}
	
	public int getDestId() {
		return destId;
	}
	public void setDestId(int destId) {
		this.destId = destId;
	}
}
