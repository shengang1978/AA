package com.divx.service.domain.manager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.GetBookInfoUtil;
import com.divx.service.MediaServiceHelper;
import com.divx.service.MessageServiceHelper;
import com.divx.service.PListFileUtil;
import com.divx.service.ProcessWithTimeout;
import com.divx.service.SocialServiceHelper;
import com.divx.service.TranscodeServiceHelper;
import com.divx.service.UnZipFileUtil;
import com.divx.service.UserHelper;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.lesson;
import com.divx.service.domain.model.*;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.model.*;
import com.divx.service.model.DcpBaseType.eAppType;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.MediaBaseType.eFileType;
import com.divx.service.model.SysMessage.eSysMessageType;
import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.LessonsOption;
import com.divx.service.model.edu.LessonsResponse;
import com.divx.service.model.edu.Score;
import com.divx.service.model.edu.SetResponse;
import com.divx.service.model.ugc.Book;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class MediaManager {
	private static final Cache<String, MediasResponse>	cachePublicMedias = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(6, TimeUnit.HOURS).build();
	private static final Cache<String, DcpMediaSign>	cacheMediaSigns = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(6, TimeUnit.HOURS).build();
	private static final Cache<String, Lesson>			cacheLessons = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(6, TimeUnit.HOURS).build();
	private static final Cache<String, StoriesResponse>	cachePublicStories = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(6, TimeUnit.HOURS).build();
	private static final Cache<String, List<Media>> cacheAllMedias = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(6, TimeUnit.HOURS).build();
	private static final Logger log = Logger.getLogger(MediaManager.class);
	
	private MediaDao mediaDao;
	
	@Autowired
	public void setMediaDao(MediaDao mediaDao)
	{
		this.mediaDao = mediaDao;
	}
	
	public MediasResponse GetPublicMedias(eAppType appType, String appVersion, int startPos, int endPos)
	{
		MediasResponse res = null;
		
		try
		{
			String pmKey = String.format("pm_%d_%s_%d_%d", appType.ordinal(), appVersion, startPos, endPos);
			res = cachePublicMedias.getIfPresent(pmKey);
			
			if (res == null)
			{
				res = new MediasResponse();
				
				List<Integer> contentTypes = new LinkedList<Integer>();
				if (appType == DcpBaseType.eAppType.Yingyueguan)
				{
					contentTypes.add(MediaBaseType.eContentType.EduBook.ordinal());
					contentTypes.add(MediaBaseType.eContentType.EduBookURL.ordinal());
				}
				else
				{
					contentTypes.add(MediaBaseType.eContentType.SMIL.ordinal());
					contentTypes.add(MediaBaseType.eContentType.Gif.ordinal());	
					contentTypes.add(MediaBaseType.eContentType.Video4Gif.ordinal());	
				}
				
				List<KeyValuePair<DcpMedia,DcpDivxassets>> objs = mediaDao.GetPublicMedias(contentTypes);
				
				List<Media> medias = new ArrayList<Media>();

				int bitVersion = MediaHelper.AppVersionToBookBitwise(appVersion);
				int nIndex = 0;
				for(KeyValuePair<DcpMedia,DcpDivxassets> obj: objs)
				{
					DcpMedia objMedia = obj.getKey();
					
					if ((objMedia.getAppversion() & bitVersion) == 0)
					{
						continue;
					}
					
					if (nIndex < startPos)
					{
						++nIndex;
						continue;
					}
					else if (nIndex > endPos)
						break;
					
					++nIndex;
					
					Media m = MediaHelper.ToMedia(obj, new Media());
					
					if (objMedia.getUserId() > 0)
					{
						User user = UserHelper.GetUser(objMedia.getUserId());
						if (user != null)
						{
							m.setUsername(user.getUsername());
							m.setNickname(user.getNickname());
						}
					}
					
					medias.add(m);
				}
				
				res.setMedias(medias);
				res.setStartPos(startPos);
				res.setCount(medias.size());
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				
				cachePublicMedias.put(pmKey, res);
			}
						
			if (res != null){
				List<DcpDownload> downs = mediaDao.GetAllDownloadCount();
				
				List<Media> medias = res.getMedias();
				Iterator<Media> itM = medias.iterator();
				Iterator<DcpDownload> itD = downs.iterator();
				
				Media m = null;
				DcpDownload d = null;
				while(itM.hasNext() && itD.hasNext())
				{
					if (m == null)
						m = itM.next();

					if (d == null)
						d = itD.next();
					
					if (m.getMediaId() == d.getDcpMedia().getMediaId())
					{
						//set download
						m.setDownloadCount(d.getDownloadCount());
						m = null;
						d = null;
					}
					else if (m.getMediaId() > d.getDcpMedia().getMediaId())
						m = null;
					else
						d = null;
				}
				
				while (itM.hasNext())
				{
					m = itM.next();
					m.setDownloadCount(0);
				}
				
				return res;
			}
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Internal Error. " + e.getMessage());
			Util.LogError(log, String.format("Exception to call GetPublicMedias(%s, %d, %d)", appType.toString(), startPos, endPos),  e);
		}
		finally
		{
			
		}
		return res;
	}
	
	// return:
	//	> 0: success. It's the created media Id.
	//  <= 0: fail
	public CreateMediaResponse CreateMedia(MediaBase obj, int userId,DcpBaseType.eAppType appType)
	{
		boolean bEnableSignCheck = ConfigurationManager.GetInstance().GetConfigValue("Media_Enable_SignCheck", false);
		
		CreateMediaResponse res = new CreateMediaResponse();
		Date now = new Date();
		DcpMedia media = new DcpMedia();
		DcpMediaSign mediaSign = null;
		try{
			if(obj.getSign() != null && !obj.getSign().isEmpty()){
				mediaSign = mediaDao.GetMediaSign(obj.getSign());
				if(mediaSign != null){
					/*DcpMedia m = mediaSign.getDcpMedia();
					
					m.setUserId(userId);
					
					TransferOption option = new TransferOption();
					option.setMediaId(m.getMediaId());
					option.setDestId(userId);
					TransferMediaResponse tfr = TransferMedia(m.getUserId(), option);
					if(tfr.getResponseCode() == ResponseCode.SUCCESS){
						res.setMediaId(tfr.getMediaId());
						res.setSign(mediaSign.getSign());
						res.setTransfered(true);
					}*/
					res.setResponseCode(ResponseCode.MEDIA_HAS_BEEN_IN_LIBRARY);
					res.setResponseMessage("media is exist!");
					return res;
				}
				else
				{
					if(bEnableSignCheck && appType == eAppType.Yingyueguan && 
							(obj.getContentType() == eContentType.EduBook ||
							obj.getContentType() == eContentType.EduBookURL)){
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("Fail to create media, the sign doesn't exist");
						return res;
					}
				}
			}
			
			media.setUserId(userId);
			media.setTitle(obj.getTitle());
			media.setDescription(obj.getDesc());
			media.setDatecreated(now);
			media.setDatemodified(now);
			media.setExpiredate(Util.GetDate(now,  Calendar.MONTH, 3));	
			
			if(MediaBaseType.eContentType.EduBookURL == obj.getContentType()){
				media.setStatus(MediaBaseType.eMediaStatus.done.ordinal());	
			}else{
				media.setStatus(MediaBaseType.eMediaStatus.creating.ordinal());	//0 means the media metadata is created. <-> MediaBaseType.eMediaStatus
			}

			media.setParentId(obj.getParentId());
			
			media.setDeleted(false);
			int contentType = MediaBaseType.eContentType.SMIL.ordinal();
			if (obj.getContentType() != null)
				contentType = obj.getContentType().ordinal();
			media.setContenttype(contentType);
			media.setIspublic(obj.getIsPublic());
			media.setWeight(0);
			media.setAppversion(DcpBaseType.BookBitwise.Normal);	//Default, it only be able to search by Normal version app.
			
			int mediaId = mediaDao.CreateMedia(media);
			if (mediaId > 0)
			{
				if (obj.getIsPublic())
				{
					cachePublicMedias.invalidateAll();
				}

				if(MediaBaseType.eContentType.EduBookURL.ordinal() == media.getContenttype()){
					DcpDivxassets dcpAsset = new DcpDivxassets();
					
					dcpAsset.setOriginalassetId(0);
					dcpAsset.setMediaId(mediaId);
					dcpAsset.setLocation(obj.getSmileUrl());
					dcpAsset.setVideoformat(0);
					dcpAsset.setDatecreated(new Date());
					dcpAsset.setDatemodified(new Date());
					
					mediaDao.CreateDivxAsset(dcpAsset); 
				}
				
				if(mediaSign == null && (
					MediaBaseType.eContentType.EduBook.ordinal() == media.getContenttype() ||
					MediaBaseType.eContentType.EduBookURL.ordinal() == media.getContenttype())){
					mediaSign = new DcpMediaSign();
					mediaSign.setDcpMedia(media);
					mediaSign.setSign(obj.getSign());
					mediaSign.setCreateDate(now);
					mediaSign.setModifyDate(now);
					if (mediaDao.createMediaSign(mediaSign) > 0)
						cacheMediaSigns.invalidateAll();
				}
				
				String keywords = obj.getKeywords();
				if (keywords != null && !keywords.trim().isEmpty())
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
				res.setMediaId(mediaId);
				if(mediaSign != null){
					res.setSign(mediaSign.getSign());
					res.setTransfered(false);
				}
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
				
			}else{
				
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to create media");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("Exception CreateMedia(%s)", Util.ObjectToJson(obj)), ex);
		}
		
		return res;
	}
	
	public Media GetMedia(int userId, int mediaId, DcpBaseType.eDeviceType deviceType)
	{		
		DcpMedia obj = mediaDao.GetMedia(mediaId);
		
		if (obj != null)
		{
			Media m = new Media();
			m.setMediaId(obj.getMediaId());
			m.setTitle(obj.getTitle());
			m.setDesc(obj.getDescription());
			m.setStatus(obj.getStatus());
			m.setUserId(obj.getUserId());
			m.setContentType(MediaBaseType.eContentType.values()[obj.getContenttype()]);
			if(obj.getSnapshoturl() != null && !obj.getSnapshoturl().isEmpty()){
				m.setSnapshotUrl(Util.UrlWithHttp(obj.getSnapshoturl()));
			}
			else
			{
				m.setSnapshotUrl("");
			}
			
			if (obj.getStatus() == MediaBaseType.eMediaStatus.done.ordinal())
			{
				// device type to video format rules
				// Need be configurable...
				MediaBaseType.eFileType fileType = MediaBaseType.eFileType.H264;
				switch(deviceType)
				{
				case Android: //MediaBaseType.eDeviceType.Android.ordinal():
				case iOS: //MediaBaseType.eDeviceType.iOS.ordinal():
					fileType = MediaBaseType.eFileType.H264;
					break;
				case CE: // MediaBaseType.eDeviceType.CE.ordinal():
					fileType = MediaBaseType.eFileType.H265;
					break;
				}
				List<DcpDivxassets> assets = mediaDao.GetDivxAsset(mediaId, null);
				if (assets != null && assets.size() > 0)
				{
					m.setSmileUrl(assets.get(0).getLocation());
				}
			}
			
			if (obj.getParentId() == 0 && 
				(m.getContentType() == eContentType.EduBook ||
				 m.getContentType() == eContentType.EduBookURL))
			{
				String signKey = String.format("ms_%d", mediaId);
				DcpMediaSign mediaSign = cacheMediaSigns.getIfPresent(signKey);
				if (mediaSign == null)
				{
					mediaSign = mediaDao.GetMediaSign(mediaId);
					if (mediaSign != null)
						cacheMediaSigns.put(signKey, mediaSign);
				}
				if(mediaSign != null){
					m.setSign(mediaSign.getSign());
				}
			}
			
			return m;
		}
		
		return null;
	}
	
	public Story GetStory(int mediaId)
	{
		DcpMedia obj = mediaDao.GetMedia(mediaId);
		
		if (obj == null || 
			obj.getContenttype() != eContentType.EduStory.ordinal() ||
			obj.getParentId() == null || obj.getParentId() <= 0)
		{
			//Invalid mediaId. It isn't a story. Or it don't have parent (lesson).
			//return null;
			if (obj.getLessonId() == null || obj.getLessonId() == 0)
				return null;
		}
		
		Story m = MediaHelper.ToStory(obj, new Story());
		if (obj.getUserId() > 0)
		{
			User user = UserHelper.GetUser(obj.getUserId());
			if (user != null)
			{
				m.setUsername(user.getUsername());
				m.setNickname(user.getNickname());
			}
		}
		List<DcpDivxassets> audioObjs = mediaDao.GetDivxAsset(mediaId, eFileType.EduStoryAudio);
		if (audioObjs != null && audioObjs.size() > 0)
		{
			m.setRecordUrl(Util.UrlWithHttp(audioObjs.get(0).getLocation()));
		}
		else
		{
			m.setRecordUrl("");
		}

		return m;
	}
	
	public List<Lesson> GetLessons(int userId, int mediaId)
	{
		List<DcpLesson> lesObjs = mediaDao.GetLessons(mediaId);
		
		List<Lesson> objs = new LinkedList<Lesson>();
		
		for(DcpLesson obj: lesObjs)
		{
			objs.add(ScoreHelper.ToLesson(obj, new Lesson(), false));
		}
		
		return objs;
	}
	
	public List<Score> GetScore(int userId, int mediaId)
	{
		List<DcpScore> scoObjs = mediaDao.GetScores(userId, mediaId);
		
		List<Score> objs = new LinkedList<Score>();
		
		for(DcpScore obj: scoObjs)
		{
			objs.add(ScoreHelper.ToScore(obj));
		}
		return objs;
	}
	
	public List<Media> GetMyMedias(DcpBaseType.eAppType appType, int userId, int startPos, int endPos)
	{		
		if (startPos > endPos || startPos < 0 || endPos < 0) {
			return null;
		}
		
		List<Integer> contentTypes = new LinkedList<Integer>();
		if (appType == DcpBaseType.eAppType.Yingyueguan)
		{
			contentTypes.add(MediaBaseType.eContentType.EduBook.ordinal());
			contentTypes.add(MediaBaseType.eContentType.EduBookURL.ordinal());
		}else{
			contentTypes.add(MediaBaseType.eContentType.SMIL.ordinal());
			contentTypes.add(MediaBaseType.eContentType.Gif.ordinal());	
			contentTypes.add(MediaBaseType.eContentType.Video4Gif.ordinal());	
		}
		
		return GetMedias(contentTypes, userId, startPos, endPos);
	}
	
	public List<Story> MyStories(DcpBaseType.eAppType appType, int userId, int startPos, int endPos)
	{		
		if (startPos > endPos || startPos < 0 || endPos < 0) {
			return null;
		}
		
		List<Integer> contentTypes = new LinkedList<Integer>();
		if (appType == DcpBaseType.eAppType.Yingyueguan)
		{
			contentTypes.add(MediaBaseType.eContentType.EduStory.ordinal());
		}
		
		return GetStories(contentTypes, userId, startPos, endPos);
	}
	
	private List<Media> GetMedias(List<Integer> contentTypes, int userId, int startPos, int endPos)
	{
		List<KeyValuePair<DcpMedia,DcpDivxassets>> objlist = mediaDao.GetMyMedias(contentTypes, userId, startPos,endPos);
		List<Media> mediaList = new LinkedList<>();
		
		for (KeyValuePair<DcpMedia,DcpDivxassets> it : objlist )
		{
			DcpMedia obj = (DcpMedia)it.getKey();
			
			Media m = MediaHelper.ToMedia(it, new Media());

//			if (obj.getParentId() == 0)
//			{				
//				String signKey = String.format("ms_%d", obj.getMediaId());
//				DcpMediaSign mediaSign = cacheMediaSigns.getIfPresent(signKey);
//				if (mediaSign == null)
//				{
//					mediaSign = mediaDao.GetMediaSign(obj.getMediaId());
//					if (mediaSign != null)
//						cacheMediaSigns.put(signKey, mediaSign);
//				}
//				if(mediaSign != null){
//					m.setSign(mediaSign.getSign());
//				}
//			}
			/*DcpDownload download = mediaDao.GetDownloadCount(obj.getMediaId());
			if(download != null){
				m.setDownloadCount(download.getDownloadCount());
			}else{
				m.setDownloadCount(0);
			}*/
			mediaList.add(m);
		}
		
		return mediaList;
	}
	
	private List<Story> GetStories(List<Integer> contentTypes, int userId, int startPos, int endPos)
	{
		List<KeyValuePair<DcpMedia,DcpDivxassets>> objlist = mediaDao.GetMyMedias(contentTypes, userId, startPos,endPos);
		HashMap<DcpMedia,List<DcpDivxassets>> maps = new HashMap<DcpMedia,List<DcpDivxassets>>();
		 
		ArrayList<Story> storyList = new ArrayList<>();
		List<DcpDivxassets> assetList = null;
		for (KeyValuePair<DcpMedia,DcpDivxassets> it : objlist )
		{
			Story m = new Story();
			DcpMedia obj = (DcpMedia)it.getKey();
			System.out.println(obj.getMediaId().toString());
			if(!maps.containsKey(obj)){
				assetList = new LinkedList<>();
				//assetList.add(it.getValue());
				maps.put(obj, assetList); 
			 }
			maps.get(obj).add(it.getValue());
			if(maps != null){
				m.setMediaId(obj.getMediaId());
				m.setTitle(obj.getTitle());
				m.setDesc(obj.getDescription());
				m.setStatus(obj.getStatus());
				m.setUserId(obj.getUserId());
				m.setContentType(MediaBaseType.eContentType.values()[obj.getContenttype()]);
				for(DcpDivxassets a: assetList)
				{
					if(a != null){
						switch(MediaBaseType.eFileType.values()[a.getVideoformat()])
						{
						case EduStoryAudio:
							m.setRecordUrl(a.getLocation());
							break;
						}
					}
					
				}
				m.setConfigs(obj.getContentSettings());
				m.setIsPublic(obj.getIspublic() != null ? obj.getIspublic() : false);
					
				if(obj.getSnapshoturl() != null && !obj.getSnapshoturl().isEmpty()){
						m.setSnapshotUrl(Util.UrlWithHttp(obj.getSnapshoturl()));
				}
				else{
						m.setSnapshotUrl("");
				}	
							
				m.setCreateDate(obj.getDatecreated().toString());	
			}
			
			
			/*DcpDownload download = mediaDao.GetDownloadCount(obj.getMediaId());
			if(download != null){
				m.setDownloadCount(download.getDownloadCount());
			}else{
				m.setDownloadCount(0);
			}*/
			
			storyList.add(m);
		}
		
		return storyList;
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
			
			/*if (media.getUserId() != userId)
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("No permission to update the media.");
				return res;
			}*/
			
			Date now = new Date();
			//media.setMediaId(obj.getMediaId());
			media.setDatemodified(now);
			if(obj.getDesc() != null && !obj.getDesc().isEmpty()){
				media.setDescription(obj.getDesc());
			}
		
			//media.setStatus(obj.getStatus().ordinal());
			if(obj.getTitle() != null && !obj.getTitle().isEmpty()){
				media.setTitle(obj.getTitle());
			}
			
			//media.setIspublic(obj.getIsPublic());
			if (mediaDao.UpdateMedia(media) > 0)
			{
				if (obj.getIsPublic())
				{
					cachePublicMedias.invalidateAll();
				}
				if(MediaBaseType.eContentType.EduBookURL.ordinal() == media.getContenttype()){
					DcpDivxassets dcpAsset = new DcpDivxassets();
					List<DcpDivxassets> assets = mediaDao.GetDivxAsset(media.getMediaId(), null);
					if (assets != null && assets.size() > 0)
					{dcpAsset = assets.get(0);
						dcpAsset.setLocation(obj.getSmileUrl());
						dcpAsset.setDatemodified(new Date());
					}else{
						dcpAsset.setOriginalassetId(0);
						dcpAsset.setMediaId(media.getMediaId());
						dcpAsset.setLocation(obj.getSmileUrl());
						dcpAsset.setVideoformat(0);
						dcpAsset.setDatecreated(new Date());
						dcpAsset.setDatemodified(new Date());
					}

					mediaDao.CreateDivxAsset(dcpAsset); 
				}
				
				if(obj.getSign() != null && (
					MediaBaseType.eContentType.EduBook.ordinal() == media.getContenttype() ||
					MediaBaseType.eContentType.EduBookURL.ordinal() == media.getContenttype())){
					DcpMediaSign mediaSign = mediaDao.GetMediaSign(media.getMediaId());
					if(mediaSign == null){
						mediaSign = new DcpMediaSign();
						mediaSign.setDcpMedia(media);
						mediaSign.setSign(obj.getSign());
						mediaSign.setCreateDate(now);
						mediaSign.setModifyDate(now);
					}else{
						mediaSign.setSign(obj.getSign());
						mediaSign.setModifyDate(now);
					}
					
					if (mediaDao.createMediaSign(mediaSign) > 0)
						cacheMediaSigns.invalidateAll();
				}
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
				if (media.getIspublic())
				{
					cachePublicMedias.invalidateAll();
				}

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
	
	public ServiceResponse UpdateUploadInfo(String token, Upload info){
		ServiceResponse res  = new ServiceResponse();
		try{
			DcpMedia m = mediaDao.GetMedia(info.getMediaId());
			if(null == m){
				res.setResponseCode(ResponseCode.MEDIA_NOT_EXISTED);
				res.setResponseMessage("the media is not existed.");
				log.warn(String.format("MediaID NOT Exist: UpdateUploadInfo(%s)", Util.ObjectToJson(info)));
				return res;
			}
			
			log.info(String.format("UpdateUploadInfo(%s)", Util.ObjectToJson(info)));
			
			switch(info.getStatus())
			{
			case done:
			case predone:
				switch(info.getContentType())
				{
				case Gif:
				case EduBook:
				case EduStory:
					m.setStatus(MediaBaseType.eMediaStatus.done.ordinal());
					//m.setParentId(info.getLessonId());
					m.setLessonId(info.getLessonId());
					m.setContentSettings(info.getContentSettings());
					break;
				default:
					m.setStatus(MediaBaseType.eMediaStatus.uploaded.ordinal());
					break;
				}
				break;
			default:
				m.setStatus(MediaBaseType.eMediaStatus.uploading.ordinal());
				break;
			}
			
			m.setSnapshoturl("");
			if (UploadInfoHelper.NeedGenerateThumbnail(info))
			{
				TranscodeServiceHelper.ThumbnailsResponse tsh = new TranscodeServiceHelper().GenerateThumbnails(info.getUploadId(), info.getFileurl());
				if(tsh.getResponseCode() == 0 && tsh.getThumbnails() != null && tsh.getThumbnails().size() > 0){
					m.setSnapshoturl( tsh.getThumbnails().get(0).getFilename());					
				}
			}
//			if(MediaBaseType.eContentType.EduBook != info.getContentType()){
//				if(info.getStatus() == Upload.eUploadStatus.done){
//					TranscodeServiceHelper.ThumbnailsResponse tsh = new TranscodeServiceHelper().GenerateThumbnails(info.getUploadId(), info.getFileurl());
//					if(tsh.getResponseCode() == 0 && tsh.getThumbnails() != null && tsh.getThumbnails().size() > 0){
//						m.setSnapshoturl( tsh.getThumbnails().get(0).getFilename());					
//					}else{
//						m.setSnapshoturl("");
//					}
//				}else{
//					m.setSnapshoturl("");
//				}
//			}
			
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
				
				switch(info.getContentType())
				{
				case SMIL:
					{
						ServiceResponse sr = Publish(m.getUserId(), info.getMediaId());
						if(sr.getResponseCode() != ResponseCode.SUCCESS){
							log.warn(sr.getResponseMessage());
						}
						//after publish success, auto share media.
						
						if(sr.getResponseCode() == ResponseCode.SUCCESS){
							if (info.getShareJson() != null && !info.getShareJson().isEmpty())
							{
								String ss = String.format("{\"ShareOption\":%s}",new String(DatatypeConverter.parseBase64Binary(info.getShareJson())));
								ServiceResponse srh = new SocialServiceHelper().ShareMedia(ss,token);
							}
						}
						break;
					}
				case Video4Gif:
					{
						ServiceResponse sr = Publish(m.getUserId(), info);
						if(sr.getResponseCode() != ResponseCode.SUCCESS){
							log.warn(sr.getResponseMessage());
						}
						
						if(sr.getResponseCode() == ResponseCode.SUCCESS){
							if (info.getShareJson() != null && !info.getShareJson().isEmpty())
							{
								String ss = String.format("{\"ShareOption\":%s}",new String(DatatypeConverter.parseBase64Binary(info.getShareJson())));
								ServiceResponse srh = new SocialServiceHelper().ShareMedia(ss,token);
							}
						}
						break;
					}
				default:	//Gif, EduBook, EduStory. It needn't do the transcoding work.
					{
						String baseUrl = ConfigurationManager.GetInstance().SMIL_OUT_FILE_PREFIX();
						String fileNmae = info.getFileurl().substring(info.getFileurl().lastIndexOf('/'));
//						if(MediaBaseType.eFileType.EduStoryZip == info.getFileType() && info.getLessonId() > 0){
//							DcpLesson lesson = mediaDao.GetLesson(info.getLessonId());
//							if(lesson != null){
//								//lesson.setLessonZipUrl(Util.UrlWithSlashes(baseUrl) + fileNmae);
//								mediaDao.SetLesson(lesson);	
//							}
//						}
//						else
						{
							DcpDivxassets dcpAsset = new DcpDivxassets();
							
							dcpAsset.setOriginalassetId(0);
							dcpAsset.setMediaId(info.getMediaId());
						/*	String baseUrl = ConfigurationManager.GetInstance().SMIL_OUT_FILE_PREFIX();
							String fileNmae = info.getFileurl().substring(info.getFileurl().lastIndexOf('/'));*/
							dcpAsset.setLocation(Util.UrlWithSlashes(baseUrl) + fileNmae);
							
							dcpAsset.setVideoformat(info.GetFileTypeByContentType().ordinal());
							
							dcpAsset.setDatecreated(new Date());
							dcpAsset.setDatemodified(new Date());
							
							mediaDao.CreateDivxAsset(dcpAsset);
							if (info.getShareJson() != null && !info.getShareJson().isEmpty())
							{
								String ss = String.format("{\"ShareOption\":%s}",new String(DatatypeConverter.parseBase64Binary(info.getShareJson())));
								ServiceResponse srh = new SocialServiceHelper().ShareMedia(ss,token);
							}
						}
						break;
					}
				}
				
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_UPDATE_MEDIA);
				res.setResponseMessage("Faile to update media.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("UpdateUploadInfo exception: " + Util.getStackTrace(ex));
			Util.LogError(log, String.format("UpdateUploadInfo(%s)", Util.ObjectToJson(info)), ex);
		}
		return res;
	}
	
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
	public ServiceResponse Publish(int userId, Upload info)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpMedia obj = mediaDao.GetMedia(info.getMediaId());
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
				DcpOriginalasset oriAsset = mediaDao.GetUploadInfo(info.getMediaId());
				String baseUrl = ConfigurationManager.GetInstance().TranscodeServiceBaseUrl();
				TransOption option = new TransOption();
				option.setAssetId(oriAsset.getId());
				option.setContentType(info.getContentType());
				List<String> locations = new LinkedList<String>();
				locations.add(info.getFileurl());
				option.setLocations(locations);
				option.setV2gOption(info.getV2gJson());
				
				PublishOption po = new PublishOption();
				po.setTransOption(option);
				reqRet = Util.HttpPost(baseUrl + "/transcode/transcodeExt", Util.ObjectToJson(po));
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
				SysMessage msg = new SysMessage();
				msg.setMessageType(eSysMessageType.ToPerson);
				msg.setMessageCategory(MessageCategory.MEDIA_SERVICE_MESSAGE_TRANSCODE_FAILED);
				msg.setSenderId(0);
				msg.setTargetId(m.getUserId());
				msg.setContent(String.format("Media \"%s\" fails to publish. Unsupport file or bad file.", m.getTitle()));
				
				helper.SendSysMessage(msg);
				
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
				SysMessage msg = new SysMessage();
				msg.setMessageType(eSysMessageType.ToPerson);
				msg.setMessageCategory(MessageCategory.MEDIA_SERVICE_MESSAGE_TRANSCODE_FAILED);
				msg.setSenderId(0);
				msg.setTargetId(m.getUserId());
				msg.setContent(String.format("Media \"%s\" fails to publish. Please try later.", m.getTitle()));

				helper.SendSysMessage(msg);
				
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
					SysMessage msg = new SysMessage();
					msg.setMessageType(eSysMessageType.ToPerson);
					msg.setMessageCategory(MessageCategory.MEDIA_SERVICE_MESSAGE_PUBLISH_COMPLETED);
					msg.setSenderId(0);
					msg.setTargetId(m.getUserId());
					msg.setContent("Your video is ready to be viewed.");
					
					ServiceResponse sr = helper.SendSysMessage(msg);
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
	
	public TransferMediaResponse TransferMedia(int userId, TransferOption option) {
		TransferMediaResponse res = new TransferMediaResponse();
		try{
			DcpMedia m = mediaDao.GetAncestorMedia(option.getMediaId());
			
			if (m == null || m.getStatus() != MediaBaseType.eMediaStatus.done.ordinal())
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media or media status isn't 'done'.");
				return res;
			}
			
			Integer id = mediaDao.IsBookInMyLibrary(option.getDestId(), m.getMediaId());
			if (id > 0)
			{
				res.setMediaId(id);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				return res;
			}

			List<DcpDivxassets> assets = mediaDao.GetDivxAsset(m.getMediaId(), null);
			if (assets == null || assets.size() == 0)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format("Cannot find the media(%d) assets", m.getMediaId()));
				return res;
			}
			
			
			DcpMedia tm = new DcpMedia();
			tm.setContenttype(m.getContenttype());
			tm.setDatecreated(new Date());
			tm.setDatemodified(new Date());
			tm.setDeleted(m.isDeleted());
			tm.setDescription(m.getDescription());
			tm.setErrorlog(m.getErrorlog());
			tm.setExpiredate(m.getExpiredate());
			tm.setParentId(m.getMediaId());
			tm.setSnapshoturl(m.getSnapshoturl());
			tm.setStatus(m.getStatus());
			tm.setTitle(m.getTitle());
			tm.setUserId(option.getDestId());
			tm.setContenttype(m.getContenttype());
			tm.setTransferUserId(userId);
			tm.setIspublic(false);
			int mediaId = mediaDao.CreateMedia(tm);
			if(mediaId <= 0){
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Fail to create the media");
				return res;
			}
			
			int assetId = 0;
			for(DcpDivxassets asset: assets)
			{
				DcpDivxassets newAssets = new DcpDivxassets();
				newAssets.setCandownload(asset.isCandownload());
				newAssets.setCanstreaming(asset.isCanstreaming());
				newAssets.setDatecreated(new Date());
				newAssets.setDatemodified(new Date());
				newAssets.setLocation(asset.getLocation());
				newAssets.setMediaId(mediaId);
				newAssets.setOriginalassetId(asset.getOriginalassetId());
				newAssets.setStatus(asset.getStatus());
				newAssets.setVideoformat(asset.getVideoformat());
				newAssets.setVideosolution(asset.getVideosolution());
				assetId = mediaDao.CreateDivxAsset(newAssets);
			}
			if(assetId > 0){
				res.setMediaId(mediaId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");	
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to Transfer the media assets.");				
			}

		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(Util.getStackTrace(ex));
			Util.LogError(log, String.format("TransferMedia exception."), ex);
		}
		return res;
	}
	
	public LessonsResponse GetLessons(int mediaId)
	{
		LessonsResponse res = new LessonsResponse();
		
		try{
			if (mediaId <= 0)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid media Id");
				return res;
			}
			
			String key = String.format("lessons_media_%d", mediaId);
			res = (LessonsResponse)CacheManager.GetInstance().GetCacheObject(key);
			
			if (res != null)
			{
				return res;
			}
			
			res = new LessonsResponse();
			
			DcpMedia m = mediaDao.GetMedia(mediaId);
			
			if (m == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid media Id");
				return res;
			}
			
			int bookId= mediaId;
			if (m.getParentId() > 0)
			{
				bookId = m.getParentId();
			}
			
			List<Lesson> lessons = new LinkedList<Lesson>();
			
			List<DcpLesson> objs = CacheManager.GetInstance().GetMediaDcpLessons(bookId);

			if (objs != null && objs.size() > 0)
			{
				for(DcpLesson obj: objs)
				{
					lessons.add(ScoreHelper.ToLesson(obj, new Lesson(), false));
				}
				//CacheManager.GetInstance().SetMediaDcpLessons(bookId, objs);
			}
			else
			{
				List<KeyValuePair<DcpLesson, DcpLessonAsset>> kvps = mediaDao.GetLessonsCovers(bookId);
				if (kvps != null && kvps.size() > 0)
				{
					for(KeyValuePair<DcpLesson, DcpLessonAsset> kvp: kvps)
					{
						lessons.add(ScoreHelper.ToLesson(kvp, new Lesson()));
					}
				}
			}
			
			if (lessons != null && lessons.size() > 0)
			{
				res.setLessons(lessons);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				CacheManager.GetInstance().SetCacheObject(key, res);
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid parameter. Fail to get lessons by mediaId");
			}
		}
		catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("GetLessons(%d) exception.", mediaId), ex);
		}
		
		return res;
	}
	
	public LessonsResponse GetLesson(int lessonId)
	{
		LessonsResponse res = CacheManager.GetInstance().GetCacheLesson(lessonId);
		if (res != null)
			return res;
		
		res = new LessonsResponse();
		
		try{
			if (lessonId <= 0)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid lesson Id");
				return res;
			}
			
			DcpLesson obj = mediaDao.GetLesson(lessonId);
			if (obj != null)
			{
				List<Lesson> lessons = new LinkedList<Lesson>();
				lessons.add(ScoreHelper.ToLesson(obj, new Lesson(), true));
				res.setLessons(lessons);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				CacheManager.GetInstance().SetCacheLesson(lessonId, res);
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid parameter. Fail to get lesson by lessonId");
			}
		}
		catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("GetLesson(%d) exception.", lessonId), ex);
		}
		
		return res;
	}
	
	public SetResponse SetLesson(int userId, Lesson lesson)
	{
		SetResponse res = new SetResponse();
		
		try{
			DcpLesson obj = null;
			if (lesson.getLessonId() > 0)
			{
				obj = mediaDao.GetLesson(lesson.getLessonId());
				obj = ScoreHelper.ToLesson(lesson, obj);
			}
			else
			{
				if (lesson.getMediaId() <= 0)
				{
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid parameter media Id");
					return res;
				}
				
				DcpMedia m = mediaDao.GetMedia(lesson.getMediaId());
				if (m == null)
				{
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid parameter media Id");
					return res;
				}
				
				if (m.getContenttype() == eContentType.EduStory.ordinal())
				{
					if (m.getLessonId() != null && m.getLessonId() > 0)
						obj = mediaDao.GetLesson(m.getLessonId());
					else
						obj = mediaDao.GetLesson(m.getParentId(), lesson.getCategory(), lesson.getNumber());
				}
				else
				{
					if (m.getParentId() != null && m.getParentId() > 0)
						obj = mediaDao.GetLesson(m.getParentId(), lesson.getCategory(), lesson.getNumber());
					else
						obj = mediaDao.GetLesson(lesson.getMediaId(), lesson.getCategory(), lesson.getNumber());
				}
				if (obj == null)
				{
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid MediaId or Info");
					return res;
				}
				obj = ScoreHelper.ToLesson(lesson, obj);
				obj.setDcpMedia(m);
			}
			
			//int id = mediaDao.SetLesson(obj);
			res.setId(obj.getLessonId());
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("SetLesson(%s) exception.", Util.ObjectToJson(lesson)), ex);
		}
		
		return res;
	}
	
	public ServiceResponse DeleteLesson(int userId, int lessonId)
	{
		ServiceResponse res = new ServiceResponse();
		
		try{
			int nRet = mediaDao.DeleteLesson(userId, lessonId); 
			if (nRet == ResponseCode.SUCCESS)
			{
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(nRet);
				res.setResponseMessage("Fail to delete the lesson. Invalid LessonId Or no permission.");
			}
		}
		catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("DeleteLesson(%d, %d) exception.", userId, lessonId), ex);
		}
		
		return res;
	}
	
	public ServiceResponse UpdateLessonInfo(Lesson lesson)
	{
		ServiceResponse res = new ServiceResponse();
		
		try{
			DcpLesson dcpLesson =  mediaDao.GetLesson(lesson.getMediaId(), lesson.getCategory(), lesson.getNumber());
			if(dcpLesson == null){
//				TranscodeServiceHelper.ThumbnailsResponse tr = new TranscodeServiceHelper().GenerateThumbnails(0, lesson.getLessonZipUrl());
//				if(tr.getResponseCode() == ResponseCode.SUCCESS){
//					lesson.setSnapshotUrl(tr.getThumbnails().get(0).getFilename());	
//					
//				}else{
//					lesson.setSnapshotUrl("");	
//				}
				
			}
			dcpLesson = ScoreHelper.ToLesson(lesson, dcpLesson);
			if(mediaDao.SetLesson(dcpLesson) > 0){
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to UpdateLessonInfo");
			}
			
		}
		catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			
		}
		
		return res;
	}
	public DownCountResponse DownloadCount(DownloadCountOption option){
		DownCountResponse res = new DownCountResponse();
		try{
			DcpMedia media = mediaDao.GetMedia(option.getMediaId());
			if(media == null){
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid parameter media Id");
				return res;
			}
			DcpDownload download = mediaDao.GetDownloadCount(option.getMediaId());
			if(download != null){
				download.setDownloadCount(download.getDownloadCount() + 1);
				download.setModifyDate(new Date());
			}else{
				download = new DcpDownload();
				download.setDcpMedia(media);
				download.setDownloadCount(1);
				download.setModifyDate(new Date());
				download.setCreateDate(new Date());
			}
			int downloadId = mediaDao.UpdateDownloadCount(download);
			if(downloadId > 0){
				res.setDownCount(download.getDownloadCount());
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to Update DownloadCount");
			}
			
			
		}catch(Exception ex){
			
		}
		return res;
		
	}
	public void cleanPublishMediaCache(){
		cachePublicMedias.invalidateAll();
		
	}
	
	private String GenerateFileName(DcpMedia media, File file)
	{
		return String.format("b_%d_%d_%s", media.getUserId(), media.getMediaId(), RandomStringUtils.randomAlphanumeric(5));
	}
	
	public ServiceResponse UpdateUgcBook(int userId, Book book)
	{
		return null;
	}
	public ServiceResponse SetBookInfo(int mediaId,String filePath) {
		ServiceResponse res = new ServiceResponse();
		try{
			DcpMedia media = mediaDao.GetMedia(mediaId); 
			File file = new File(filePath);
			String fileName = file.getName();
			String pathName = GenerateFileName(media, file);
			String outPath = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH(); 

			String thumbnailOut = Util.UrlWithSlashes(outPath) +  pathName;

			String cmdline = "";
			
			String fileExt = "";
			int nPos = filePath.lastIndexOf(".");
			if (nPos > 0)
			{
				fileExt = filePath.substring(nPos +1);
			}
			
			if (fileExt.compareToIgnoreCase("zip") == 0)
			{
				
				File f = new File(thumbnailOut);
				if (!f.exists())
				{
					f.mkdir();
					
				}
				List<String> files=UnZipFileUtil.Unzip(filePath, thumbnailOut);
				if(files == null ||files.size() < 0){
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to unzip file");
					return res;
				}
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid file extension.");
				return res;
			}
			
			String bookPath = "";
			File fi = new File(thumbnailOut);
			File[] files = fi.listFiles();
			for(File f : files){
				if(f.isDirectory())
				{
					bookPath = f.getPath();
				}
			}
			
			List<KeyValueFourPair<String, String, String, String>> bookInfos = GetBookInfoUtil.RenameBook(bookPath);
			if(bookInfos != null && bookInfos.size() > 0){
			String key = "";
				Map<String, DcpLesson> lessons = new HashMap<String, DcpLesson>();
				//List<DcpLesson> lessons = new LinkedList<>();
				List<DcpLessonAsset> lessonAssets = new LinkedList<>();
				
				for(KeyValueFourPair<String, String, String, String> bookInfo : bookInfos){
					DcpLessonAsset lessonAsset = new DcpLessonAsset();
					lessonAsset.setContent(bookInfo.getValue3().replace("\\","/").replace(Util.UrlWithSlashes(outPath), ""));
					if(lessonAsset.getContent().indexOf("P0.jpg") > 0 ){
						key = String.format("%d_%s_%d", mediaId, bookInfo.getKey().substring(0, 1), Integer.parseInt(bookInfo.getValue1()));
						if (!lessons.containsKey(key))
						{
							DcpLesson lesson = new DcpLesson();
							lesson.setCategory(bookInfo.getKey().substring(0, 1));
							lesson.setCategoryTitle(bookInfo.getKey().substring(Util.getStringIndex(bookInfo.getKey()) + 1));
							lesson.setNumber(Integer.parseInt(bookInfo.getValue1()));
							lesson.setTitle(bookInfo.getValue2());
							lesson.setDatecreated(new Date());
							lesson.setDatemodified(new Date());
							lesson.setSnapshoturl(lessonAsset.getContent());	
						
							lessons.put(key, lesson);
						}
						DcpLesson lesson = lessons.get(key);
						lesson.setSnapshoturl(lessonAsset.getContent());	
					}
					else if (lessonAsset.getContent().indexOf(".plist") > 0 )
					{
						key = String.format("%d_%s_%d", mediaId, bookInfo.getKey().substring(0, 1), Integer.parseInt(bookInfo.getValue1()));
						if (!lessons.containsKey(key))
						{
							DcpLesson lesson = new DcpLesson();
							lesson.setCategory(bookInfo.getKey().substring(0, 1));
							lesson.setCategoryTitle(bookInfo.getKey().substring(Util.getStringIndex(bookInfo.getKey()) + 1));
							lesson.setNumber(Integer.parseInt(bookInfo.getValue1()));
							lesson.setTitle(bookInfo.getValue2());
							lesson.setDatecreated(new Date());
							lesson.setDatemodified(new Date());
							
							lessons.put(key, lesson);
						}
						
						DcpLesson lesson = lessons.get(key);
						String configinfo = PListFileUtil.PListFile2String(Util.UrlWithSlashes(outPath) + lessonAsset.getContent());
						System.out.println(configinfo);
						lesson.setConfig(configinfo);
						lessons.put(key, lesson);
					}
					
					switch(lessonAsset.getContent().substring(lessonAsset.getContent().lastIndexOf(".") + 1).toLowerCase()){
						case "jpg" :
							lessonAsset.setAssettype(eFileType.EduLessonPhoto.ordinal());
							break;
						case "mp3" :
							lessonAsset.setAssettype(eFileType.EduLessonAudio.ordinal());
							break;
						case "plist" :
							lessonAsset.setAssettype(eFileType.EduLessonConfig.ordinal());
							break;
						case "mp4" :
							lessonAsset.setAssettype(eFileType.EduLessonVideo.ordinal());
							break;
						default :
							lessonAsset.setAssettype(eFileType.other.ordinal());
							break;
						
					}
					lessonAsset.setDatecreated(new Date());
					lessonAsset.setDatemodified(new Date());
					lessonAssets.add(lessonAsset);
				}
				if(lessons.size() > 0){			
					List<DcpLesson> ll= new LinkedList<DcpLesson>(lessons.values());	
					int ret = mediaDao.SetLessons(mediaId,ll );
					if(ret > 0){
						for(int i = 0; i < lessonAssets.size(); i++){
							String content = lessonAssets.get(i).getContent();
							String num = content.substring(content.lastIndexOf("/L") + 2, content.lastIndexOf("/"));
							String category =  content.substring(content.lastIndexOf("/C") + 2, content.lastIndexOf("/L"));
							String lessonKey = String.format("%d_%s_%s", mediaId, category,num);							
							DcpLesson obj = lessons.get(lessonKey);
							lessonAssets.get(i).setDcpLesson(lessons.get(lessonKey));
						}
						mediaDao.DeleteDcpLessonAsset(lessonAssets);
						int aet = mediaDao.CreateDcpLessonAsset(lessonAssets);
						if(aet <= 0){
							res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
							res.setResponseMessage("add book file error");
						}
					}
				}
			}
			String bookPic = GetBookInfoUtil.getBookPic(bookPath);
			
			media.setSnapshoturl(bookPic.replace("\\","/").replace(outPath, ""));
			if(mediaDao.UpdateMedia(media) > 0){
				cleanPublishMediaCache();
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("add book error");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Util.LogError(log, String.format("Exception SetBookInfo(%d, %s)", mediaId, filePath), ex);
		}
		return res;
	}
	
	public StoryInfoResponse GetStoryInfo(int mediaId){
		StoryInfoResponse res = new StoryInfoResponse();
		try{
			Story story = GetStory(mediaId);
			if(story != null && story.getLessonId() != null){
				LessonsResponse lr = GetLesson(story.getLessonId());
				if(ResponseCode.SUCCESS == lr.getResponseCode() && lr.getLessons() != null && lr.getLessons().size() > 0){
					res.setStory(story);
					res.setLessons(lr.getLessons());
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("success");
					
				}else{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage(String.format("Fail to get lesson info for lessonId %d", mediaId));
				}
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage(String.format("Fail to get StoryInfo for mediaId %d", mediaId));
			}
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Fail to get StoryInfo");
			Util.LogError(log, "Fail to get StoryInfo", ex);
		}
		return res;
	}
	
	public List<Media> GetAllMedias(eAppType appType,int startPos,int endPos){
		List<Media> medias = null;
		try{
			String allMediasKey = String.format("allMedias_%d_%d_%d", appType.ordinal(), startPos, endPos);
			medias = cacheAllMedias.getIfPresent(allMediasKey);
			if(medias == null){
				List<Integer> contentTypes = new LinkedList<Integer>();
				if (appType == DcpBaseType.eAppType.Yingyueguan)
				{
					contentTypes.add(MediaBaseType.eContentType.EduBook.ordinal());
					contentTypes.add(MediaBaseType.eContentType.EduBookURL.ordinal());
				}
				
				
				List<KeyValuePair<DcpMedia,DcpDivxassets>> objs = mediaDao.GetAllMedias(contentTypes);
				if(objs == null)
					return medias;
				int nIndex = 0;
				medias = new LinkedList<>();
				for(KeyValuePair<DcpMedia,DcpDivxassets> obj: objs)
				{
					DcpMedia objMedia = obj.getKey();	
					if (nIndex < startPos)
					{
						++nIndex;
						continue;
					}
					else if (nIndex > endPos)
						break;
					
					++nIndex;
					
					Media m = MediaHelper.ToMedia(obj, new Media());
					
					if (objMedia.getUserId() > 0)
					{
						User user = UserHelper.GetUser(objMedia.getUserId());
						if (user != null)
						{
							m.setUsername(user.getUsername());
							m.setNickname(user.getNickname());
						}
					}
					if (objMedia.getParentId() == 0)
					{				
						String signKey = String.format("ms_%d", objMedia.getMediaId());
						DcpMediaSign mediaSign = cacheMediaSigns.getIfPresent(signKey);
						if (mediaSign == null)
						{
							mediaSign = mediaDao.GetMediaSign(objMedia.getMediaId());
							if (mediaSign != null)
								cacheMediaSigns.put(signKey, mediaSign);
						}
						if(mediaSign != null){
							m.setSign(mediaSign.getSign());
						}
					}
					
					medias.add(m);
				}
			}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return medias;
	}
	
	public int SetMediaPublic(boolean isPublic,List<String> mediaIds){
		
		try{
			int ret = mediaDao.SetMediaIsPublic(isPublic, mediaIds);
			if(ret >= 0){
				cacheAllMedias.invalidateAll();
				return ret;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return -1;
	}
	
	public ServiceResponse setPlist2String(List<String> mediaIds){
		ServiceResponse res = new ServiceResponse();
		try{
			String fileServerUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX();
			String fileOutUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH();
			for(String mediaId : mediaIds){
				List<DcpLesson> lessons =  mediaDao.GetLessons(Integer.parseInt(mediaId));
				if(lessons != null){
					for(DcpLesson lesson : lessons){
						try{
							Lesson le = ScoreHelper.ToLesson(lesson, new Lesson(), true);
							if(le != null){
								String plistUrl = le.getPlistUrl();
								String plistPath = plistUrl.replace(Util.UrlWithSlashes(fileServerUrl), Util.UrlWithSlashes(fileOutUrl));
								String config = PListFileUtil.PListFile2String(plistPath);
								if(config.isEmpty()){
									res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
									res.setResponseMessage(String.format("lessonAssets plist file not exits for lessonId : %d ,filepath %s" , lesson.getLessonId(),plistPath));
									return res;
								}
								lesson.setConfig(config);
								int ret = mediaDao.SetLesson(lesson);
								if(ret > 0){
									res.setResponseCode(ResponseCode.SUCCESS);
									res.setResponseMessage("success");
								}
							}	
						}catch(Exception ex){
							res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
							res.setResponseMessage("fail to set plist 2 String" + Util.getStackTrace(ex));
							Util.LogError(log, String.format("Convert plist to string exception. %s", Util.ObjectToJson(lesson)), ex);
						}
						
					}
				
				}else{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("lessons not exits for mediaId :" + mediaId);
				}
			}
		
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("fail to set plist 2 String" + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception to setPlist2String(%s)", Util.ObjectToJson(mediaIds)), ex);
		}
		return res;
	}
}
