package com.divx.service.domain.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.divx.service.domain.model.ShareDbInfo;
import com.divx.service.model.QueryOption;
import com.divx.service.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheManager {
	private static CacheManager instance;
	static {
		instance = new CacheManager();
	}
	
	public static CacheManager GetInstance(){
		return instance;
	}
	
	public void SetCacheSharesOfGroup(int groupId, List<ShareDbInfo> obj)
	{
		String key = String.format("sig_%d", groupId);
		cache.put(key, obj);
	}
	
	public List<ShareDbInfo> GetCacheSharesOfGroup(int groupId)
	{
		String key = String.format("sig_%d", groupId);
		return cache.getIfPresent(key);
	}
	
	public void ClearCacheSharesOfGroup(int groupId)
	{
		String key = String.format("sig_%d", groupId);
		cache.invalidate(key);
	}
	
	public void SetCacheMyShares(int userId, QueryOption.QueryType qt, List<ShareDbInfo> obj)
	{
		String key = String.format("ms_%d_%d", userId, qt.ordinal());
		cache.put(key, obj);
	}
	
	public List<ShareDbInfo> GetCacheMyShares(int userId, QueryOption.QueryType qt)
	{
		String key = String.format("ms_%d_%d", userId, qt.ordinal());
		return cache.getIfPresent(key);
	}
	
	public void ClearCacheMyShares(int userId)
	{
		List<String> keys = new LinkedList<String>();
		for (QueryOption.QueryType qt : QueryOption.QueryType.values()) {
			keys.add(String.format("ms_%d_%d", userId, qt.ordinal()));
		}
		cache.invalidateAll(keys);
	}
	
	public void ClearCacheMySharesOfAllFriends(int userId)
	{
		List<User> users = GetCacheMyFriends(userId);
		if (users != null)
		{
			for(User u: users)
				ClearCacheMyShares(u.getUserId());
		}
		else
		{
			cache.invalidateAll();
		}
	}
	
	public List<User> GetCacheMyFriends(int userId)
	{
		String key = String.format("mf_%d", userId);
		return cacheMyFriends.getIfPresent(key);
	}
	
	public void SetCacheMyFriend(int userId, List<User> user)
	{
		String key = String.format("mf_%d", userId);
		cacheMyFriends.put(key, user);
	}
	
	public void ClearCacheMyFriend(int userId)
	{
		String key = String.format("mf_%d", userId);
		cacheMyFriends.invalidate(key);
	}
	
	private Cache<String, List<ShareDbInfo>> cache = CacheBuilder
			.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS)
			.build();
	private Cache<String, List<User>> cacheMyFriends = CacheBuilder
			.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS)
			.build();
}
