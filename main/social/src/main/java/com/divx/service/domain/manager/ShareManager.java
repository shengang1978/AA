package com.divx.service.domain.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MessageServiceHelper;
import com.divx.service.UserHelper;
import com.divx.service.Util;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.dao.GroupDao;
import com.divx.service.domain.dao.ShareDao;
import com.divx.service.domain.dao.ShareHistoryDao;
import com.divx.service.domain.model.ActivityDbInfo;
import com.divx.service.domain.model.DcpShare;
import com.divx.service.domain.model.DcpShareUser;
import com.divx.service.domain.model.OsfComments;
import com.divx.service.domain.model.OsfProjects;
import com.divx.service.domain.model.OsfTeamMembers;
import com.divx.service.domain.model.ShareDbInfo;
import com.divx.service.model.ActivitiesResponse;
import com.divx.service.model.Activity;
import com.divx.service.model.DcpBaseType;
import com.divx.service.model.GetMediaResult;
import com.divx.service.model.GroupRole;
import com.divx.service.model.KeyValueFourPair;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;
import com.divx.service.model.Like;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.QueryOption.MediaType;
import com.divx.service.model.edu.StoryInfoResponse;
import com.divx.service.model.BaseSocialType;
import com.divx.service.model.BaseSocialType.ActionType;
import com.divx.service.model.MediaResponse;
import com.divx.service.model.MessageCategory;
import com.divx.service.model.QueryOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.Share;
import com.divx.service.model.ShareOption;
import com.divx.service.model.SharesResponse;
import com.divx.service.model.SysMessage;
import com.divx.service.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class ShareManager {

	private ShareDao shareDao;
	private ShareHistoryDao shareHistoryDao;
	private GroupDao groupDao;
	private FriendDao friendDao;

	@Autowired
	public void setShareDao(ShareDao shareDao) {
		this.shareDao = shareDao;
	}

	@Autowired
	public void ShareHistoryDao(ShareHistoryDao shareHistoryDao) {
		this.shareHistoryDao = shareHistoryDao;
	}

	@Autowired
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Autowired
	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}
	private static final Logger log = Logger.getLogger(ShareManager.class);
	public static final Cache<String, MediaResponse> shareCache = CacheBuilder
			.newBuilder().maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	public ServiceResponse ShareMedia(int userId, ShareOption option) {
		ServiceResponse res = new ServiceResponse();
		try {
			String content = "Share media";
			DcpShare obj = new DcpShare();
			DcpShareUser shareUser = new DcpShareUser();
			// Get the media info from the media server
			//
			MediaResponse mediaRet = null;
			String key = String.format("sharemedia_%d", option.getMediaId());
			mediaRet = shareCache.getIfPresent(key);
			if (mediaRet == null) {
				String baseUrl = ConfigurationManager.GetInstance()
						.MediaServiceBaseUrl();
				String reqUrl = String.format("%s/media/%d", baseUrl,
						option.getMediaId());

				String jsonRet = Util.HttpGet(reqUrl);

				mediaRet = Util.JsonToObject(jsonRet,
						MediaResponse.class);
				if (mediaRet.getResponseCode() != ResponseCode.SUCCESS) {
					res.setResponseCode(mediaRet.getResponseCode());
					res.setResponseMessage(mediaRet.getResponseMessage());
					return res;
				}
				shareCache.put(key, mediaRet);
				obj.setSnapshoturl(mediaRet.getMedia().getSnapshotUrl());

			}
			int lessonId = 0;
			String snapshoturl = "";
			MediaBaseType.eContentType mediaType = mediaRet.getMedia().getContentType();
			if(MediaBaseType.eContentType.EduStory == mediaType){
				StoryInfoResponse storyInfo = new StoryInfoResponse();
				String baseUrl = ConfigurationManager.GetInstance()
						.MediaServiceBaseUrl();
				String reqUrl = String.format("%s/media/GetStory/%d", baseUrl,
						option.getMediaId());
				
				String jsonRet = Util.HttpGet(reqUrl);
				storyInfo = Util.JsonToObject(jsonRet, StoryInfoResponse.class);
				lessonId = storyInfo.getStory().getLessonId();
				snapshoturl = storyInfo.getStory().getSnapshotUrl();
				obj.setSnapshoturl(snapshoturl);
			}
			MediaBaseType.eMediaStatus mediaStatus = mediaRet.getMedia()
					.getStatus();
			if (mediaStatus != MediaBaseType.eMediaStatus.done
					&& mediaStatus != MediaBaseType.eMediaStatus.publishing) {
				res.setResponseCode(ResponseCode.ERROR_MEDIA_STATUS_TO_SHARE);
				res.setResponseMessage(String
						.format("\"done\" or \"publishing\" media status is allowed to share. Current status is \"%s\". ",
								mediaStatus == null ? "none" : mediaStatus
										.toString()));
				if(mediaStatus == null){
					log.info("share media error: \"done\" or \"publishing\" media status is allowed to share. Current status is none. " + Util.ObjectToJson(mediaRet));
				}
				
				return res;
			}
			obj.setStatus(mediaStatus.ordinal());
			switch (option.getShareType()) {
			case all:
				// public the media to all.
				obj.setSharetype(option.getShareType().ordinal());
				//obj.setGroupId(0);
				
				shareUser.setFriendId(0);
				shareUser.setGroupId(0);
				break;
			case friend:
				obj.setSharetype(option.getShareType().ordinal());
				//obj.setFriendId(option.getDestId());
				shareUser.setFriendId(option.getDestId());
				User u = UserHelper.GetUser(userId);
				User u2 = UserHelper.GetUser(option.getDestId());
				OsfProjects gp = new OsfProjects();
				gp.setCategoryId(3);
				gp.setEnabled(true);
				gp.setEntered(new Date());
				gp.setEnteredById(new Long(userId));
				gp.setModified(new Date());
				gp.setPublish(true);
				gp.setTitle(u.getNickname()
						+ "-"
						+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
								.format(new Date()));
				gp.setDescription(u.getNickname() + " Share "
						+ mediaRet.getMedia().getTitle() + " to "
						+ u2.getNickname());
				gp.setUniqueId(String.format("ShareMedia-%d-%d", userId,
						new Date().getTime()));
				int groupId = groupDao.CreateGroup(gp);
				if (groupId <= 0) {

					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Can't share a media to a friend.");
					return res;

				}

				OsfTeamMembers userobj = new OsfTeamMembers();
				userobj.setEnabled(true);
				/*
				 * userobj.setNickname(u.getNickname());
				 * userobj.setPhotourl(u.getPhotoUrl());
				 */
				userobj.setProjectId(groupId);
				userobj.setRoleId(GroupRole.groupAdmin.ordinal());
				userobj.setStatus("");
				userobj.setUserId(userId);
				/* userobj.setUsername(u.getUsername()); */
				friendDao.SetUserToGroup(userobj);

				OsfTeamMembers userobj2 = new OsfTeamMembers();
				userobj2.setEnabled(true);
				/*
				 * userobj2.setNickname(u2.getNickname());
				 * userobj2.setPhotourl(u2.getPhotoUrl());
				 */
				userobj2.setProjectId(groupId);
				userobj2.setRoleId(GroupRole.groupMember.ordinal());
				userobj2.setStatus("");
				userobj2.setUserId(option.getDestId());
				/* userobj2.setUsername(u2.getUsername()); */
				friendDao.SetUserToGroup(userobj2);

				//obj.setGroupId(groupId);
				shareUser.setGroupId(groupId);
			

				content = String.format("%s shares a media \"%s\" to you.",
						u.getNickname(), mediaRet.getMedia().getTitle());
				CacheManager.GetInstance().ClearCacheMyShares(userId);
				CacheManager.GetInstance().ClearCacheMyShares(option.getDestId());
				GroupManager.RemoveMyGroupCache(userId);
				GroupManager.RemoveMyGroupCache(option.getDestId());
				break;
			case group:
				obj.setSharetype(option.getShareType().ordinal());
				OsfProjects group = groupDao.GetGroup(option.getDestId());
				OsfTeamMembers user = null;
				if (group != null) {
					user = friendDao.GetUserInGroup(group.getId().intValue(),
							userId);
					if (user == null
							|| user.getRoleId() == GroupRole.groupGuest
									.ordinal()) {
						res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
						res.setResponseMessage("Can't find the user.");
						return res;
					}
				} else {
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Can't find the group.");
					return res;
				}
				//obj.setGroupId(option.getDestId());
				shareUser.setGroupId(option.getDestId());
				
				shareUser.setFriendId(0);

				/*
				 * content =
				 * String.format("%s shares a media \"%s\" in group \"%s\"",
				 * user.getNickname(), mediaRet.getMedia().getTitle(),
				 * group.getTitle());
				 */
				content = String.format("1 video has been added to %s", group.getTitle());
//				cache.invalidate(String.format("shares_in_group_%d",
//						option.getDestId()));
				
				break;
			case myFirends:
				obj.setSharetype(option.getShareType().ordinal());
				OsfProjects g = groupDao.GetMyFriendGroup(userId);
				if(g != null){
					shareUser.setGroupId(g.getId().intValue());
				}
				break;
			default:
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid parameter");
				return res;
			}

			//obj.setUserId(userId);
			obj.setDatecreated(new Date());
			obj.setDatemodified(new Date());
			obj.setMediaId(option.getMediaId());

			obj.setMediatitle(mediaRet.getMedia().getTitle());
			obj.setMediadescription(mediaRet.getMedia().getDesc());
			

			obj.setContent(mediaRet.getMedia().getSmileUrl());
			obj.setContenttype(mediaRet.getMedia().getContentType().ordinal());
			obj.setLessonId(lessonId);
			int shareId = shareDao.CreateShare(obj,shareUser.getGroupId());
			if (shareId > 0) {
				shareUser.setUserId(userId);
				shareUser.setCreateDate(new Date());
				shareUser.setModifyDate(new Date());
				shareUser.setStatus(true);
				obj.setShareId(shareId);
				shareUser.setDcpShare(obj);
				int shareUserId = shareDao.CreateShareUser(option.getShareType(),shareUser);
				if(shareUserId <= 0){
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to share media");
					return res;
				}
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				MessageServiceHelper helper = new MessageServiceHelper();

				switch (option.getShareType()) {
					case friend: {
						CacheManager.GetInstance().ClearCacheMyShares(option.getDestId());
						CacheManager.GetInstance().ClearCacheMyShares(userId);
						SysMessage msg = new SysMessage();
						msg.setMessageType(SysMessage.eSysMessageType.ToPerson);
						msg.setMessageCategory(MessageCategory.SOCIAL_SERVICE_MESSAGE_TO_PERSON);
						msg.setTargetId(option.getDestId());
						msg.setContent(content);
						msg.setSenderId(userId);
						
						ServiceResponse sr = helper.SendSysMessage(msg);
						if (sr.getResponseCode() != ResponseCode.SUCCESS) {
							res.setResponseMessage(String.format(
									"Send message fail. %d, %s",
									sr.getResponseCode(), sr.getResponseMessage()));
						}
						break;
					}
					case group: {
						CacheManager.GetInstance().ClearCacheSharesOfGroup(option.getDestId());
						SysMessage msg = new SysMessage();
						msg.setMessageType(SysMessage.eSysMessageType.ToGroup);
						msg.setMessageCategory(MessageCategory.SOCIAL_SERVICE_MESSAGE_TO_GROUP);
						msg.setTargetId(option.getDestId());
						msg.setContent(content);
						msg.setSenderId(userId);
						
						ServiceResponse sr = helper.SendSysMessage(msg);
						if (sr.getResponseCode() != ResponseCode.SUCCESS) {
							res.setResponseMessage(String.format(
									"Send message fail. %d, %s",
									sr.getResponseCode(), sr.getResponseMessage()));
						}
						break;
					}
					case myFirends:
						CacheManager.GetInstance().ClearCacheMySharesOfAllFriends(userId);
						break;
					case all:
						cachePublic.invalidateAll();
						break;
				}
			}
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}

	public static final Cache<String,List<ShareDbInfo>> cachePublic = CacheBuilder
			.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS)
			.build();
	public static final Cache<Integer, MediaBaseType.eMediaStatus> statusCache = CacheBuilder
			.newBuilder().maximumSize(1000)
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	public MediaBaseType.eMediaStatus getMediaStatusCache(Integer key) {
		MediaBaseType.eMediaStatus mediaStatus = null;
		mediaStatus = statusCache.getIfPresent(key);
		if (mediaStatus == null) {
			String baseUrl = ConfigurationManager.GetInstance()
					.MediaServiceBaseUrl();
			String reqUrl = String.format("%s/media/%d", baseUrl, key);
			try {
				String jsonRet = Util.HttpGet(reqUrl);
				MediaResponse mediaRet = Util.JsonToObject(
						jsonRet, MediaResponse.class);
				if (mediaRet.getResponseCode() != ResponseCode.SUCCESS) {
					return mediaStatus;
				}
				mediaStatus = mediaRet.getMedia().getStatus();
				if (mediaStatus == MediaBaseType.eMediaStatus.done) {
					shareDao.UpdateDcpShareStatus(key,
							MediaBaseType.eMediaStatus.done.ordinal());
					//cache.invalidateAll();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (mediaStatus == null)
				mediaStatus = MediaBaseType.eMediaStatus.error_transcode_fail;
			statusCache.put(key, mediaStatus);
		}

		return mediaStatus;
	}

	public SharesResponse GetShares(int userId, QueryOption.QueryType option,int startPos, int endPos) {
		SharesResponse res = new SharesResponse();

		try {
			List<ShareDbInfo> shares = null;
			shares = CacheManager.GetInstance().GetCacheMyShares(userId, option);
			if (shares == null) {
				switch (option) {
				case all:
					shares = shareDao.GetShares(userId, option.ordinal());
					break;
				case mine:
					shares = shareDao.GetShares(userId, option.ordinal());
					break;
				case friend:
					shares = shareDao.GetShares(userId, option.ordinal());
					break;
				case pseudo:
					shares = shareDao.GetShares(userId, option.ordinal());
					break;
				default:
					shares = shareDao.GetShares(userId, option.ordinal());
					break;
				}
				if (shares != null && shares.size() > 0) {
					for(ShareDbInfo info: shares)
					{
						LikePointCache.UpdateMediaLikePoint(info.getDcpShare().getMediaId(), info.getLikePoint());
					}
					CacheManager.GetInstance().SetCacheMyShares(userId, option, shares);
				}

			}
			if (shares != null && shares.size() > 0) {
				List<ShareDbInfo> subShares = null;
				if(startPos==0 && endPos == 0){
					subShares = shares;
				}else{
					int sp = startPos;
					int ep = endPos;
					if (startPos >= shares.size()) {
						sp = 0;
						ep = 0;
					}
					if (ep >= shares.size()) {
						ep = shares.size();
					}
					
					if (sp <= ep) {
						subShares = shares.subList(sp, ep);
					}
				}
				List<Share> objs = new LinkedList<Share>();
				for (ShareDbInfo share : subShares) {
					if (share.getDcpShare().getStatus() == MediaBaseType.eMediaStatus.publishing
							.ordinal()) {

						share.getDcpShare().setStatus(getMediaStatusCache(share.getDcpShare().getMediaId())
								.ordinal());

					}
					Share obj = ToShare(share);
					obj.setLikePoint(LikePointCache.GetMediaLikePoint(obj.getMediaId()));
					objs.add(obj);
				}
				res.setShares(objs);
				res.setStartPos(startPos);
				res.setCount(subShares != null ? subShares.size() : 0);
			}

			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

	
	public SharesResponse GetPublicShares(DcpBaseType.eAppType appType, QueryOption.MediaType option,QueryOption.SearchType searchOption, int startPos, int endPos) {
		SharesResponse res = new SharesResponse();

		try {
			List<ShareDbInfo> shares = null;
			String key = String.format("public_shares_%d_%d_%d", appType.ordinal(),option.ordinal(),searchOption.ordinal());
			shares = cachePublic.getIfPresent(key);
			if (shares == null) {
				List<Integer> manshiContentType = new LinkedList<Integer>();
				manshiContentType.add(eContentType.Gif.ordinal());
				manshiContentType.add(eContentType.Video4Gif.ordinal());
				
				switch(appType)
				{
				case Yingyueguan:
					List<Integer> cts = new LinkedList<Integer>();
					if(MediaType.book.ordinal() == option.ordinal()){
						cts.add(eContentType.EduBook.ordinal());
						cts.add(eContentType.EduBookURL.ordinal());
					}else if(MediaType.story.ordinal() == option.ordinal()){
						cts.add(eContentType.EduStory.ordinal());
					}

					shares = shareDao.GetPublicShares(cts,searchOption);
					break;
				case Manshi:
					shares = shareDao.GetPublicShares(manshiContentType,searchOption);
					break;
				default:
					if (ConfigurationManager.GetInstance().GetConfigValue("Social_Enable_Manshi", true)){
						shares = shareDao.GetPublicShares(manshiContentType,searchOption);
					}
					break;
				}
				
				if (shares != null && shares.size() > 0) {
					for(ShareDbInfo info: shares)
					{
						LikePointCache.UpdateMediaLikePoint(info.getDcpShare().getMediaId(), info.getLikePoint());
					}
					cachePublic.put(key, shares);
				}
			}

			if (shares != null && shares.size() > 0) {
				List<ShareDbInfo> subShares = null;
				if(startPos==0 && endPos == 0){
					subShares = shares;
				}else{
					int sp = startPos;
					int ep = endPos;
					if (startPos >= shares.size()) {
						sp = 0;
						ep = 0;
					}
					if (ep >= shares.size()) {
						ep = shares.size();
					}
					
					if (sp <= ep) {
						subShares = shares.subList(sp, ep);
					}
				}
				List<Share> objs = new LinkedList<Share>();
				for (ShareDbInfo share : subShares) {
					if (share.getDcpShare().getStatus() == MediaBaseType.eMediaStatus.publishing
							.ordinal()) {
						share.getDcpShare().setStatus(getMediaStatusCache(share.getDcpShare().getMediaId())
								.ordinal());

					}
					Share obj = ToShare(share);
					obj.setLikePoint(LikePointCache.GetMediaLikePoint(obj.getMediaId()));
					objs.add(obj);
					
				}
				res.setShares(objs);
				res.setStartPos(startPos);
				res.setCount(subShares != null ? subShares.size() : 0);
			}
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Internal Error. " + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception GetPublicShares(%s, %s, %s, %d, %d)", appType.toString(), option.toString(), searchOption.toString(), startPos, endPos), ex);
		}

		return res;
	}

	public SharesResponse GetSharesInGroup(int userId, int groupId,final int startPos,int endPos) {
		SharesResponse res = new SharesResponse();

		try {
			List<ShareDbInfo> shares = null;
			
			shares = CacheManager.GetInstance().GetCacheSharesOfGroup(groupId);
			if (shares == null) {
				shares = shareDao.GetSharesOfGroups(userId, groupId);
				if (shares != null && shares.size() > 0) {
					
					for (ShareDbInfo share : shares)
					{
						LikePointCache.UpdateMediaLikePoint(share.getDcpShare().getMediaId(), share.getLikePoint());
						LikePointCache.UpdateShareActivity(share.getDcpShare().getShareId(), share.getCommentCount(), share.getLikeCount());
					}
					CacheManager.GetInstance().SetCacheSharesOfGroup(groupId, shares);
				}
			}
			
			if (shares != null && shares.size() > 0) {
				List<ShareDbInfo> subShares = null;
				if(startPos==0 && endPos == 0){
					subShares = shares;
				}else{
					int sp = startPos;
					int ep = endPos;
					if (startPos >= shares.size()) {
						sp = 0;
						ep = 0;
					}
					if (ep >= shares.size()) {
						ep = shares.size();
					}
					
					if (sp <= ep) {
						subShares = shares.subList(sp, ep);
					}
				}
				
				List<Share> objs = new LinkedList<Share>();
				for (ShareDbInfo share : subShares) {
					if (share.getDcpShare().getStatus() == MediaBaseType.eMediaStatus.publishing
							.ordinal()) {
						share.getDcpShare().setStatus(getMediaStatusCache(share.getDcpShare().getMediaId())
								.ordinal());
					}

					Share obj = ToShare(share);
					obj.setLikePoint(LikePointCache.GetMediaLikePoint(obj.getMediaId()));
					KeyValuePair<Integer, Integer> kvp = LikePointCache.GetShareActivity(obj.getShareId());
					if (kvp != null)
					{
						obj.setCommentCount(kvp.getKey());
						obj.setLikeCount(kvp.getValue());
					}
					objs.add(obj);
				}
				res.setShares(objs);
				res.setCount(subShares != null ? subShares.size() : 0);
			}
			res.setStartPos(startPos);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

	public ServiceResponse AddLike(int userId, Like like)
	{
		ServiceResponse res = new ServiceResponse();
		
		try
		{
			if (like.getShareId() <= 0 || like.getPoint() < 0)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid shareId or point");
				return res;
			}
			
			ActivityDbInfo ret = shareHistoryDao.AddComment(userId, like.getShareId(), ActionType.like, like.getOption(), "", like.getPoint());
			if (ret.getShareId() > 0)
			{
				LikePointCache.UpdateMediaLikePoint(ret.getMediaId(), ret.getLikePoint());
				LikePointCache.UpdateShareActivity(ret.getShareId(), ret.getCommentCount(), ret.getLikeCount());
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Inner Error. " + ret);
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Internal Error. " + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception to AddLike(%d, %s)", userId, Util.ObjectToJson(like)), ex);
		}

		return res;
	}
	public ServiceResponse AddShareActivity(int userId, int shareId,
			ActionType type, String content) {
		ServiceResponse sharesResponse = new ServiceResponse();
		OsfComments comments = null;
		int result=0;
		try {
			boolean bAddActivity = true;
			if (type.equals(ActionType.like)) {//是点赞
				comments = shareHistoryDao.GetLike(shareId, userId);
				if (comments!=null) {
					bAddActivity = false;
					comments.setEnabled(!content.equals(BaseSocialType.eLikeOption.none.toString()));
					comments.setContent(content);
					result = shareHistoryDao.UpdateSharaActivities(comments);
				}
			} 
			if (bAddActivity) {// 评论 或者 新增点赞
				comments = new OsfComments();
				comments.setActivitytype(type.ordinal());
				comments.setContent(content);
				comments.setEnabled(type == ActionType.comment || !content.equals(BaseSocialType.eLikeOption.none.toString()));
				comments.setEntered(new Date());
				comments.setEnteredById(userId);
				comments.setEntity(type.toString());
				comments.setLinkedId(shareId);
				comments.setModified(new Date());
				comments.setModifiedById(userId);
				result = shareHistoryDao.AddShareActivity(comments);
			}
			if (result > 0) {
				sharesResponse.setResponseCode(ResponseCode.SUCCESS);
				sharesResponse.setResponseMessage("Success");
			} else {
				sharesResponse
						.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				sharesResponse
						.setResponseMessage("Fail to add share activity "
								+ type.toString());
			}
		} catch (Exception ex) {
			sharesResponse.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			sharesResponse.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return sharesResponse;
	}

	public ActivitiesResponse GetShareHistory(int userId, int shareId,
			int actType,int startPos, int endPos) {
		ActivitiesResponse res = new ActivitiesResponse();

		try {
			List<OsfComments> comms = shareHistoryDao.GetShareActivities(
					shareId, actType);
			if (comms != null && comms.size() > 0) {
				List<OsfComments> subComments = null;
				if(startPos==0 && endPos == 0){
					subComments = comms;
				}else{
					int sp = startPos;
					int ep = endPos;
					if (startPos >= comms.size()) {
						sp = 0;
						ep = 0;
					}
					if (ep >= comms.size()) {
						ep = comms.size();
					}
					
					if (sp <= ep) {
						subComments = comms.subList(sp, ep);
					}
				}
				List<Activity> acts = new LinkedList<Activity>();

				Map<Integer, User> mapUsers = new HashMap<Integer, User>();

				for (OsfComments o : subComments) {
					Activity act = new Activity();

					act.setActivityId(o.getId().intValue());
					act.setActivityType(ActionType.values()[o
							.getActivitytype()]);
					act.setContent(o.getContent());
					act.setDate(o.getEntered());

					if (mapUsers.containsKey(o.getEnteredById())) {
						act.setUser(mapUsers.get(o.getEnteredById()));
					} else {
						User user = UserHelper.GetUser(new Long(o
								.getEnteredById()).intValue());
						act.setUser(user);
						mapUsers.put(user.getUserId(), user);
					}

					acts.add(act);
				}

				res.setActivities(acts);
			}
			res.setStartPos(startPos);
			res.setCount(comms != null ? comms.size() : 0);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	public ServiceResponse RemoveShare(int userId, int shareId) {
		ServiceResponse res = new ServiceResponse();
		try {
			int mid = shareDao.DeleteShare(userId, shareId);
			if (mid > 0) {
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("You can not to remove the share");
			}
		} catch (Exception ex) {

		}

		return res;
	}

//	private Share ToShare(KeyValuePair<DcpShare,DcpShareUser> obj,int likeCount, int commentCount) {
//		Share dst = new Share();
//		dst.setShareId(obj.getKey().getShareId());
//		dst.setUser(UserHelper.GetUser(obj.getValue().getUserId()));
//		dst.setDate(obj.getKey().getDatecreated());
//		dst.setTitle(obj.getKey().getMediatitle());
//		dst.setDescription(obj.getKey().getMediadescription());
//		dst.setSnapshotUrl(obj.getKey().getSnapshoturl());
//		dst.setMediaId(obj.getKey().getMediaId());
//		dst.setGroupId(obj.getValue().getGroupId() == null ? 0 : obj.getValue().getGroupId());
//		dst.setStatus(obj.getKey().getStatus() == MediaBaseType.eMediaStatus.publishing
//				.ordinal() ? Share.eStatus.publishing : Share.eStatus.done);
//		dst.setContentType(MediaBaseType.eContentType.values()[obj.getKey().getContenttype()]);
//		dst.setSmileUrl(obj.getKey().getContent());
//		dst.setLikeCount(likeCount);
//		dst.setCommentCount(commentCount);
//		dst.setLessonId(obj.getKey().getLessonId());
//		return dst;
//	}
	
	private Share ToShare(ShareDbInfo info) {
		DcpShare dbShare = info.getDcpShare();
		DcpShareUser dbShareUser = info.getDcpShareUser();
		Share dst = new Share();
		dst.setShareId(info.getDcpShare().getShareId());
		dst.setUser(UserHelper.GetUser(dbShareUser.getUserId()));
		dst.setDate(info.getDcpShare().getDatecreated());
		dst.setTitle(info.getDcpShare().getMediatitle());
		dst.setDescription(info.getDcpShare().getMediadescription());
		dst.setSnapshotUrl(dbShare.getSnapshoturl());
		dst.setMediaId(dbShare.getMediaId());
		dst.setGroupId(dbShareUser.getGroupId() == null ? 0 : dbShareUser.getGroupId());
		dst.setStatus(dbShare.getStatus() == MediaBaseType.eMediaStatus.publishing
				.ordinal() ? Share.eStatus.publishing : Share.eStatus.done);
		dst.setContentType(MediaBaseType.eContentType.values()[dbShare.getContenttype()]);
		dst.setSmileUrl(dbShare.getContent());
		dst.setLikeCount(info.getLikeCount());
		dst.setCommentCount(info.getCommentCount());
		dst.setLessonId(dbShare.getLessonId());
		dst.setLikePoint(info.getLikePoint());
		return dst;
	}
}
