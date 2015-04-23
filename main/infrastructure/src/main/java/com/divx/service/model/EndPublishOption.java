package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EndPublishOption")
public class EndPublishOption {
	public enum eStatus
	{
		success,
		error
	}

	private String	smilPath;
	private int assetId;
	private eStatus status;
	private MediaBaseType.eFileType fileType;
	private String message;
	
	public String getSmilPath() {
		return smilPath;
	}
	public void setSmilPath(String smilPath) {
		this.smilPath = smilPath;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public eStatus getStatus() {
		return status;
	}
	public void setFileType(MediaBaseType.eFileType filetype) {
		this.fileType = filetype;
	}
	public MediaBaseType.eFileType getFileType() {
		return fileType;
	}
	public void setStatus(eStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
