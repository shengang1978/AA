package com.divx.service.model;


import javax.xml.bind.annotation.XmlRootElement;

import com.divx.service.model.MediaBaseType.eFileType;
@XmlRootElement(name = "Upload")
public class Upload {
	public enum eUploadStatus
	{
		none,		//Not begin(not asset file)
		uploading,	// It's uploading. Not complete
		expired,	// The uploading asset file is expired.
		done,		// Uploading process is finished.
		predone		// uploading is done. Process isn't done. There is still something which need to do.
	}
	
	private int uploadId;
	private eUploadStatus status;
	private int totalSize;
	private int mediaId;
	private int endPosition;
	private String filename;
	private String fileurl;
	private String shareJson;
	private String v2gJson;
	private MediaBaseType.eContentType contentType;
	private MediaBaseType.eFileType	fileType;

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	//	private Date expireDate;
	public int getUploadId() {
		return uploadId;
	}
	public void setUploadId(int uploadId) {
		this.uploadId = uploadId;
	}
	public eUploadStatus getStatus() {
		return status;
	}
	public void setStatus(eUploadStatus status) {
		this.status = status;
	}
	public void setStatus(int nStatus)
	{
		this.status = eUploadStatus.values()[nStatus];
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	public int getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}

	public String getShareJson() {
		return shareJson;
	}

	public void setShareJson(String shareJson) {
		this.shareJson = shareJson;
	}

	public MediaBaseType.eContentType getContentType() {
		return contentType;
	}

	public void setContentType(MediaBaseType.eContentType contentType) {
		this.contentType = contentType;
	}

	public String getV2gJson() {
		return v2gJson;
	}

	public void setV2gJson(String v2gJson) {
		this.v2gJson = v2gJson;
	}

	public MediaBaseType.eFileType getFileType() {
		return fileType;
	}

	public void setFileType(MediaBaseType.eFileType fileType) {
		this.fileType = fileType;
	}
	
	public MediaBaseType.eFileType GetFileTypeByContentType()
	{
		if (fileType == null || fileType == eFileType.Auto)
		{
			MediaBaseType.eFileType ft = eFileType.Auto;
			switch(contentType)
			{
			case Gif:
			case Video4Gif:
				ft = eFileType.Gif;
				break;
			case EduBook:
				ft = eFileType.EduBook;
				break;
			}
			return ft;
		}
		else
		{
			return fileType;
		}
	}
}
