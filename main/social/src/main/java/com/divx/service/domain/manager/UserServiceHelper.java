package com.divx.service.domain.manager;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.ServiceResponse;

public class UserServiceHelper {
	public class UserResponse extends ServiceResponse
	{
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
		
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}		
	}
	
	public static UserResponse GetUser(int userId) throws Exception
	{
		String fromUrl = String.format("%s/user/%d", ConfigurationManager.GetInstance().UserServiceBaseUrl() ,userId);
		return Util.JsonToObject(Util.HttpGet(fromUrl), UserResponse.class);		
	}
}
