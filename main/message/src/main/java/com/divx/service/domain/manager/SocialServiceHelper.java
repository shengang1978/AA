package com.divx.service.domain.manager;

import java.util.ArrayList;
import java.util.List;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

public class SocialServiceHelper {
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

	public UsersResponse GetUsersOfGroup(int groupId)
	{
		UsersResponse res;
		try
		{
			String url = ConfigurationManager.GetInstance().SocialServiceBaseUrl();
			String reqUrl = String.format("%s/group/GetUsers/%d", url, groupId);
			
			SocialServiceHelper ssh = new SocialServiceHelper();
			ssh.QueryUserOption = new QueryUserOption();
			ssh.QueryUserOption.setQueryType(QueryType.all);
			
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("ServiceToken", "02c70159-822f-467c-9567-82e23dd90dce"));
			
			String ret = Util.HttpPost(reqUrl, Util.ObjectToJson(ssh), headers);
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
}
