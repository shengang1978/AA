package com.divx.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.divx.service.model.KeyValuePair;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.User;

public class UserServiceHelper extends ServiceResponse{
	public class AuthResponse extends ServiceResponse{
		private String token;
		private String deviceGuid;
		public void setToken(String token)
		{
			this.token = token;
		}
		
		public String getToken()
		{
			return token;
		}

		public String getDeviceGuid() {
			return deviceGuid;
		}

		public void setDeviceGuid(String deviceGuid) {
			this.deviceGuid = deviceGuid;
		}
	}
	public AuthResponse AuthResponse;
	public class FindUsersResponse extends ServiceResponse{
		private List<User>	users;

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}
	}
	
	public enum eRegisterType
	{
		username,
		email,
		mobile
	}
	
	public class RegisterOption {
		private eRegisterType registerType;
		private String username;
		private String password;
		private String repassword;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getRepassword() {
			return repassword;
		}
		public void setRepassword(String repassword) {
			this.repassword = repassword;
		}
		public eRegisterType getRegisterType() {
			return registerType;
		}
		public void setRegisterType(eRegisterType registerType) {
			this.registerType = registerType;
		}
	}	
	
	public enum eFindOption
	{
		username,
		email,
		mobile,
		nickname
	}
	
	public class FindUserOption {
		private eFindOption	findOption;
		private String searchKey;
		public eFindOption getFindOption() {
			return findOption;
		}
		public void setFindOption(eFindOption findOption) {
			this.findOption = findOption;
		}
		public String getSearchKey() {
			return searchKey;
		}
		public void setSearchKey(String searchKey) {
			this.searchKey = searchKey;
		}
	}

	public class UserResponse extends ServiceResponse
	{
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}		
	}
	
	RegisterOption RegisterOption;
	public FindUserOption FindUserOption;

	//Register
	public AuthResponse RespondInviteUser(eRegisterType registerType, String username, String password, String repassword)
	{
		AuthResponse res = new AuthResponse();
		
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().UserServiceBaseUrl();
			String reqUrl = baseUrl + "/user/";
			
			UserServiceHelper ush = new UserServiceHelper();
			RegisterOption ro = new RegisterOption();
			ro.setRegisterType(registerType);
			ro.setUsername(username);
			ro.setPassword(password);
			ro.setRepassword(repassword);
			
			ush.RegisterOption = ro;
			
			String ret = Util.HttpPostJson(reqUrl, ush);
			if (ret.isEmpty())
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call Register()");
				return res;
			}
			
			UserServiceHelper.AuthResponse sr = Util.JsonToObject(ret, UserServiceHelper.AuthResponse.class);
			res.setToken(sr.getToken());
			res.setResponseCode(sr.getResponseCode());
			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		return res;
	}
	
	public FindUsersResponse FindUser(eFindOption ft, String value)
	{
		FindUsersResponse res = new FindUsersResponse();
		
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().UserServiceBaseUrl();
			String reqUrl = baseUrl + String.format("/user/FindUsers/%s?searchKey=%s",ft,value);
			
			String ret = Util.HttpGet(reqUrl);
			if (ret.isEmpty())
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call FindUsers");
				return res;
			}
			
			UserServiceHelper.FindUsersResponse sr = Util.JsonToObject(ret, FindUsersResponse.class);
			res.setUsers(sr.getUsers());
			res.setResponseCode(sr.getResponseCode());
			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		return res;
	}

	public UserResponse GetUser(int userId) throws Exception
	{
		String fromUrl = String.format("%s/userInner/%d", ConfigurationManager.GetInstance().UserServiceBaseUrl() ,userId);
		return Util.JsonToObject(Util.HttpGet(fromUrl), UserResponse.class);		
	}
	public class UserPhotoOption {
		private int userId;
		private String photourl;
		
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getPhotourl() {
			return photourl;
		}
		public void setPhotourl(String photourl) {
			this.photourl = photourl;
		}
	}
	UserPhotoOption UserPhotoOption;
	
	public ServiceResponse UpdateUserPhoto(int userId,String photoUrl)
	{
		ServiceResponse res = new ServiceResponse();
		try{
			String reqUrl = String.format("%s/userInner/UpdateUserPhoto", ConfigurationManager.GetInstance().UserServiceBaseUrl());
			UserServiceHelper ush = new UserServiceHelper();
			UserPhotoOption userPhotoOption = new UserPhotoOption();
			userPhotoOption.setUserId(userId);
			userPhotoOption.setPhotourl(photoUrl);
			ush.UserPhotoOption = userPhotoOption;
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			String ret = Util.HttpPutJson(reqUrl, ush, headers);
			if (ret.isEmpty())
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call UpdateUserPhoto.");
				return res;
			}
			res = Util.JsonToObject(ret, ServiceResponse.class);
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());	
		}
		return res;
			
	}
	
	public UserResponse registerUser(String inviteType,String option)
	{
		UserResponse res = new UserResponse();
		try{
			String reqUrl = String.format("%s/userInner/createUser", ConfigurationManager.GetInstance().UserServiceBaseUrl());
			UserServiceHelper ush = new UserServiceHelper();
			RegisterOption ro = new RegisterOption();
			eRegisterType type = (eRegisterType) Enum.valueOf(eRegisterType.class, inviteType);
			switch(type){
			case username:
				ro.setRegisterType(eRegisterType.username);
				break;
			case email:
				ro.setRegisterType(eRegisterType.email);
				break;
			case mobile:
				ro.setRegisterType(eRegisterType.mobile);
				break;
			}
		
			ro.setUsername(option);
			ro.setPassword(option);
			ro.setRepassword(option);
			
			ush.RegisterOption = ro;
			
			System.out.println(Util.ObjectToJson(ro));
			String ret = Util.HttpPostJson(reqUrl, ush);
			if (ret.isEmpty())
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to create user.");
				return res;
			}
			res = Util.JsonToObject(ret, UserResponse.class);
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());	
		}
		return res;
			
	}
}
