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
import com.divx.service.Util;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.dao.GroupDao;
import com.divx.service.domain.dao.ShareDao;
import com.divx.service.domain.dao.ShareHistoryDao;
import com.divx.service.domain.model.DcpShare;
import com.divx.service.domain.model.OsfComments;
import com.divx.service.domain.model.OsfProjects;
import com.divx.service.domain.model.OsfTeamMembers;
import com.divx.service.model.ActivitiesResponse;
import com.divx.service.model.Activity;
import com.divx.service.model.GetMediaResult;
import com.divx.service.model.GroupRole;
import com.divx.service.model.KeyValueTriplePair;
import com.divx.service.model.LikeOption;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.MediaResponse;
import com.divx.service.model.MessageCategory;
import com.divx.service.model.QueryOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.Share;
import com.divx.service.model.ShareOption;
import com.divx.service.model.SharesResponse;
import com.divx.service.model.User;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class ShareManager {

	private ShareDao shareDao;
	private ShareHistoryDao shareHistoryDao;
	private GroupDao groupDao;
	private FriendDao friendDao;
	private UserHelper userHelper = new UserHelper();

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
				/*
				 * MediaServiceHelper msh = new MediaServiceHelper();
				 * ServiceHeaderHelper helper = new ServiceHeaderHelper();
				 * String strToken = helper.getHeader("Token");
				 * if(strToken.isEmpty()){
				 * res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				 * res.setResponseMessage
				 * (String.format("Can't call ServiceHeaderHelper to get Token %s"
				 * , helper.getHeader("Token"))); return res; }
				 * 
				 * ServiceResponse sr =
				 * msh.PublicMedia(strToken,option.getMediaId(),true);
				 * if(sr.getResponseCode() == 0){
				 * obj.setSharetype(option.getShareType().ordinal());
				 * obj.setGroupId(0); }else{
				 * res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				 * res.setResponseMessage
				 * (String.format("Can't public the media to all. %s %s",
				 * sr.getResponseCode(),sr.getResponseMessage())); return res; }
				 */
				obj.setSharetype(option.getShareType().ordinal());
				obj.setGroupId(0);
				cachePublic.invalidateAll();
				break;
			case friend:
				obj.setSharetype(option.getShareType().ordinal());
				obj.setFriendId(option.getDestId());
				User u = userHelper.GetUser(userId);
				User u2 = userHelper.GetUser(option.getDestId());
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

				obj.setGroupId(groupId);

				content = String.format("%s shares a media \"%s\" to you.",
						u.getNickname(), mediaRet.getMedia().getTitle());
				RemoveMySharesCache(userId);
				RemoveMySharesCache(option.getDestId());
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
				obj.setGroupId(option.getDestId());

				/*
				 * content =
				 * String.format("%s shares a media \"%s\" in group \"%s\"",
				 * user.getNickname(), mediaRet.getMedia().getTitle(),
				 * group.getTitle());
				 */
				content = String.format("1 video has been added to %s", group.getTitle());
				cache.invalidate(String.format("shares_in_group_%d",
						option.getDestId()));
				break;
			default:
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid parameter");
				return res;
			}

			obj.setUserId(userId);
			obj.setDatecreated(new Date());
			obj.setDatemodified(new Date());
			obj.setMediaId(option.getMediaId());

			obj.setMediatitle(mediaRet.getMedia().getTitle());
			obj.setMediadescription(mediaRet.getMedia().getDescription());
			obj.setSnapshoturl(mediaRet.getMedia().getSnapshotUrl());

			obj.setContent(mediaRet.getMedia().getSmileUrl());
			obj.setContenttype(mediaRet.getMedia().getContentType().ordinal());
			if (shareDao.CreateShare(obj) > 0) {
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				MessageServiceHelper helper = new MessageServiceHelper();

				switch (option.getShareType()) {
				case friend: {
					ServiceResponse sr = helper.SendSysMessage(
							MessageServiceHelper.eSysMessageType.ToPerson,
							MessageCategory.SOCIAL_SERVICE_MESSAGE_TO_PERSON,
							option.getDestId(), content);
					if (sr.getResponseCode() != ResponseCode.SUCCESS) {
						res.setResponseMessage(String.format(
								"Send message fail. %d, %s",
								sr.getResponseCode(), sr.getResponseMessage()));
					}
					break;
				}
				case group: {
					ServiceResponse sr = helper.SendSysMessage(
							MessageServiceHelper.eSysMessageType.ToGroup,
							MessageCategory.SOCIAL_SERVICE_MESSAGE_TO_GROUP,
							option.getDestId(), content);
					if (sr.getResponseCode() != ResponseCode.SUCCESS) {
						res.setResponseMessage(String.format(
								"Send message fail. %d, %s",
								sr.getResponseCode(), sr.getResponseMessage()));
					}
					break;
				}
				}
			}
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}

	public static final Cache<String, List<DcpShare>> cache = CacheBuilder
			.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS)
			.build();
	public static final Cache<String, List<DcpShare>> cachePublic = CacheBuilder
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
					cache.invalidateAll();
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

	public SharesResponse GetShares(int userId, QueryOption.QueryType option) {
		SharesResponse res = new SharesResponse();

		try {
			List<DcpShare> shares = null;
			String key = String
					.format("shares_%d_%d", userId, option.ordinal());
			shares = cache.getIfPresent(key);
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
					cache.put(key, shares);
				}

			}
			if (shares != null && shares.size() > 0) {
				List<Share> objs = new LinkedList<Share>();
				for (DcpShare share : shares) {
					if (share.getStatus() == MediaBaseType.eMediaStatus.publishing
							.ordinal()) {

						share.setStatus(getMediaStatusCache(share.getMediaId())
								.ordinal());

					}
					objs.add(ToShare(share,0,0));
				}
				res.setShares(objs);
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

	public SharesResponse GetPublicShares(int startPos, int endPos) {
		SharesResponse res = new SharesResponse();

		try {
			List<DcpShare> shares = null;
			String key = String.format("public_shares");
			shares = cachePublic.getIfPresent(key);
			if (shares == null) {
				shares = shareDao.GetPublicShares();
				if (shares != null && shares.size() > 0) {
					cache.put(key, shares);
				}
			}

			if (shares != null && shares.size() > 0) {
				List<DcpShare> subShares = null;
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
				for (DcpShare share : subShares) {
					if (share.getStatus() == MediaBaseType.eMediaStatus.publishing
							.ordinal()) {
						share.setStatus(getMediaStatusCache(share.getMediaId())
								.ordinal());

					}
					objs.add(ToShare(share,0,0));
				}
				res.setShares(objs);
				res.setStartPos(startPos);
				res.setCount(subShares != null ? subShares.size() : 0);
				log.info("success:"+ objs.toString());
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

	public SharesResponse GetSharesInGroup(int userId, int groupId,final int startPos,int endPos) {
		SharesResponse res = new SharesResponse();

		try {
			//List<DcpShare> shares = null;
			List<KeyValueTriplePair<Integer, Integer, DcpShare>> shares = null;
			String key = String.format("shares_in_group_%d", groupId);
			//shares = cache.getIfPresent(key);
			if (shares == null) {
				shares = shareDao.GetSharesOfGroups(userId, groupId);
				if (shares != null && shares.size() > 0) {
					//cache.put(key, shares);
				}
			}
			
			if (shares != null && shares.size() > 0) {
				List<KeyValueTriplePair<Integer, Integer, DcpShare>> subShares = null;
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
				for (KeyValueTriplePair<Integer, Integer, DcpShare> share : subShares) {
					if (share.getValue2().getStatus() == MediaBaseType.eMediaStatus.publishing
							.ordinal()) {
						share.getValue2().setStatus(getMediaStatusCache(share.getValue2().getMediaId())
								.ordinal());
					}
					objs.add(ToShare(share.getValue2(),share.getKey(),share.getValue1()));
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

	public ServiceResponse AddShareActivity(int userId, int shareId,
			Activity.ActionType type, String content) {
		ServiceResponse sharesResponse = new ServiceResponse();
		OsfComments comments = null;
		int result=0;
		try {
			boolean bAddActivity = true;
			if (type.equals(Activity.ActionType.like)) {//是点赞
				comments = shareHistoryDao.GetLike(shareId, userId);
				if (comments!=null) {
					bAddActivity = false;
					comments.setEnabled(!content.equals(LikeOption.none.toString()));
					comments.setContent(content);
					result = shareHistoryDao.UpdateSharaActivities(comments);
				}
			} 
			if (bAddActivity) {// 评论 或者 新增点赞
				comments = new OsfComments();
				comments.setActivitytype(type.ordinal());
				comments.setContent(content);
				comments.setEnabled(type == Activity.ActionType.comment || !content.equals(LikeOption.none.toString()));
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
					act.setActivityType(Activity.ActionType.values()[o
							.getActivitytype()]);
					act.setContent(o.getContent());
					act.setDate(o.getEntered());

					if (mapUsers.containsKey(o.getEnteredById())) {
						act.setUser(mapUsers.get(o.getEnteredById()));
					} else {
						User user = userHelper.GetUser(new Long(o
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

	private Share ToShare(DcpShare obj,int likeCount, int commentCount) {
		Share dst = new Share();

		dst.setShareId(obj.getShareId());
		dst.setOwnerId(obj.getUserId());
		dst.setDate(obj.getDatecreated());
		dst.setTitle(obj.getMediatitle());
		dst.setDescription(obj.getMediadescription());
		dst.setSnapshotUrl(obj.getSnapshoturl());
		dst.setMediaId(obj.getMediaId());
		dst.setGroupId(obj.getGroupId() == null ? 0 : obj.getGroupId());
		dst.setStatus(obj.getStatus() == MediaBaseType.eMediaStatus.publishing
				.ordinal() ? Share.eStatus.publishing : Share.eStatus.done);
		dst.setLikeCount(likeCount);
		dst.setCommentCount(commentCount);
		dst.setContentType(MediaBaseType.eContentType.values()[obj.getContenttype()]);
		dst.setSmileUrl(obj.getContent());
		return dst;
	}

	public static void RemoveMySharesCache(int userId) {
		List<String> keys = new LinkedList<String>();
		for (QueryOption.QueryType qt : QueryOption.QueryType.values()) {
			keys.add(String.format("shares_%d_%d", userId, qt.ordinal()));
		}
		cache.invalidateAll(keys);
	}
}
