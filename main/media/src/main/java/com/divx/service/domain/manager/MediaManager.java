package com.divx.service.domain.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MediaServiceHelper;
import com.divx.service.MessageServiceHelper;
import com.divx.service.SocialServiceHelper;
import com.divx.service.TranscodeServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.model.*;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.domain.repository.impl.MediaDaoImpl;
import com.divx.service.model.*;

@Service
public class MediaManager {
	
	private static final Logger log = Logger.getLogger(MediaManager.class);
	
	private MediaDao mediaDao;
	
	@Autowired
	public void setMediaDao(MediaDao mediaDao)
	{
		this.mediaDao = mediaDao;
	}
	
	public MediasResponse GetPublicMedias(int startPos, int endPos)
	{
		MediasResponse res = new MediasResponse();
		
		try
		{
			List<DcpMedia> objs = mediaDao.GetPublicMedias(startPos, endPos);
			
			List<Media> medias = new ArrayList<Media>();
			String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
			for(int i = 0; i < objs.size(); ++i)
			{
				Media m = new Media();
				DcpMedia obj = objs.get(i);
				
				m.setTitle(obj.getTitle());
				m.setDescription(obj.getDescription());
				//m.setKeywords(obj.getk);
				m.setMediaId(obj.getMediaId());
				
				if(obj.getSnapshoturl() != null && !obj.getSnapshoturl().isEmpty()){
					m.setSnapshotUrl(configUrl + obj.getSnapshoturl());
				}else{
					m.setSnapshotUrl("");
				}
				m.setStatus(obj.getStatus());
				
				medias.add(m);
			}
			
			res.setMedias(medias);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			log.error("MediaManager.GetPublicMedias", e);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		finally
		{
			
		}
		return res;
	}
	
	// return:
	//	> 0: success. It's the created media Id.
	//  <= 0: fail
	public int CreateMedia(MediaBase obj, int userId)
	{
		Date now = new Date();
		
		DcpMedia media = new DcpMedia();
		media.setUserId(userId);
		media.setTitle(obj.getTitle());
		media.setDescription(obj.getDescription());
		media.setDatecreated(now);
		media.setDatemodified(now);
		media.setExpiredate(Util.GetDate(now,  Calendar.MONTH, 3));	
		
		if(MediaBaseType.eContentType.URL == obj.getContentType()){
			media.setStatus(MediaBaseType.eMediaStatus.done.ordinal());	
		}else{
			media.setStatus(MediaBaseType.eMediaStatus.creating.ordinal());	//0 means the media metadata is created. <-> MediaBaseType.eMediaStatus
		}
		
		media.setDeleted(false);
		int contentType = MediaBaseType.eContentType.SMIL.ordinal();
		if (obj.getContentType() != null)
			contentType = obj.getContentType().ordinal();
		media.setContenttype(contentType);
		
		int mediaId = mediaDao.CreateMedia(media);
		if (mediaId > 0)
		{
			if(MediaBaseType.eContentType.URL.ordinal() == media.getContenttype()){
				DcpDivxassets dcpAsset = new DcpDivxassets();
				
				dcpAsset.setOriginalassetId(0);
				dcpAsset.setMediaId(mediaId);
				dcpAsset.setLocation(obj.getSmileUrl());
				dcpAsset.setVideoformat(0);
				
				dcpAsset.setDatecreated(new Date());
				dcpAsset.setDatemodified(new Date());
				
				mediaDao.CreateDivxAsset(dcpAsset); 
					
				
			}
			
			String keywords = obj.getKeywords();
			if (!keywords.trim().isEmpty())
			{
				List<DcpMediaKeywords> words = new ArrayList<DcpMediaKeywords>();

				String[] keys = keywords.trim().split(",");
				for(int i = 0; i < keys.length; ++i)
				{
					if (keys[i].trim().isEmpty())
						continue;
					
					DcpMediaKeywords word = new DcpMediaKeywords();
					word.setMediaId(mediaId);
					word.setDatecreated(new Date());
					word.setKeyword(keys[i].trim());
					words.add(word);
				}
				
				if (words.size() > 0)
					mediaDao.CreateKeywords(words);
			}
		}
		
		return mediaId;
	}
	
	public Media GetMedia(int userId, int mediaId, int deviceType)
	{		
		DcpMedia obj = mediaDao.GetMedia(mediaId);
		String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
		if (obj != null)
		{
			Media m = new Media();
			m.setMediaId(obj.getMediaId());
			m.setTitle(obj.getTitle());
			m.setDescription(obj.getDescription());
			m.setStatus(obj.getStatus());
			m.setUserId(obj.getUserId());
			m.setContentType(MediaBaseType.eContentType.values()[obj.getContenttype()]);
			if(obj.getSnapshoturl() != null && !obj.getSnapshoturl().isEmpty()){
				m.setSnapshotUrl(configUrl + obj.getSnapshoturl());
			}
			else
			{
				m.setSnapshotUrl("");
			}
			
			if (obj.getStatus() == MediaBaseType.eMediaStatus.done.ordinal())
			{
				// device type to video format rules
				// Need be configurable...
				int videoFormat = MediaBaseType.eFileType.H264.ordinal();
				switch(deviceType)
				{
				case 0: //MediaBaseType.eDeviceType.Android.ordinal():
				case 1: //MediaBaseType.eDeviceType.iOS.ordinal():
					videoFormat = MediaBaseType.eFileType.H264.ordinal();
					break;
				case 2: // MediaBaseType.eDeviceType.CE.ordinal():
					videoFormat = MediaBaseType.eFileType.H265.ordinal();
					break;
				}
				DcpDivxassets asset = mediaDao.GetDivxAsset(mediaId, videoFormat);
				if (asset != null)
				{
					m.setSmileUrl(asset.getLocation());
				}
			}
			
			return m;
		}
		
		return null;
	}
	
	public List<Media> GetMyMedias(int userId, int startPos, int endPos)
	{		
		if (startPos > endPos || startPos < 0 || endPos < 0) {
			return null;
		}
		
		List<KeyValuePair<DcpMedia,DcpDivxassets>> objlist = mediaDao.GetMyMedias(userId, startPos,endPos);
		ArrayList<Media> mediaList = new ArrayList<>();
		String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
		for (KeyValuePair<DcpMedia,DcpDivxassets> it : objlist )
		{
			DcpMedia obj = (DcpMedia)it.getKey();
			
			Media m = new Media();
			m.setMediaId(obj.getMediaId());
			m.setTitle(obj.getTitle());
			m.setDescription(obj.getDescription());
			m.setStatus(obj.getStatus());
			m.setUserId(obj.getUserId());
			m.setContentType(MediaBaseType.eContentType.values()[obj.getContenttype()]);		
			m.setSmileUrl(it.getValue().getLocation());
				
			if(obj.getSnapshoturl() != null && !obj.getSnapshoturl().isEmpty()){
					m.setSnapshotUrl(configUrl + obj.getSnapshoturl());
			}else{
					m.setSnapshotUrl("");
			}	
						
			m.setCreateDate(obj.getDatecreated());
			mediaList.add(m);
		}
		
		return mediaList;
	}
	
	// return
	//	>= 0: success
	//  < 0 : fail.
	public ServiceResponse UpdateMedia(MediaBase obj, int userId)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			if (obj.getMediaId() <= 0)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid media id.");
				return res;
			}			
			
