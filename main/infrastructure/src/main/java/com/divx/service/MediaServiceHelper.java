package com.divx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;

import com.divx.service.model.KeyValuePair;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.UploadInfoResponse;
import com.divx.service.model.Upload;

public class MediaServiceHelper extends ServiceResponse {
	
		public class MediaBase {
			private int mediaId;
			private String title;
			private String description;
			private String keywords;
			private boolean isPublic;
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
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			public String getKeywords() {
				return keywords;
			}
			public void setKeywords(String keywords) {
				this.keywords = keywords;
			}
			public boolean isPublic() {
				return isPublic;
			}
			public void setPublic(boolean isPublic) {
				this.isPublic = isPublic;
			}
		}	
		MediaBase MediaBase;
	
	//PublicMedia
	public ServiceResponse PublicMedia(String strToken, int mediaId, boolean isPublic)
	{
		
		ServiceResponse res = new ServiceResponse();
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MediaServiceBaseUrl();
			String reqUrl = baseUrl + "/media/";
			
			MediaServiceHelper msh = new MediaServiceHelper();
			MediaBase mb = new MediaBase();
			mb.setMediaId(mediaId);
			mb.setPublic(isPublic);
			msh.MediaBase = mb;
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("Token", strToken));
			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			String ret = Util.HttpPut(reqUrl, Util.ObjectToJson(msh).replaceFirst("isPublic", "public"),headers);  
			if (ret.isEmpty())
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call UpdateMedia");
				return res;
			}
			
			MediaServiceHelper sr = Util.JsonToObject(ret, MediaServiceHelper.class);
			
			res.setResponseCode(sr.getResponseCode());
			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		return res;
	}
	public enum eUploadStatus
	{
		none,		//Not begin(not asset file)
		uploading,	// It's uploading. Not complete
		expired,	// The uploading asset file is expired.
		done		// Uploading finished.
	}
//	public class Upload {
//		
//		
//		private int uploadId;
//		private eUploadStatus status;
//		private int totalSize;
//		private int mediaId;
//		private int endPosition;
//		private String filename;
//		private String fileurl;
//		private String shareJson;
//
//		public String getFileurl() {
//			return fileurl;
//		}
//
//		public void setFileurl(String fileurl) {
//			this.fileurl = fileurl;
//		}
//		public String getFilename() {
//			return filename;
//		}
//		public void setFilename(String filename) {
//			this.filename = filename;
//		}
//		//	private Date expireDate;
//		public int getUploadId() {
//			return uploadId;
//		}
//		public void setUploadId(int uploadId) {
//			this.uploadId = uploadId;
//		}
//		public eUploadStatus getStatus() {
//			return status;
//		}
//		public void setStatus(eUploadStatus status) {
//			this.status = status;
//		}
//		public void setStatus(int nStatus)
//		{
//			this.status = eUploadStatus.values()[nStatus];
//		}
//		public int getTotalSize() {
//			return totalSize;
//		}
//		public void setTotalSize(int totalSize) {
//			this.totalSize = totalSize;
//		}
//		public int getMediaId() {
//			return mediaId;
//		}
//		public void setMediaId(int mediaId) {
//			this.mediaId = mediaId;
//		}
//		public int getEndPosition() {
//			return endPosition;
//		}
//		public void setEndPosition(int endPosition) {
//			this.endPosition = endPosition;
//		}
//
//		public String getShareJson() {
//			return shareJson;
//		}
//
//		public void setShareJson(String shareJson) {
//			this.shareJson = shareJson;
//		}
//	}
//	public class UploadInfoResponse extends ServiceResponse {
//		private Upload uploadInfo;
//		public Upload getUploadInfo() {
//			return uploadInfo;
//		}
//
//		public void setUploadInfo(Upload uploadInfo) {
//			this.uploadInfo = uploadInfo;
//		}
//		
//	}
	

	
	public UploadInfoResponse UploadInfoResponse;
	public Upload Upload;
	private static final Logger log = Logger.getLogger(MediaServiceHelper.class);
	
	public ServiceResponse UpdateMediaStatus(String token, Upload up)
	{
		ServiceResponse res = new ServiceResponse();
		try{
			String baseUrl = ConfigurationManager.GetInstance().MediaServiceBaseUrl();
			String reqUrl = baseUrl + "/media/UpdateUploadInfo";
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("Token", token));
			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			MediaServiceHelper msh = new MediaServiceHelper();

			msh.Upload = up;
			
			String strRet = Util.HttpPutJson(reqUrl, msh, headers);
			if(!strRet.isEmpty()){
				res = Util.JsonToObject(strRet, ServiceResponse.class);
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
//	public ServiceResponse UpdateMediaStatus(int uploadId,int mediaId,int status,String fileurl,String token,String shareJson)
//	{
//		ServiceResponse res = new ServiceResponse();
//		try{
//			String baseUrl = ConfigurationManager.GetInstance().MediaServiceBaseUrl();
//			String reqUrl = baseUrl + "/media/UpdateUploadInfo";
//			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
//			headers.add(new KeyValuePair<String, String>("Token", token));
//			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
//			MediaServiceHelper msh = new MediaServiceHelper();
//			Upload up = new Upload();
//			up.setMediaId(mediaId);
//			up.setUploadId(uploadId);
//			up.setStatus(status);
//			up.setFileurl(fileurl);
//			up.setShareJson(DatatypeConverter.printBase64Binary(shareJson.getBytes()));
//			msh.Upload = up;
//			
//			String strRet = Util.HttpPutJson(reqUrl, msh, headers);
//			if(!strRet.isEmpty()){
//				res = Util.JsonToObject(strRet, ServiceResponse.class);
//			}
//		}catch(Exception ex){
//			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
//			res.setResponseMessage(ex.getMessage());
//		}
//		return res;
//	}
	public UploadInfoResponse GetUploadInfo(String token,int mediaId){
		UploadInfoResponse res = new UploadInfoResponse();
		try{
			String reqUrl = String.format("%s/storage/GetUploadInfo/%d", ConfigurationManager.GetInstance().StorageServiceBaseUrl(),mediaId);
			//List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			//headers.add(new KeyValuePair<String, String>("Token", token));
			// headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			String ret = Util.HttpGet(reqUrl);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call GetUploadInfo");
				return res;
			}
			MediaServiceHelper msh = Util.JsonToObject(ret, MediaServiceHelper.class);
			res = msh.UploadInfoResponse;
			
		}catch(Exception  ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
	public enum eStatus
	{
		success,
		error
	}
	
	public class EndPublishOption {		
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
	
	public EndPublishOption EndPublishOption;
	public static boolean EndPublish(String endPublishOption)
	{
		EndPublishOption endOpt = null;
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MediaServiceBaseUrl();
			String reqUrl = baseUrl + "/media/EndPublish";
			endOpt = Util.JsonToObject(endPublishOption, EndPublishOption.class);
			String reqRet = Util.HttpPutJson(reqUrl, endOpt, null);
			ServiceResponse sr = Util.JsonToObject(reqRet, ServiceResponse.class);
			if (sr.getResponseCode() == ResponseCode.SUCCESS)
				return true;
			else
			{
				log.error(String.format("Error To call EndPublish. %s, %s, %s", reqUrl, Util.ObjectToJson(endOpt), reqRet));
			}
		}
		catch(Exception ex)
		{
			log.error(String.format("Fail to call EndPublish(%s)", Util.ObjectToJson(endOpt)), ex);
		}
		return false;
	}
}
