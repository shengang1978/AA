package com.divx.service.domain.manager;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

public class UserServiceHelper {
	public class User {
		private int userId;
		private String nickname;
		private String username;
		private String photourl;
		
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPhotourl() {
			return photourl;
		}
		public void setPhotourl(String photourl) {
			this.photourl = photourl;
		}
	}
	
	public class UserResponse extends ServiceResponse{
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
	}
	
	public UserResponse GetUser(int userId)
	{
		UserResponse res;
		try
		{
			String url = ConfigurationManager.GetInstance().UserServiceBaseUrl();
			String reqUrl = String.format("%s/user/%d", url, userId);
			String ret = Util.HttpGet(reqUrl);
			res = Util.JsonToObject(ret, UserServiceHelper.UserResponse.class);			
		}
		catch(Exception e)
		{
			res = new UserResponse();
			e.printStackTrace();
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		return res;
	}
}
