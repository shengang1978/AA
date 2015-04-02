package com.divx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.divx.service.model.KeyValuePair;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

public class SocialServiceHelper{
	public class Group {
		private int id;
		private String title;
		private String description;
		private Date createDate;
		private String photoUrl;
		
		public void setId(int id)
		{
			this.id = id;
		}
		public int getId()
		{
			return id;
		}
		
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setDescription(String desc)
		{
			description = desc;
		}
		public String getDescription()
		{
			return description;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
	}
	public class GroupResponse extends ServiceResponse {
		private int groupId;

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

	}
	Group Group;
	
	public class User
	{
		private int userId;
		private String username;
		private String nickname;
		private String photoUrl;
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
	}
	
	public class UsersResponse extends ServiceResponse {
		private List<User>	users;

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}
	}
	
	public enum QueryType
	{
		all,
		friend,
		admin,
		guest
	}
	
	public class QueryUserOption {
		
		private QueryType queryType;

		public QueryType getQueryType() {
			return queryType;
		}

		public void setQueryType(QueryType queryType) {
			this.queryType = queryType;
		}
	}
	
	QueryUserOption QueryUserOption;
	public enum ShareType
	{
		all,	//Share to all.
		friend,	//Share to friend
		group	//Share in group
	}
	public class ShareOption {
		
		
		private int mediaId;
		private ShareType shareType;
		private int destId;
		
		public int getMediaId() {
			return mediaId;
		}
		public void setMediaId(int mediaId) {
			this.mediaId = mediaId;
		}
		
		public ShareType getShareType() {
			return shareType;
		}
		public void setShareType(ShareType shareType) {
			this.shareType = shareType;
		}
		
		public int getDestId() {
			return destId;
		}
		public void setDestId(int destId) {
			this.destId = destId;
		}
	}
	public UsersResponse GetUsersOfGroup(int groupId)
	{
		UsersResponse res;
		try
		{
			String url = ConfigurationManager.GetInstance().SocialServiceBaseUrl();
			String reqUrl = String.format("%s/socialInner/UsersInGroup/%d", url, groupId);
			
			String ret = Util.HttpGet(reqUrl);
			res = Util.JsonToObject(ret, SocialServiceHelper.UsersResponse.class);			
		}
		catch(Exception e)
		{
			res = new UsersResponse();
			e.printStackTrace();
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		return res;
	}
	
	public UsersResponse UserFriends(int userId)
	{
		UsersResponse res;
		try
		{
			String url = ConfigurationManager.GetInstance().SocialServiceBaseUrl();
			String reqUrl = String.format("%s/socialInner/UserFriends/%d", url, userId);
			
			String ret = Util.HttpGet(reqUrl);
			res = Util.JsonToObject(ret, SocialServiceHelper.UsersResponse.class);			
		}
		catch(Exception e)
		{
			res = new UsersResponse();
			e.printStackTrace();
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		return res;
	}
	public GroupResponse UpdateGroupPhotoUrl(String token,int groupId, String photoUrl){
		GroupResponse res = new GroupResponse();
		try{
			String reqUrl = String.format("%s/group/SetGroupPhotoUrl",ConfigurationManager.GetInstance().SocialServiceBaseUrl());
			SocialServiceHelper ssh = new SocialServiceHelper();
			Group group = new Group();
			group.setId(groupId);
			group.setPhotoUrl(photoUrl);
			ssh.Group = group;
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			headers.add(new KeyValuePair<String, String>("Token", token));
			String ret = Util.HttpPutJson(reqUrl,ssh, headers);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call UpdateGroup.");
				return res;
			}
			SocialServiceHelper.GroupResponse gr = Util.JsonToObject(ret, GroupResponse.class);
			res.setGroupId(gr.getGroupId());
			res.setResponseCode(gr.getResponseCode());
			res.setResponseMessage(gr.getResponseMessage());
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
	//ShareOption ShareOption;
	public ServiceResponse ShareMedia(String shareJson,String token){
		ServiceResponse res = new ServiceResponse();
		try{
			//ShareOption shareOption = Util.JsonToObject(shareJson, ShareOption.class);
			String reqUrl = String.format("%s/socialInner/InnerShareMedia", ConfigurationManager.GetInstance().SocialServiceBaseUrl());
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("Token", token));
			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			String ret = Util.HttpPost(reqUrl, shareJson, headers);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call ShareMedia." + reqUrl );
				return res;
			}
			res = Util.JsonToObject(ret, ServiceResponse.class);
		}catch(Exception  ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
		
	}
}
