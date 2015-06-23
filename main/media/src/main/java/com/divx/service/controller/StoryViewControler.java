package com.divx.service.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.divx.service.ConfigurationManager;
import com.divx.service.UserHelper;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.manager.ComparatorPhotoUrl;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.manager.RankStatManager;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.Media;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.Story;
import com.divx.service.model.User;
import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.LessonsResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Controller
@RequestMapping("/ss")
public class StoryViewControler {
	private static final Cache<Integer, StoryInfo> cacheStory = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(6, TimeUnit.HOURS).build();
	
	private MediaManager mediaManager;
	private RankStatManager rankStatManager;
	
	@Autowired
	public void setMediaManager(MediaManager mediaManager)
	{
		this.mediaManager = mediaManager; 
	}
	
	@Autowired
	public void setRankStatManager(RankStatManager rankStatManager)
	{
		this.rankStatManager = rankStatManager; 
	}
	
	@RequestMapping("/pad.do")
	public ModelAndView storyViewPad(int mediaId, HttpServletRequest request)
	{
		return storyView(mediaId, false, request);
	}
	
	@RequestMapping("/phone.do")
	public ModelAndView storyViewPhone(int mediaId, HttpServletRequest request)
	{
		return storyView(mediaId, true, request);
	}
	
	@RequestMapping("/index.do")
	public ModelAndView storyViewIndex(int mediaId)
	{
		return new ModelAndView();
	}
	
	@RequestMapping(value = "/hit.do", method = RequestMethod.GET)
	@ResponseBody
	public String addStoryPlayHit(int mediaId)
	{
		Map<String, String> ret = new HashMap<String, String>();
		Integer count = rankStatManager.AddStoryPlayHit(mediaId);
		ret.put("playCount", count.toString());
		
		return count.toString();
	}
	public ModelAndView storyView(int mediaId, boolean bPhone, HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		
		try
		{
			int playCount = rankStatManager.GetStoryPlayHit(mediaId);

			
			String url = request.getRequestURL().toString() + "?" + request.getQueryString();
			
			WxTicket ticket = TxHelper.sign(url);
			
			StoryInfo info = cacheStory.getIfPresent(mediaId);
			if (info != null)
			{
				if (playCount >= 0)
					info.setPlayCount(playCount);
				else
					info.setPlayCount(1);
				
				info.setTicket(ticket);
				return info.FillModelAndView(mav);
			}
			
			int code = ResponseCode.ERROR_INVALID_PARAMETER;
			String message = "";
			
			Story story = mediaManager.GetStory(mediaId);
			if (story == null || story.getContentType() != eContentType.EduStory)
			{
				code = ResponseCode.ERROR_INVALID_PARAMETER;
				message = String.format("无效故事信息(%d)", mediaId);
				mav.addObject("code", code);
				mav.addObject("message", message);
				return mav;
			}
			
			if (story.getLessonId() == null || story.getLessonId() <= 0)
			{
				code = ResponseCode.ERROR_INVALID_PARAMETER;
				message = "无效课程信息";
				mav.addObject("code", code);
				mav.addObject("message", message);
				return mav;
			}
			
			LessonsResponse lesson = mediaManager.GetLesson(story.getLessonId());
			if (lesson.getResponseCode() != ResponseCode.SUCCESS)
			{
				mav.addObject("code", lesson.getResponseCode());
				mav.addObject("message", lesson.getResponseMessage());
				return mav;
			}
			
			info = new StoryInfo();
			info.setPlayCount(playCount);
			List<KeyValuePair<Float, String>> imgs = new LinkedList<KeyValuePair<Float, String>>();
			
			if (lesson.getLessons() != null && lesson.getLessons().size() > 0 && story.getConfigs() != null && !story.getConfigs().isEmpty())
			{
				List<String> pUrls = lesson.getLessons().get(0).getPhotoUrls();
				info.setCoverUrl(lesson.getLessons().get(0).getSnapshotUrl());
				String[] tms = story.getConfigs().split(",");
				float base = 0;
				for(int i = 0; i < tms.length && i < pUrls.size(); ++i)
				{
					if (tms[i] != null && !tms[i].isEmpty())
					{
						float cur = Float.parseFloat(tms[i]);//Integer.parseInt(tms[i]);
						float dur = cur - base;
						if (dur > 0)
						{
							imgs.add(new KeyValuePair<Float, String>(dur, pUrls.get(i)));
							base = cur;
						}
					}
				}
			}
			
			String username;
			User user = UserHelper.GetUser(story.getUserId());
			if (user != null)
				username = user.getNickname();
			else
				username = String.format("%d", story.getUserId());
			
			
			String d = story.getCreateDate();

			String[] ds = d.split(" ");
			if (ds.length >= 2)
			{
				info.setDate(ds[0]);
				info.setTime(ds[1].substring(0, 8));
			}
			else if (ds.length == 1)
			{
				info.setDate(ds[0]);
				info.setTime("00:00:00");
			}
			else
			{
				info.setDate("0000-00-00");
				info.setTime("00:00:00");
			}
			info.setImgs(imgs);
			info.setUsername(username);
			info.setTitle(story.getTitle());
			info.setRecordUrl(story.getRecordUrl());
			if (user.getPhotourl() != null && !user.getPhotourl().isEmpty())
				info.setPhotoUrl(user.getPhotourl());
			else
				info.setPhotoUrl(Util.UrlWithHttp("def_photo.jpg"));
			info.setCode(ResponseCode.SUCCESS);
			info.setMessage("Success");
			info.setTicket(ticket);
			
			cacheStory.put(mediaId, info);

			return info.FillModelAndView(mav);
		}
		catch(Exception ex)
		{
			mav.addObject("code", ResponseCode.ERROR_INTERNAL_ERROR);
			mav.addObject("message", ex.getMessage());
		}
		return mav;
	}
	
	private class StoryInfo
	{
		private int code;
		private String message;
		private String title;
		private String username;
		private String recordUrl;
		private String photoUrl;
		private String date;
		private String time;
		private String coverUrl;
		private int playCount;
		private WxTicket ticket;
		private List<KeyValuePair<Float, String>> imgs;
		
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public List<KeyValuePair<Float, String>> getImgs() {
			return imgs;
		}
		public void setImgs(List<KeyValuePair<Float, String>> imgs) {
			this.imgs = imgs;
		}
		
		public String getRecordUrl() {
			return recordUrl;
		}
		public void setRecordUrl(String recordUrl) {
			this.recordUrl = recordUrl;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
		public ModelAndView FillModelAndView(ModelAndView mav)
		{
			mav.addObject("title", getTitle());
			mav.addObject("imgs", getImgs());			
			mav.addObject("code", getCode());
			mav.addObject("message", getMessage());
			mav.addObject("username", getUsername());
			mav.addObject("recordUrl", getRecordUrl());
			mav.addObject("photoUrl", getPhotoUrl());
			mav.addObject("date", getDate());
			mav.addObject("time", getTime());
			mav.addObject("coverUrl", getCoverUrl());
			mav.addObject("playCount", getPlayCount());
			mav.addObject("ticket", getTicket());
			
			return mav;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getCoverUrl() {
			return coverUrl;
		}
		public void setCoverUrl(String coverUrl) {
			this.coverUrl = coverUrl;
		}
		public int getPlayCount() {
			return playCount;
		}
		public void setPlayCount(int playCount) {
			this.playCount = playCount;
		}
		public WxTicket getTicket() {
			return ticket;
		}
		public void setTicket(WxTicket ticket) {
			this.ticket = ticket;
		}
	}
}
