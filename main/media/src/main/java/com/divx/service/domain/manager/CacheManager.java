package com.divx.service.domain.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.divx.service.domain.model.DcpLesson;
import com.divx.service.model.edu.EduStatResponse;
import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.LessonsResponse;
import com.divx.service.model.edu.ScoresResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheManager {
	public static CacheManager GetInstance()
	{
		return instance; 
	}
	
	public Object GetCacheObject(String key)
	{
		return cacheMediaLessons.getIfPresent(key);
	}
	
	public void SetCacheObject(String key, Object obj)
	{
		cacheMediaLessons.put(key, obj);
	}
	
	public List<DcpLesson> GetMediaDcpLessons(int mediaId)
	{
		return cacheMediaDcpLessons.getIfPresent(mediaId);
	}
	
	public void SetMediaDcpLessons(int mediaId, List<DcpLesson> objs)
	{
		cacheMediaDcpLessons.put(mediaId, objs);
	}
	
	public ScoresResponse GetCacheScoresOfLesson(int userId, int lessonId)
	{
		String key = String.format("sl_%d_%d", userId, lessonId);
		
		return (ScoresResponse)cacheScoreObjects.getIfPresent(key);
	}
	
	public void SetCacheScoresOfLesson(int userId, int lessonId, ScoresResponse res)
	{
		String key = String.format("sl_%d_%d", userId, lessonId);
		scoreKeysLock.lock();
		
		try
		{
			Set<String> keys = null;
			if (!mapScoreKeys.containsKey(userId))
			{	
				keys = new java.util.TreeSet<String>();
				mapScoreKeys.put(userId, keys);
			}
			else
			{
				keys = mapScoreKeys.get(userId); 
			}
			
			keys.add(key);
		}
		finally
		{
			scoreKeysLock.unlock();
		}
		cacheScoreObjects.put(key, res);
	}
	
	public ScoresResponse GetCacheScoresOfMedia(int userId, int mediaId)
	{
		String key = String.format("sm_%d_%d", userId, mediaId);
		
		return (ScoresResponse)cacheScoreObjects.getIfPresent(key);
	}
	
	public void SetCacheScoresOfMedia(int userId, int mediaId, ScoresResponse res)
	{
		String key = String.format("sm_%d_%d", userId, mediaId);
		scoreKeysLock.lock();
		
		try
		{
			Set<String> keys = null;
			if (!mapScoreKeys.containsKey(userId))
			{	
				keys = new java.util.TreeSet<String>();
				mapScoreKeys.put(userId, keys);
			}
			else
			{
				keys = mapScoreKeys.get(userId); 
			}
			
			keys.add(key);
		}
		finally
		{
			scoreKeysLock.unlock();
		}
		cacheScoreObjects.put(key, res);
	}
	
	public EduStatResponse GetCacheEduStat(int userId)
	{
		String key = String.format("es_%d", userId);
		return (EduStatResponse)cacheScoreObjects.getIfPresent(key);
	}
	
	public void SetCacheEduStat(int userId, EduStatResponse res)
	{
		String key = String.format("es_%d", userId);
		scoreKeysLock.lock();
		
		try
		{
			Set<String> keys = null;
			if (!mapScoreKeys.containsKey(userId))
			{	
				keys = new java.util.TreeSet<String>();
				mapScoreKeys.put(userId, keys);
			}
			else
			{
				keys = mapScoreKeys.get(userId); 
			}
			
			keys.add(key);
		}
		finally
		{
			scoreKeysLock.unlock();
		}
		cacheScoreObjects.put(key, res);
	}
	
	public void ScoreCacheInvalidate(int userId)
	{
		scoreKeysLock.lock();
		
		try
		{
			if (mapScoreKeys.containsKey(userId))
			{	
				Set<String> keys = mapScoreKeys.get(userId);
				cacheScoreObjects.invalidateAll(keys);
				keys.clear();
			}
		}
		finally
		{
			scoreKeysLock.unlock();
		}
	}
	
	public void SetCacheLesson(int lessonId, LessonsResponse res)
	{
		String key = String.format("l_%d", lessonId);
		cacheLessons.put(key, res);
	}
	
	public LessonsResponse GetCacheLesson(int lessonId)
	{
		String key = String.format("l_%d", lessonId);
		return cacheLessons.getIfPresent(key);
	}
	
	
	private static CacheManager instance;
	static {
		instance = new CacheManager();
	}
	
	Map<Integer, Set<String>> mapScoreKeys = new TreeMap<Integer, Set<String>>();
	Lock scoreKeysLock = new ReentrantLock(); 
	private Cache<String, Object> cacheScoreObjects = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(6, TimeUnit.HOURS).build();
	private Cache<String, Object> cacheMediaLessons = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).build();
	private Cache<Integer, List<DcpLesson>> cacheMediaDcpLessons = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).build();
	private Cache<String, LessonsResponse> cacheLessons = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(24, TimeUnit.HOURS).build();
}