			DcpMedia media = mediaDao.GetMedia(obj.getMediaId());
			if (media == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media.");
				return res;
			}
			
			if (media.getUserId() != userId)
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("No permission to update the media.");
				return res;
			}
			
			Date now = new Date();
			//media.setMediaId(obj.getMediaId());
			media.setDatemodified(now);
			if(obj.getDescription() != null && !obj.getDescription().isEmpty()){
				media.setDescription(obj.getDescription());
			}
		
			//media.setStatus(obj.getStatus().ordinal());
			if(obj.getTitle() != null && !obj.getTitle().isEmpty()){
				media.setTitle(obj.getTitle());
			}
			if (mediaDao.UpdateMedia(media) > 0)
			{
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Internal error. Fail to update the media.");
			}

		}
		catch(Exception ex)
		{
			log.error("MediaManager.UpdateMedia", ex);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		return res;
	}
	
	public ServiceResponse DeleteMedia(int mediaId, int userId)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpMedia media = mediaDao.GetMedia(mediaId);
			if (media == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media.");
				return res;
			}
			
			if (media.getUserId() != userId)
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("No permission to update the media.");
				return res;
			}
			
			if (mediaDao.DeleteMedia(media) > 0)
			{
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Internal error. Fail to delete the media.");
			}
		}
		catch(Exception ex)
		{
			log.error("MediaManager.DeleteMedia", ex);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}

		return res;
	}
	
	/*public UploadAssetInfo GetUploadInfo(int mediaId)
	{
		UploadAssetInfo info = new UploadAssetInfo();;
		DcpOriginalasset asset = mediaDao.GetUploadInfo(mediaId);
		if (asset != null)
		{			
			info.setUploadId(asset.getOriginalassetId());
			info.setMediaId(asset.getMediaId());
			info.setTotalSize(asset.getTotalsize());
			info.setEndPosition(asset.getUploadpos());
			info.setStatus(asset.getStatus());
			info.setFileurl(asset.getFileurl());
		}
		else
		{
			info.setMediaId(mediaId);
			info.setStatus(Upload.eUploadStatus.none);
		}
		
		return info;
	}*/
	public UploadInfoResponse GetUploadInfo(String token,int mediaId){
		UploadInfoResponse res = new UploadInfoResponse();
		try{
			UploadInfoResponse ret = new MediaServiceHelper().GetUploadInfo(token, mediaId);
			if(null == ret){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call GetUploadInfo");
				return res;
			}
			Upload u =  new Upload();
			u.setEndPosition(ret.getUploadInfo().getEndPosition());
			u.setFilename(ret.getUploadInfo().getFilename());
			u.setFileurl(ret.getUploadInfo().getFileurl());
			u.setMediaId(ret.getUploadInfo().getMediaId());
			u.setStatus(ret.getUploadInfo().getStatus().ordinal());
			u.setTotalSize(ret.getUploadInfo().getTotalSize());
			u.setUploadId(ret.getUploadInfo().getUploadId());
			res.setUploadInfo(u);
			res.setResponseCode(ret.getResponseCode());
			res.setResponseMessage(ret.getResponseMessage());
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
	
	public ServiceResponse UpdateUploadInfo(Upload info,String token){
		ServiceResponse res  = new ServiceResponse();
		try{
			DcpMedia m = mediaDao.GetMedia(info.getMediaId());
			if(null == m){
				res.setResponseCode(ResponseCode.MEDIA_NOT_EXISTED);
				res.setResponseMessage("the media is not existed.");
				return res;
			}
			//DcpOriginalasset obj = mediaDao.GetOriginalasset(info.getUploadId());
			//log.info(String.format("UpdateUploadInfo(%s)", Util.ObjectToJson(info)));
			
			switch(info.getStatus())
			{
			case done:
				if (info.getContentType() == MediaBaseType.eContentType.Gif)
					m.setStatus(MediaBaseType.eMediaStatus.done.ordinal());
				else if (MediaBaseType.eContentType.File == info.getContentType())
						m.setStatus(MediaBaseType.eMediaStatus.done.ordinal());
				else
					m.setStatus(MediaBaseType.eMediaStatus.uploaded.ordinal());
				break;
			default:
				m.setStatus(MediaBaseType.eMediaStatus.uploading.ordinal());
				break;
			}
			if(MediaBaseType.eContentType.File != info.getContentType()){
				if(info.getStatus() == Upload.eUploadStatus.done){
					TranscodeServiceHelper.ThumbnailsResponse tsh = new TranscodeServiceHelper().GenerateThumbnails(info.getUploadId(), info.getFileurl());
					if(tsh.getResponseCode() == 0 && tsh.getThumbnails() != null && tsh.getThumbnails().size() > 0){
						m.setSnapshoturl( tsh.getThumbnails().get(0).getFilename());					
					}else{
						m.setSnapshoturl("");
					}
				}else{
					m.setSnapshoturl("");
				}
			}
			
			m.setDatemodified(new Date());
			if (info.getContentType() != null)
				m.setContenttype(info.getContentType().ordinal());

			int mid = mediaDao.UpdateMedia(m);
			if(mid > 0 && info.getStatus() == Upload.eUploadStatus.done){
				DcpOriginalasset asset = new DcpOriginalasset();
				asset.setOriginalassetId(info.getUploadId());
				asset.setMediaId(info.getMediaId());
				asset.setFileurl(info.getFileurl());
				asset.setDatecreated(new Date());
				asset.setDatemodified(new Date());
				asset.setDeleted(false);
				int uid =  mediaDao.UpdateUploadInfo(asset);
				
				if (info.getContentType() != MediaBaseType.eContentType.Gif &&MediaBaseType.eContentType.File != info.getContentType())
				{	
					ServiceResponse sr = this.Publish(m.getUserId(), info.getMediaId());
					if(sr.getResponseCode() != ResponseCode.SUCCESS){
						log.warn(sr.getResponseMessage());
					}
					//after publish success, auto share media.
					
					if(sr.getResponseCode() == ResponseCode.SUCCESS){
						String ss = String.format("{\"ShareOption\":%s}",new String(DatatypeConverter.parseBase64Binary(info.getShareJson())));
						ServiceResponse srh = new SocialServiceHelper().ShareMedia(ss,token);
					}
				}else{
					
					DcpDivxassets dcpAsset = new DcpDivxassets();
					
					dcpAsset.setOriginalassetId(0);
					dcpAsset.setMediaId(info.getMediaId());
					String baseUrl = ConfigurationManager.GetInstance().SMIL_OUT_FILE_PREFIX();
					String fileNmae = info.getFileurl().substring(info.getFileurl().lastIndexOf('/'));
					dcpAsset.setLocation(Util.UrlWithSlashes(baseUrl) + fileNmae);
					dcpAsset.setVideoformat(0);
					
					dcpAsset.setDatecreated(new Date());
					dcpAsset.setDatemodified(new Date());
					
					mediaDao.CreateDivxAsset(dcpAsset);
				}
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
				
				
			}else{
				res.setResponseCode(ResponseCode.ERROR_UPDATE_MEDIA);
				res.setResponseMessage("Faile to update media.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
	/*public int SetUploadInfo(Upload info)
	{
		DcpOriginalasset obj = null;
		if (info.getUploadId() <= 0)
		{
			obj = new DcpOriginalasset();
			obj.setDatecreated(new Date());
			obj.setOriginalassetId(info.getUploadId());
			obj.setDeleted(false);
			obj.setCompleted(false);
			obj.setMediaId(info.getMediaId());
		}
		else
		{
			obj = mediaDao.GetOriginalasset(info.getUploadId());
		}
		
		obj.setDatemodified(new Date());
		obj.setStatus(info.getStatus().ordinal());
		obj.setTotalsize(info.getTotalSize());
		obj.setFileurl(info.getFileurl());
		obj.setUploadpos(info.getEndPosition());
		obj.setFilename(info.getFilename());
		
		int uiId = mediaDao.UpdateUploadInfo(obj);
		if (uiId > 0)
		{
			DcpMedia m = mediaDao.GetMedia(info.getMediaId());
			m.setStatus(info.getStatus() == Upload.eUploadStatus.done ? MediaBaseType.eMediaStatus.uploaded.ordinal() : MediaBaseType.eMediaStatus.uploading.ordinal());
			if(info.getStatus() == Upload.eUploadStatus.done){
				TranscodeServiceHelper.ThumbnailsResponse tsh = new TranscodeServiceHelper().GenerateThumbnails(obj.getOriginalassetId(), obj.getFileurl());
				if(tsh.getResponseCode() == 0 && tsh.getThumbnails() != null && tsh.getThumbnails().size() > 0){
					m.setSnapshoturl( tsh.getThumbnails().get(0).getFilename());
					
				}else{
					m.setSnapshoturl("");
				}
			}else{
				m.setSnapshoturl("");
			}
			m.setDatemodified(new Date());
			mediaDao.UpdateMedia(m);
			return uiId;
		}
		
		return 0;
	}*/
	
	class PublishResult
	{
		private ServiceResponse ServiceResponse;

		public ServiceResponse getServiceResponse() {
			return ServiceResponse;
		}

		public void setServiceResponse(ServiceResponse serviceResponse) {
			ServiceResponse = serviceResponse;
		}
		
	}
	public ServiceResponse Publish(int userId, int mediaId)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpMedia obj = mediaDao.GetMedia(mediaId);
			if (obj == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media");
				return res;
			}
			
			if (obj.getUserId() != userId)
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("No permission");
				return res;
			}
			
			String reqRet = "";
			ServiceResponse ret = null;
			MediaBaseType.eMediaStatus status = MediaBaseType.eMediaStatus.values()[obj.getStatus()];
			switch(status)
			{
			case uploaded:
			case error_transcode_fail:
				DcpOriginalasset oriAsset = mediaDao.GetUploadInfo(mediaId);
				String baseUrl = ConfigurationManager.GetInstance().TranscodeServiceBaseUrl();
				reqRet = Util.HttpPost(baseUrl + "/transcode/transcode/" + oriAsset.getId().toString(), oriAsset.getFileurl());
				ret = Util.JsonToObject(reqRet, ServiceResponse.class);
			case edited:
				
				break;
			default:
				res.setResponseCode(ResponseCode.ERROR_MEDIA_STATUS_TO_PUBLISH);
				res.setResponseMessage(String.format("Wrong media status \"%s\" to publish.", status.toString()));
				return res;
			}
			
			if (ret != null && ret.getResponseCode() == ResponseCode.SUCCESS)
			{
				obj.setStatus(MediaBaseType.eMediaStatus.publishing.ordinal());
				mediaDao.UpdateMedia(obj);
				
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else if (ret != null)
			{
				res.setResponseCode(ResponseCode.ERROR_PUBLISH_MEDIA_FAILED);
				res.setResponseMessage(ret.getResponseMessage());
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("JsonToObject error. " + reqRet);
			}
		}
		catch(Exception e)
		{
			MediaManager.log.error("MediaManager.Publish", e);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		return res;
	}
	
	public ServiceResponse EndPublish(EndPublishOption option)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpOriginalasset oriAsset = mediaDao.GetOriginalasset(option.getAssetId());
			if (oriAsset == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the asset");
				return res;
			}
			
			DcpMedia m = mediaDao.GetMedia(oriAsset.getMediaId());
			if (m == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media");
				return res;
			}
			
			MediaBaseType.eMediaStatus status = MediaBaseType.eMediaStatus.values()[m.getStatus()];
			switch(status)
			{
			case publishing:
			case done:
			case error_transcode_fail:
				break;
			
			default:
				res.setResponseCode(ResponseCode.ERROR_MEDIA_STATUS_TO_PUBLISH);
				res.setResponseMessage(String.format("Wrong media status \"%s\" to finish publish.", status.toString()));
				return res;
			}
			
			if (option.getStatus() == EndPublishOption.eStatus.error)
			{
				// Fail to transcode.
				if (status != MediaBaseType.eMediaStatus.done)
					m.setStatus(MediaBaseType.eMediaStatus.error_transcode_fail.ordinal());
				
				m.setDatemodified(new Date());
				m.setErrorlog(option.getMessage());
				
				mediaDao.UpdateMedia(m);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				
				MessageServiceHelper helper = new MessageServiceHelper();
				helper.SendSysMessage(MessageServiceHelper.eSysMessageType.ToPerson, 
									  MessageCategory.MEDIA_SERVICE_MESSAGE_TRANSCODE_FAILED, 
									  m.getUserId(), 
									  String.format("Media \"%s\" fails to publish. Unsupport file or bad file.", m.getTitle()));
				
				return res;
			}
			else if (option.getSmilPath() == null || option.getSmilPath().trim().isEmpty())
			{
				// Invalid smil
				if (status != MediaBaseType.eMediaStatus.done)
					m.setStatus(MediaBaseType.eMediaStatus.error_transcode_fail.ordinal());
				m.setDatemodified(new Date());				
				m.setErrorlog("smil path is empty.");
				
				mediaDao.UpdateMedia(m);
				
				res.setResponseCode(ResponseCode.ERROR_PUBLISH_MEDIA_INVALID_SMIL);
				res.setResponseMessage("Smil path is empty");
				
				MessageServiceHelper helper = new MessageServiceHelper();
				helper.SendSysMessage(MessageServiceHelper.eSysMessageType.ToPerson, 
									  MessageCategory.MEDIA_SERVICE_MESSAGE_TRANSCODE_FAILED, 
									  m.getUserId(), 
									  String.format("Media \"%s\" fails to publish. Please try later.", m.getTitle()));
				
				return res;
			}
			
			DcpDivxassets obj = new DcpDivxassets();
			
			obj.setOriginalassetId(option.getAssetId());
			obj.setMediaId(oriAsset.getMediaId());
			obj.setLocation(option.getSmilPath());
			obj.setVideoformat(option.getFileType().ordinal());
			
			obj.setDatecreated(new Date());
			obj.setDatemodified(new Date());
			
			if (mediaDao.CreateDivxAsset(obj) > 0)
			{		
				if(MediaBaseType.eMediaStatus.done.ordinal() == m.getStatus()){
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
					return res;
				}
				m.setStatus(MediaBaseType.eMediaStatus.done.ordinal());
				if (mediaDao.UpdateMedia(m) > 0)
				{				
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
					MessageServiceHelper helper = new MessageServiceHelper();
					ServiceResponse sr = helper.SendSysMessage(MessageServiceHelper.eSysMessageType.ToPerson, 
										  MessageCategory.MEDIA_SERVICE_MESSAGE_PUBLISH_COMPLETED, 
										  m.getUserId(),"Your video is ready to be viewed.");
					if (sr.getResponseCode() != ResponseCode.SUCCESS)
					{
						res.setResponseMessage(String.format("Fail to send message. %d, %s", sr.getResponseCode(), sr.getResponseMessage()));
					}
				}
				else
				{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to update media status.");
				}
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to save asset information.");
			}
		}
		catch(Exception e)
		{
			MediaManager.log.error("MediaManager.EndPublish", e);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		return res;
		
	}
	
	public ServiceResponse CancelUpload(int mediaId,String token){
		ServiceResponse res = new ServiceResponse();
		try{
			DcpMedia m = mediaDao.GetMedia(mediaId);
			if (m == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media");
				return res;
			}
			if(m.getStatus() == MediaBaseType.eMediaStatus.uploading.ordinal()){
				String baseUrl = ConfigurationManager.GetInstance().StorageServiceBaseUrl();
				String reqUrl = String.format(baseUrl + "/storage/CancelUpload/%d",mediaId);
				List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
				headers.add(new KeyValuePair<String, String>("Token", token));
				headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
				String ret = Util.HttpPut(reqUrl,"",headers);
				if(!ret.isEmpty()){
					ServiceResponse ur = Util.JsonToObject(ret, ServiceResponse.class);
					if(ur.getResponseCode() == ResponseCode.SUCCESS){
						
						m.setStatus(MediaBaseType.eMediaStatus.creating.ordinal());
						int uid = mediaDao.UpdateMedia(m);
						if(uid > 0){
							res.setResponseCode(ResponseCode.SUCCESS);
							res.setResponseMessage("success");
						}else{
							res.setResponseCode(ResponseCode.ERROR_UPDATE_MEDIA);
							res.setResponseMessage("Faile to update media.");
						}
					}else{
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("Faile to CancelUpload.");
					}
				}
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Cannot CancelUpload.");
			}
			
			
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
}
