package com.divx.service.domain.manager;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.divx.service.ConfigurationManager;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.model.DcpDivxassets;
import com.divx.service.domain.model.DcpMedia;
import com.divx.service.domain.model.DcpStoryplayTotalstat;
import com.divx.service.model.DcpBaseType;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.Media;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.Story;
import com.divx.service.model.StoryPlayStat;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class MediaHelper {
	private static final Cache<String, HashMap<String, Integer>> cacheAppVersionBooks = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(60, TimeUnit.MINUTES).build();
	
	public static Media ToMedia(DcpMedia src, Media dst)
	{	
		dst.setMediaId(src.getMediaId());
		dst.setTitle(src.getTitle());
		dst.setDesc(src.getDescription());
		dst.setStatus(src.getStatus());
		dst.setIsPublic(src.getIspublic());
		dst.setParentId(src.getParentId());
		
		dst.setUserId(src.getUserId());
		dst.setCreateDate(src.getDatecreated().toString());
		dst.setContentType(eContentType.values()[src.getContenttype()]);
		
		if(src.getSnapshoturl() != null && !src.getSnapshoturl().isEmpty()){
			dst.setSnapshotUrl(Util.UrlWithHttp(src.getSnapshoturl()));
		}
		else{
			dst.setSnapshotUrl("");
		}

		return dst;
	}
	
	public static Story ToStory(DcpMedia src, Story dst)
	{
		dst.setMediaId(src.getMediaId());
		dst.setTitle(src.getTitle());
		dst.setDesc(src.getDescription());
		dst.setStatus(src.getStatus());
		dst.setIsPublic(src.getIspublic());
		dst.setParentId(src.getParentId() == null ? 0 : src.getParentId());
		
		dst.setUserId(src.getUserId());
		dst.setCreateDate(src.getDatecreated().toString());
		dst.setContentType(eContentType.values()[src.getContenttype()]);
		
		dst.setLessonId(src.getLessonId());
		if(src.getSnapshoturl() != null && !src.getSnapshoturl().isEmpty()){
			dst.setSnapshotUrl(Util.UrlWithHttp(src.getSnapshoturl()));
		}
		else{
			dst.setSnapshotUrl("");
		}
		
		dst.setConfigs(src.getContentSettings());

		return dst;
	}
	
	public static Media ToMedia(KeyValuePair<DcpMedia,DcpDivxassets> obj, Media dst)
	{	
		DcpMedia src = obj.getKey();
		
		dst.setMediaId(src.getMediaId());
		dst.setTitle(src.getTitle());
		dst.setDesc(src.getDescription());
		dst.setStatus(src.getStatus());
		dst.setIsPublic(src.getIspublic());
		dst.setParentId(src.getParentId());
		
		dst.setUserId(src.getUserId());
		dst.setCreateDate(src.getDatecreated().toString());
		dst.setContentType(eContentType.values()[src.getContenttype()]);
		
		if(src.getSnapshoturl() != null && !src.getSnapshoturl().isEmpty()){
			dst.setSnapshotUrl(Util.UrlWithHttp(src.getSnapshoturl()));
		}
		else{
			dst.setSnapshotUrl("");
		}

		if (obj.getValue() != null)
			dst.setSmileUrl(Util.UrlWithHttp(obj.getValue().getLocation()));
		
		return dst;
	}

	public static int AppVersionToBookBitwise(String appVersion)
	{
		if (appVersion == null || appVersion.isEmpty())
		{
			return DcpBaseType.BookBitwise.Normal;
		}
	
		String key = String.format("appversionbooks");
		HashMap<String, Integer> mapAppVersionSettings = cacheAppVersionBooks.getIfPresent(key);
		if (mapAppVersionSettings == null || mapAppVersionSettings.size() == 0)
		{
			String appVersionBooks = ConfigurationManager.GetInstance().GetConfigValue("Media_AppVersion_Books", "");
			if (appVersionBooks == null || appVersionBooks.isEmpty())
				return DcpBaseType.BookBitwise.Normal;
		
			String[] avbs = appVersionBooks.split(",");
			
			mapAppVersionSettings = new HashMap<String, Integer>();
			for(String obj: avbs)
			{
				try
				{
					String[] kvp = obj.split(":");
					if (kvp != null && kvp.length >= 2)
						mapAppVersionSettings.put(kvp[0], Integer.parseInt(kvp[1]));
				}
				catch(Exception ex)
				{	
				}
			}
			if (mapAppVersionSettings.size() > 0)
			{
				cacheAppVersionBooks.put(key, mapAppVersionSettings);
			}
		}
		
		if (mapAppVersionSettings.containsKey(appVersion))
			return mapAppVersionSettings.get(appVersion);
		
		return DcpBaseType.BookBitwise.Normal;
	}
	
	public static StoryPlayStat ToStoryPlayStat(DcpStoryplayTotalstat src, StoryPlayStat dest)
	{
		DcpMedia m = src.getDcpMedia();
		dest.setMediaId(m.getMediaId());
		dest.setDate(src.getDatemodified().toString());
		dest.setPlayCount(src.getPlaycount());
		dest.setSnapshoturl(src.getDcpLesson().getSnapshoturl());
		dest.setTitle(m.getTitle());
		dest.setDesc(m.getDescription());
		return dest;
	}
}
