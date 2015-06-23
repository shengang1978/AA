package com.divx.service;

import java.util.concurrent.TimeUnit;

import com.divx.service.model.ResponseCode;
import com.divx.service.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class UserHelper {
	private static final Cache<String, User> cacheUsers = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(60, TimeUnit.MINUTES).build();
	
	public static User GetUser(int userId)
	{
		String key = String.format("cu_%d", userId);
		User user = cacheUsers.getIfPresent(key);
		if (user == null)
		{
			UserServiceHelper userHelper = new UserServiceHelper();
			UserServiceHelper.UserResponse uRes;
			try {
				uRes = userHelper.GetUser(userId);
				if (uRes.getResponseCode() == ResponseCode.SUCCESS)
				{
					user = uRes.getUser();
					cacheUsers.put(key, user);
				}
			} 
			catch (Exception e) {
			}
		}
		return user;
	}
}
