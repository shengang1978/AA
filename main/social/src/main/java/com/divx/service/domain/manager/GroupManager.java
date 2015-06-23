package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MessageServiceHelper;
import com.divx.service.UserHelper;
import com.divx.service.Util;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.dao.GroupDao;
import com.divx.service.domain.model.OsfProjects;
import com.divx.service.domain.model.OsfTeamMembers;
import com.divx.service.model.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.Gson;

@Service
public class GroupManager {
	public static final Cache<String, List<KeyValueTriplePair<Integer, Integer, OsfProjects>>> cacheMyGroups = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build();
	public static final Cache<String, List<User>> cacheGroupUsers = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build();
	
	public enum GroupType
	{
		unknown,
		myfriend,
		
	}
	private GroupDao groupDao;
	private FriendDao friendDao;
	
	@Autowired
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	@Autowired
	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}
	
	public GroupResponse CreateGroup(int userId, GroupOption option)
	{
		GroupResponse res = new GroupResponse();
		
		try
		{
			OsfProjects group = new OsfProjects();
			
			group.setCategoryId(2);
			group.setEnabled(true);
			group.setEntered(new Date());
			group.setEnteredById(new Long(userId));
			group.setModified(new Date());
			group.setPublish(true);
			group.setTitle(option.getTitle());
			group.setDescription(option.getDescription());
			group.setUniqueId(String.format("Group_%d_%s", userId, UUID.randomUUID().toString()));

			int groupId = groupDao.CreateGroup(group);
			if (groupId > 0)
			{
				res.setGroupId(groupId);
				RemoveMyGroupCache(userId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to create the group.");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	
	public MyGroupsResponse MyGroups(int reqUserId, QueryOption.QueryType option,final int startPos, int endPos)
	{
		MyGroupsResponse mgs = new MyGroupsResponse();
		//List<OsfProjects> groups = null;
		List<KeyValueTriplePair<Integer, Integer, OsfProjects>> groups = null;
		try{
			String key = String.format("mygroups_%d_%d", reqUserId, option.ordinal());
			groups = cacheMyGroups.getIfPresent(key);
			
			if (groups == null)
			{
				switch(option)
				{
					case all:
					{
						 groups  = groupDao.GetMyGroups(reqUserId,option.ordinal());
						
						break;
					}
					case mine:
					{
						 groups  = groupDao.GetMyGroups(reqUserId,option.ordinal());
						
						break;
					}
					case friend:
					{
						 groups  = groupDao.GetMyGroups(reqUserId,option.ordinal());
						
						break;
					}
					
					case pseudo:
					{
						 groups  = groupDao.GetMyGroups(reqUserId,option.ordinal());
						
						break;
					}
					default:
					{
						 groups  = groupDao.GetMyGroups(reqUserId,option.ordinal());						
					}						
				}
				if(groups != null && groups.size()>0){
					cacheMyGroups.put(key, groups);
				}				
			}
			List<KeyValueTriplePair<Integer, Integer, OsfProjects>> subGroups = null;
			int count = 0;
			if(groups.size()>0)
			{
				if(startPos==0 && endPos == 0){
					subGroups = groups;
				}else{
					if (endPos != 0)
					{
						int  starPos = startPos;
						if (endPos >= groups.size())
						{
							endPos = groups.size() ;
						}
						
						if (startPos > endPos)
						{
						  starPos = endPos;
						}
						subGroups = groups.subList(starPos, endPos);
					}
				}
				
				
				List<Group> gs = new LinkedList<Group>();
				for (KeyValueTriplePair<Integer, Integer, OsfProjects> obj: subGroups)
				{	
					Group group = new Group();
					group.setId(obj.getValue2().getId().intValue());
					group.setTitle(obj.getValue2().getTitle());
					group.setDescription(obj.getValue2().getDescription());
					group.setCreateDate(obj.getValue2().getEntered());
					group.setUserCount(obj.getKey());
					group.setAssetCount(obj.getValue1());
					if (obj.getValue2().getPhotourl() != null && !obj.getValue2().getPhotourl().isEmpty())
					{
						String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
						group.setPhotoUrl(configUrl + obj.getValue2().getPhotourl());
					}else{
						group.setPhotoUrl("");
					}
					
					gs.add(group);
				}	
				mgs.setGroups(gs);
				count = gs.size();
			}
			mgs.setStartPos(startPos);
			mgs.setCount(count);
			mgs.setResponseCode(ResponseCode.SUCCESS);
			mgs.setResponseMessage("Success");
			
		}catch(Exception ex){
			mgs.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			mgs.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		
		}
		
		return mgs;
	}
	
	public ServiceResponse AddUserToGroup(int reqUserId, int groupId, int userId)
	{
		ServiceResponse res = new ServiceResponse();
		
		try
		{
			if(userId != reqUserId){
				
			
				OsfTeamMembers reqMember = friendDao.GetUserInGroup(groupId, reqUserId);
				if (reqMember == null || reqMember.getRoleId() != GroupRole.groupAdmin.ordinal())
				{
					res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
					res.setResponseMessage("User doesn't have permission to add a user to group.");
					return res;
				}
			}
			/*User user = userHelper.GetUser(userId);*/
			OsfTeamMembers obj = new OsfTeamMembers();
			
			obj.setEnabled(true);
			/*obj.setNickname(user.getNickname());
			obj.setPhotourl(user.getPhotoUrl());*/
			obj.setProjectId(groupId);
			if(userId == reqUserId){
				obj.setRoleId(GroupRole.groupAdmin.ordinal());
			}else{
				obj.setRoleId(GroupRole.groupMember.ordinal());
			}
			
			obj.setStatus("");
			obj.setUserId(userId);
			/*obj.setUsername(user.getUsername());*/
			int tmId = friendDao.SetUserToGroup(obj);
			if (tmId > 0)
			{
				ImHelper.AddUserToGroup(groupId, userId);
				RemoveMyGroupCache(userId);
				RemoveMyGroupCache(reqUserId);
				RemoveGroupUsersCache(groupId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				MessageServiceHelper msh = new MessageServiceHelper();
				OsfProjects group = groupDao.GetGroup(groupId);
				if(group != null){
//					SysMessage msg = new SysMessage();
//					msg.setMessageType(SysMessage.eSysMessageType.ToGroup);
//					msg.setMessageCategory(MessageCategory.SOCIAL_SERVICE_MESSAGE_TO_GROUP);
//					msg.setTargetId(groupId);
//					msg.setContent(String.format("%s has joined %s",userHelper.GetUser(userId).getNickname(),group.getTitle()));
//					msg.setSenderId(userId);
//					
//					ServiceResponse sr = msh.SendSysMessage(msg);
//					if (sr.getResponseCode() != 0) {
//						res.setResponseMessage(String.format(
//								"Fail to send message. (%d) %s",
//								sr.getResponseCode(), sr.getResponseMessage()));
//					}	
				}
				
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to set user to group.");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	
	public ServiceResponse RemoveUserFromGroup(int reqUserId, int groupId, int userId)
	{
		ServiceResponse res = new ServiceResponse();
		try{
			OsfTeamMembers reqMember = friendDao.GetUserInGroup(groupId, reqUserId);
			if (reqMember == null || reqMember.getRoleId() != GroupRole.groupAdmin.ordinal())
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("User doesn't have permission to remove a user to group.");
				return res;
			}			
			if(userId == reqUserId){
				OsfProjects group = groupDao.GetGroup(groupId);
				if(group != null && group.getEnteredById() == userId){
					res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
					res.setResponseMessage("You are the group owner,so you can not remove yourself to group.");
					return res;
				}
			}
			int tmId = friendDao.RemoveUserFromGroup(groupId, reqUserId, userId);
			if (tmId > 0)
			{
				ImHelper.RemoveUserFromGroup(groupId, userId);
				RemoveMyGroupCache(userId);
				RemoveMyGroupCache(reqUserId);
				RemoveGroupUsersCache(groupId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to remove user to group.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
			
		}
		return res;
	}
	
	public UsersResponse GetUsersOfGroup(int reqUserId, int groupId, QueryUserOption.QueryType option)
	{
		UsersResponse urs = new UsersResponse();
		try{
			OsfTeamMembers reqMember =  friendDao.GetUserInGroup(groupId, reqUserId);
			if (reqMember == null )
			{
				urs.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				urs.setResponseMessage("The user does't belong to this group.");
				return urs;
			}	
			String key = String.format("groupUsers_%d_%d", groupId, option.ordinal());
			List<User>	users = cacheGroupUsers.getIfPresent(key);
			if (users == null)
			{
				users = new LinkedList<User>();
				
				List<OsfTeamMembers> reqMembers = null;
				switch(option)
				{
					case all:{
						 reqMembers = friendDao.GetUsers(groupId, GroupRole.unknown.ordinal());
						
						break;
					}
					case friend:{
						 reqMembers = friendDao.GetUsers(groupId, GroupRole.groupMember.ordinal());
						
						break;
						
					}
					case admin:{
						 reqMembers = friendDao.GetUsers(groupId, GroupRole.groupAdmin.ordinal());
						
						break;
						
					}
					case guest:{
						 reqMembers = friendDao.GetUsers(groupId, GroupRole.groupGuest.ordinal());
						
						break;
						
					}
					default:{
						 reqMembers = friendDao.GetUsers(groupId, GroupRole.unknown.ordinal());
					}
				
				}
				if(reqMembers != null && reqMembers.size() > 0){					
					for(OsfTeamMembers obj : reqMembers){
						User u = UserHelper.GetUser((int)obj.getUserId());
						if(u != null && u.getUserId() > 0){
							users.add(u);
						}
						
						
					}					
				}
				if (users != null && users.size() > 0)
					cacheGroupUsers.put(key, users);
			}
			
			urs.setUsers(users);
			urs.setResponseCode(ResponseCode.SUCCESS);
			urs.setResponseMessage("Success");
		}catch(Exception ex){
			urs.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			urs.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return urs;
	}
	
	public ServiceResponse SetUserRole(int reqUserId, int groupId, int userId, GroupRole role)
	{
		ServiceResponse res = new ServiceResponse();
		try{
			OsfTeamMembers reqMember =  friendDao.GetUserInGroup(groupId, reqUserId);
			if (reqMember == null || reqMember.getRoleId() != GroupRole.groupAdmin.ordinal())
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("User doesn't have permission to set role for a user.");
				return res;
			}
			int tmId = friendDao.SetUserRoleInGroup(groupId, userId,role.ordinal());
			if (tmId > 0)
			{
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to set role for a user.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	
	public GroupResponse DeleteGroup(int reqUserId,int groupId)
	{
		GroupResponse res = new GroupResponse();
		try{
			OsfProjects group = groupDao.GetGroup(groupId);
			if (group == null || group.getCategoryId() == 1 || group.getEnteredById() != reqUserId  )
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("User doesn't have permission to delete a group.");
				return res;
			}
			int tmId = groupDao.DeleteGroup(groupId);
			if (tmId > 0)
			{
				ImHelper.DeleteGroup(groupId);
				RemoveMyGroupCache(reqUserId);
				RemoveGroupUsersCache(groupId);
				res.setGroupId(groupId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to delete a group.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	
	public GroupResponse UpdateGroup(int reqUserId,Group group)
	{
		GroupResponse res = new GroupResponse();
		try{
			OsfProjects gr = groupDao.GetGroup(group.getId());
			if (gr == null || gr.getCategoryId() == 1 || gr.getEnteredById() != reqUserId  )
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("User doesn't have permission to update a group.");
				return res;
			}
			gr.setTitle(group.getTitle() == null ? "group_"+ reqUserId + System.currentTimeMillis() : group.getTitle());
			gr.setDescription(group.getDescription());
			int groupId = groupDao.UpdateGroup(gr);
			if (groupId > 0)
			{
				RemoveMyGroupCacheOfUsers(groupId);
				RemoveGroupUsersCache(groupId);
				RemoveMyGroupCache(reqUserId);
				res.setGroupId(groupId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to update a group.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	
	public ServiceResponse AddUsersToGroup(int userId,
			UsersOption usersOption) {
		ServiceResponse res = new ServiceResponse();
		try{
			OsfTeamMembers reqMember = friendDao.GetUserInGroup(usersOption.getGroupId(), userId);
			if (reqMember == null || reqMember.getRoleId() != GroupRole.groupAdmin.ordinal())
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("User doesn't have permission to add a user to group.");
				return res;
			}
			int[] groupIds = usersOption.getGroupIds();
			int[] userIds = usersOption.getUserIds();
			String[] emails = usersOption.getEmails();
			String[] mobiles = usersOption.getMobiles();
			String key = String.format("groupUsers_%d_%d", usersOption.getGroupId(), QueryUserOption.QueryType.all.ordinal());
			List<User>	us = cacheGroupUsers.getIfPresent(key);
			if(us == null){
				us = new LinkedList<User>();
				List<OsfTeamMembers> reqMembers = friendDao.GetUsers(usersOption.getGroupId(), GroupRole.unknown.ordinal());
				for(OsfTeamMembers obj : reqMembers){
					User u = UserHelper.GetUser((int)obj.getUserId());
					us.add(u);	
				}
				if (us != null && us.size() > 0)
					cacheGroupUsers.put(key, us);
			}
			int tmId = friendDao.AddUsersToGroup(usersOption.getGroupId(), usersOption.arrToStrng(groupIds), usersOption.arrToStrng(userIds), usersOption.arrToStrng(emails), usersOption.arrToStrng(mobiles));
		   
			
		   if(tmId >= 0){
			   RemoveGroupUsersCache(usersOption.getGroupId());
			   res.setResponseCode(ResponseCode.SUCCESS);
			   res.setResponseMessage("Success");
			   MessageServiceHelper msh = new MessageServiceHelper();
				OsfProjects group = groupDao.GetGroup(usersOption.getGroupId());
				List<OsfTeamMembers> users = friendDao.GetUsers(usersOption.getGroupId(), 0);
				String content = "";
				List<User> newUsers = new LinkedList<User>();
				for(OsfTeamMembers user : users){
					User u = UserHelper.GetUser((int)user.getUserId());
					newUsers.add(u);	
				}
				newUsers.removeAll(us);
				for(User user : newUsers){
					if(user == newUsers.get(0)){
						content = String.format("%s",user.getNickname());
					}else{
						content = content + String.format(",%s",user.getNickname());
					}
					
				}
				String message = newUsers.size() > 1 ? String.format("%s have joined %s",content,group.getTitle()) : String.format("%s has joined %s",content,group.getTitle());
				if(group != null){
					SysMessage msg = new SysMessage();
					msg.setMessageType(SysMessage.eSysMessageType.ToGroup);
					msg.setMessageCategory(MessageCategory.SOCIAL_SERVICE_MESSAGE_TO_GROUP);
					msg.setTargetId(usersOption.getGroupId());
					msg.setContent(message);
					msg.setSenderId(userId);
					
					ServiceResponse sr = msh.SendSysMessage(msg);
					if (sr.getResponseCode() != 0) {
						res.setResponseMessage(String.format(
								"Fail to send message. (%d) %s",
								sr.getResponseCode(), sr.getResponseMessage()));
					}	
				}
		   }else{
			   res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call PROCEDURE");
		   }
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		
		return res;
	}
	
	public MyRoleInGroupResponse GetMyRoleInGroup(int userId, int groupId)
	{
		MyRoleInGroupResponse res = new MyRoleInGroupResponse();
		
		try
		{
			OsfTeamMembers tm = friendDao.GetUserInGroup(groupId, userId);
			if (tm == null)
			{
				res.setRole(GroupRole.unknown);
			}
			else
			{
				if (tm.getRoleId() == GroupRole.groupAdmin.ordinal())
				{
					OsfProjects g = groupDao.GetGroup(groupId);
					if (g.getEnteredById() == userId)
						res.setRole(GroupRole.admin);
					else
						res.setRole(GroupRole.groupAdmin);
				}
				else
					res.setRole(GroupRole.values()[(int) tm.getRoleId()]);
			}
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		
		return res;
	}

	public ServiceResponse SetGroupPhotoUrl(int userId, Group group) {
		ServiceResponse res = new ServiceResponse();
		try{
			OsfProjects gr = groupDao.GetGroup(group.getId());
			if (gr == null || gr.getCategoryId() == 1 || gr.getEnteredById() != userId  )
			{
				res.setResponseCode(ResponseCode.ERROR_NO_PERMISSION);
				res.setResponseMessage("User doesn't have permission to update a group.");
				return res;
			}
			gr.setPhotourl(group.getPhotoUrl());
			int gId = groupDao.UpdateGroup(gr);
			if (gId > 0)
			{
				RemoveMyGroupCacheOfUsers(group.getId());
				RemoveGroupUsersCache(group.getId());
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to update a group.");
			}
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	// Clear all the users group cache if the group info is changed.
	//		Update group
	//		Delete group
	//		Update the group photo
	public static void RemoveMyGroupCacheOfUsers(int groupId)
	{
		List<String> keys = new LinkedList<String>();
		for(QueryUserOption.QueryType qt: QueryUserOption.QueryType.values())
		{
			String key = String.format("groupUsers_%d_%d", groupId, qt.ordinal());
			List<User>	users = cacheGroupUsers.getIfPresent(key);
			if (users != null)
			{
				for(User u: users)
				{
					keys.add(String.format("mygroups_%d_%d", u.getUserId(), qt.ordinal()));
				}
			}
		}
		if (keys.size() > 0)
		{
			cacheMyGroups.invalidateAll(keys);
		}
	}
	
	// Clear the user's group cache
	//		Remove user from cache
	public static void RemoveMyGroupCache(int userId)
	{
		List<String> keys = new LinkedList<String>();
		for(QueryOption.QueryType qt: QueryOption.QueryType.values())
		{
			keys.add(String.format("mygroups_%d_%d", userId, qt.ordinal()));			
		}
		cacheMyGroups.invalidateAll(keys);
	}
	
	// Clear the group users cache of the group
	//		Remove the user from group
	//		Add user to group
	public static void RemoveGroupUsersCache(int groupId)
	{
		List<String> keys = new LinkedList<String>();
		for(QueryUserOption.QueryType qt: QueryUserOption.QueryType.values())
		{
			keys.add(String.format("groupUsers_%d_%d", groupId, qt.ordinal()));
		}
		cacheGroupUsers.invalidateAll(keys);
	}
}
