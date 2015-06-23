package com.divx.service.domain.manager;

import java.util.concurrent.TimeUnit;

import com.divx.service.model.KeyValuePair;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class LikePointCache {
	private static Cache<String, Integer> cacheMediaPoint = CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(24, TimeUnit.HOURS).build();
	private static Cache<String, KeyValuePair<Integer, Integer>> cacheShareActivity = CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(24, TimeUnit.HOURS).build();
	public static int GetMediaLikePoint(int mediaId)
	{
		String key = String.format("mp_%d", mediaId);
		Integer point = cacheMediaPoint.getIfPresent(key);
		if (point != null)
			return point;
		
		return 0;
	}
	
	public static int UpdateMediaLikePoint(int mediaId, int point)
	{
		String key = String.format("mp_%d", mediaId);

		cacheMediaPoint.put(key, point);
		
		return point;
	}
	
	public static KeyValuePair<Integer, Integer> GetShareActivity(int shareId)
	{
		String key = String.format("sa_%d", shareId);
		return cacheShareActivity.getIfPresent(key);
	}
	
	public static void UpdateShareActivity(int shareId, int commentCount, int likeCount)
	{
		String key = String.format("sa_%d", shareId);
		KeyValuePair<Integer, Integer> kvp = cacheShareActivity.getIfPresent(key);
		if (kvp == null)
		{
			kvp = new KeyValuePair<Integer, Integer>(commentCount, likeCount);
		}
		else
		{
			kvp.setKey(commentCount);
			kvp.setValue(likeCount);
		}
		cacheShareActivity.put(key, kvp);
	}
}


