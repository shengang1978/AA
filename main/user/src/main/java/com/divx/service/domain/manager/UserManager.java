package com.divx.service.domain.manager;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.AuthHelper;
import com.divx.service.Email;
import com.divx.service.ConfigurationManager;
import com.divx.service.MessageServiceHelper;
import com.divx.service.Util;
import com.divx.service.auth.DivxAuthUserService;
import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.auth.model.DivXAuthUser;
import com.divx.service.domain.manager.SendEmailHelper.WatchMonitor;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpOauthUsers;
import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpRole;
import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.DcpUserExt;
import com.divx.service.domain.model.DcpUserRole;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.domain.repository.TokenDao;
import com.divx.service.im.HXImHelper;
import com.divx.service.model.AuthResponse;
import com.divx.service.model.ChangePasswordOption;
import com.divx.service.model.CheckUserResponse;
import com.divx.service.model.EmailTemplate;
import com.divx.service.model.FindUserOption;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueStringPair;
import com.divx.service.model.MailSetting;
import com.divx.service.model.OAuthOption;
import com.divx.service.model.UserResponse;
import com.divx.service.model.OAuthOption.eAuthProvider;
import com.divx.service.model.Organization;
import com.divx.service.model.QQUserInfo;
import com.divx.service.model.RegisterOption;
import com.divx.service.model.RegisterOption.eRegisterType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.User;
import com.divx.service.model.UserInfoResponse;
import com.divx.service.model.UsersResponse;
import com.divx.service.model.WeixinRet;
import com.divx.service.model.WeixinUserInfo;
import com.divx.service.model.UserInfoResponse.RegisterType;
import com.divx.service.model.UserOption;
import com.divx.service.model.UserOption.OptionType;
import com.divx.service.model.StartupResponse;
import com.divx.service.model.UserPhotoOption;
import com.divx.service.model.UserProfile;
import com.divx.service.model.UserProfileResponse;
import com.divx.service.model.im.HxUser;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


@Service
public class UserManager {
	public static final Cache<Integer, UserProfile> cacheProfile = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(6, TimeUnit.HOURS).build();
	public static final Cache<String, List<OsfUsers>> cacheFindUsers = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(6, TimeUnit.HOURS).build();
	public static final Cache<String, UsersResponse> cacheListUsers = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(6, TimeUnit.HOURS).build();
	public static final Cache<String, StartupResponse> cacheStartup = CacheBuilder.newBuilder().maximumSize(5).expireAfterWrite(1, TimeUnit.HOURS).build();
	
	private static final Logger log =  Logger.getLogger(UserManager.class);
	private final static DivxAuthUserService userDetailsService = new DivxAuthUserService();

	private DivxUserDao divxUserDao;
	private TokenDao tokenDao;
	
	@Autowired
	public void setDivxUserDao(DivxUserDao divxUserDao) {
		this.divxUserDao = divxUserDao;
	}
	
	@Autowired
	public void setTokenDao(TokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}
	//public  static boolean sendEmailEnable = ConfigurationManager.GetInstance().GetConfigValue("SendEmail_Enable", false);
	static com.divx.service.domain.manager.SendEmailHelper.WatchMonitor monitor;
	static Thread threadMonitor;
	
	static{
		if(false){
			SendEmailHelper helper = new SendEmailHelper();
			monitor = helper.new WatchMonitor();
			
			threadMonitor = new Thread(monitor);
			threadMonitor.start();
		}
	
	  }
	public AuthResponse Login(String username, String password, String deviceUniqueId, String deviceType, int orgId)
	{
		AuthResponse res = new AuthResponse();
		try
		{
			if (username.isEmpty() || password.isEmpty())
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_USERNAME_OR_PASSWORD_INVALID);
				res.setResponseMessage("Invalid username or password!");
				return res;
			}
			OsfUsers user = divxUserDao.GetUserByUsername(orgId, username);
			if (user == null)
			{
				if (username.indexOf("@") > 0)
				{
					user = divxUserDao.GetUserByEmail(orgId, username);
				}
				else
				{
					user = divxUserDao.GetUserByMobile(orgId, username);
				}
			}
			
			if (user == null || !StringUtils.equals(password, user.getPassword()))
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_USERNAME_OR_PASSWORD_INVALID);
				res.setResponseMessage("Invalid username or password!");
				return res;
			}
			
			UUID uid = UUID.randomUUID();
			user.setToken(uid.toString());
			user.setLastLogin(new Date());
			divxUserDao.UpdateUser(user);
			res.setResponseMessage("Success");

			res = generateAuthResponse(deviceUniqueId, user, deviceType);
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("Login(%s)", username), ex);
		}
			
		return  res;
	}
	public AuthResponse generateAuthResponse(String deviceUniqueId,OsfUsers user,String deviceType){
		DcpToken token = tokenDao.GetToken(deviceUniqueId, user.getId().intValue());
		AuthResponse ar = new AuthResponse();
		UUID uid = UUID.randomUUID();
		if (token == null)
		{
			token = new DcpToken();
			token.setDatecreated(new Date());
			token.setDatemodified(new Date());
			token.setDeviceuniqueid(deviceUniqueId);
			try
			{
				int nDeviceType = Integer.parseInt(deviceType);
				token.setDevicetype(nDeviceType);
			}
			catch(Exception ex)
			{
				ar.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				ar.setResponseMessage(String.format("Invalid deviceType(%s)", deviceType));
				return ar;
			}
			token.setDeviceguid(Util.EncryptDeviceUniqueId(deviceUniqueId, user.getId()));
			token.setIsactive(true);
			token.setUserId(user.getId().intValue());
			
			token.setToken(uid.toString());
			token.setExpiredate(Util.GetDate(new Date(), Calendar.MONTH, 24));
			int tokenId = tokenDao.CreateToken(token);
			if(tokenId <= 0){
				ar.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				ar.setResponseMessage("Invalid DeviceType " + deviceType);
				return ar;
			}
		}
		else
		{
			token.setDatemodified(new Date());
			token.setIsactive(true);
			token.setUserId(user.getId().intValue());
			token.setToken(uid.toString());
			token.setExpiredate(Util.GetDate(new Date(), Calendar.MONTH, 24));
			int tokenId = tokenDao.UpdateToken(token);
			if(tokenId <= 0){
				ar.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				ar.setResponseMessage("Invalid DeviceType " + deviceType);
				return ar;
			}
		}
		DcpUserRole userRole = divxUserDao.GetRoleByUserId(user.getId().intValue());
		if(userRole != null){
			ar.setRoleName(userRole.getDcpRole().getRoleName());
		}else{
			ar.setRoleName("");
		}
		MessageServiceHelper helper = new MessageServiceHelper();
		ServiceResponse sr = helper.RegisterDevice(token.getDeviceguid(), 
				token.getDevicetype(), 
				user.getUsername(), 
				user.getNickname().isEmpty() ? user.getUsername() : user.getNickname(), 
				user.getPhotourl());
		if (sr.getResponseCode() != 0)
		{
			ar.setResponseMessage(String.format("Fail to register device on message service. ResponseCode(%d), %s", sr.getResponseCode(), sr.getResponseMessage()));
		}
		ar.setDeviceGuid(token.getDeviceguid());
		ar.setToken(new DivXAuthToken(user, token).GetAuthTokenString(new DivXAuthUser(user)));	
		User u = new User();
		u.setUserId(user.getId().intValue());
		u.setUsername(user.getUsername());
		u.setNickname(user.getNickname());
		if(user.getPhotourl() != null && !user.getPhotourl().isEmpty()){
			u.setPhotourl(Util.UrlWithHttp(user.getPhotourl()));
		}else{
			u.setPhotourl("");
		}
	
		ar.setUser(u);
		ar.setResponseMessage("success");
		ar.setResponseCode(ResponseCode.SUCCESS);				
		return ar;
		
	}
	public AuthResponse OAuthLogin(OAuthOption option, String deviceUniqueId, String deviceType, int orgId,String token)
	{
		AuthResponse res = new AuthResponse();
		try{
			OsfUsers user  = null;
			DcpOauthUsers oauthUser = null;
			if(option.getAccessToken() == null || option.getOpenId() == null ||option.getAuthProvider() == null){
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid AccessToken or OpenId");
				return res;
			}
			if(token == null || token.isEmpty()){
				switch(option.getAuthProvider())
				{
				case QQ:
					user = QQLogin(option.getAccessToken(),option.getOpenId(),orgId);
				break;
				case Weixin:
					user = WeixinLogin(option.getAccessToken(),option.getOpenId(),orgId);
					break;
				default:
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("authProvider type can only be QQ or Weixin");
					return res;
				}
				if(user == null){
					res.setResponseCode(ResponseCode.AUTH_ERROR_INVALID_OAUTH_TOKEN);
					res.setResponseMessage("Invalid oauth token");
					return res;
				}
				
				oauthUser = divxUserDao.GetDcpOauthUser(option.getOpenId(), option.getAuthProvider().ordinal());
				if(oauthUser == null){
					user.setUsernameStatus(false);
					int userId = divxUserDao.CreateUser(user);	
					if(userId > 0){
						String username = String.format("tyty%06d", userId);
						if(divxUserDao.GetUserByUsername(orgId, username) != null){
							username = String.format("%s%d", username , System.currentTimeMillis()/1000);
						}
						user.setUsername(username);
						divxUserDao.UpdateUser(user);
						oauthUser = new DcpOauthUsers();
						oauthUser.setAccessToken(option.getAccessToken());
						oauthUser.setOpenId(option.getOpenId());
						oauthUser.setOsfUsers(user);
						oauthUser.setOauthType(option.getAuthProvider().ordinal());
						oauthUser.setCreateDate(new Date());
						oauthUser.setModifyDate(new Date());
						int oUId = divxUserDao.createOauthUser(oauthUser);	
						ImHelper.RegisterUser(user.getId().intValue(), user.getNickname());
						cacheProfile.invalidate(userId);
					}
				}
				else
				{
					oauthUser.setAccessToken(option.getAccessToken());
					oauthUser.setModifyDate(new Date());
					int oUId = divxUserDao.createOauthUser(oauthUser);	
					user = divxUserDao.GetUser(oauthUser.getOsfUsers().getId().intValue());
				}
			}
			else
			{
				//Bind the account with the OAuth.
				AuthHelper  helper = new AuthHelper(token);
				if(helper.isGuest()){
					res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
					res.setResponseMessage("you must be login befor set QQ or Weixin.");
					return res;
				}
				oauthUser = divxUserDao.GetDcpOauthUser(option.getOpenId(), option.getAuthProvider().ordinal());
				if (oauthUser != null && oauthUser.getOsfUsers().getId().intValue() != helper.getUserId())
				{
					res.setResponseCode(ResponseCode.AUTH_ERROR_OAUTH_HAS_BOUND);
					res.setResponseMessage("您的QQ或微信号已经被绑定其它账号。");
					return res;	
				}
				
				user = divxUserDao.GetUser(helper.getUserId());
				if(eRegisterType.Guest.ordinal() == user.getUsernametype()){
					OsfUsers u = null;
					switch(option.getAuthProvider())
					{
					case QQ:
						u = QQLogin(option.getAccessToken(),option.getOpenId(),orgId);
						user.setUsernametype(eRegisterType.QQ.ordinal());
						break;
					case Weixin:
						u = WeixinLogin(option.getAccessToken(),option.getOpenId(),orgId);
						user.setUsernametype(eRegisterType.Weixin.ordinal());
						break;
					default:
						res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
						res.setResponseMessage("authProvider type can only be QQ or Weixin");
						return res;
					}
					if(u == null){
						res.setResponseCode(ResponseCode.AUTH_ERROR_INVALID_OAUTH_TOKEN);
						res.setResponseMessage("Invalid oauth token");
						return res;
					}
					else
					{
						user.setNickname(u.getNickname()); 
						user.setPhotourl(u.getPhotourl());
						user.setUsername(String.format("tyty%06d", helper.getUserId()));
						user.setLastLogin(new Date());
						divxUserDao.UpdateUser(user);
						cacheProfile.invalidate(helper.getUserId());
						ImHelper.RegisterUser(user.getId().intValue(), user.getNickname());
					}
				}
				
				if(oauthUser == null){
					oauthUser = new DcpOauthUsers();
					oauthUser.setAccessToken(option.getAccessToken());
					oauthUser.setOpenId(option.getOpenId());
					oauthUser.setOsfUsers(user);
					oauthUser.setOauthType(option.getAuthProvider().ordinal());
					oauthUser.setCreateDate(new Date());
					oauthUser.setModifyDate(new Date());
				}
				else
				{
					oauthUser.setAccessToken(option.getAccessToken());
					oauthUser.setModifyDate(new Date());
				}
				divxUserDao.createOauthUser(oauthUser);	
			}
	
			if(user != null){
				if(token == null || token.isEmpty()){
					res = generateAuthResponse(deviceUniqueId, user, deviceType);	
				}
				else
				{
					res.setDeviceGuid(Util.EncryptDeviceUniqueId(deviceUniqueId, user.getId()));
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("success");
					User u = new User();
					u.setUserId(user.getId().intValue());
					u.setUsername(user.getUsername());
					u.setNickname(user.getNickname());
					if(user.getPhotourl() != null && !user.getPhotourl().isEmpty()){
						u.setPhotourl(Util.UrlWithHttp(user.getPhotourl()));
					}else{
						u.setPhotourl("");
					}
				
					res.setUser(u);
					res.setToken(token);
				}
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("fail to OAuthLogin");
				return res;
			}
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		return res;
	}
	
	public OsfUsers WeixinLogin(String accessToken,String openId,int orgId){
		OsfUsers u = null;
		try{
			
				String checkTokenUrl = String.format(ConfigurationManager.GetInstance().CheckWeixinTokenUrl(),accessToken,openId);
				String checkTokenRet = Util.HttpGet(checkTokenUrl);
				if(checkTokenRet.isEmpty())
					return null;
				WeixinRet obj = Util.JsonToObject(checkTokenRet, WeixinRet.class);
				if(obj == null)
					return null;
				if(obj.getErrcode() == 0 && "ok".equals(obj.getErrmsg())){
					u = new OsfUsers();
					String getUserInfoUrl = String.format(ConfigurationManager.GetInstance().GetWeixinUserInfoUrl(), accessToken,openId);
					String userInfo = Util.HttpGet(getUserInfoUrl);
					if(userInfo.isEmpty())
						return null;
					WeixinUserInfo user = Util.JsonToObject(userInfo, WeixinUserInfo.class);
					if(user == null)
						return null;
					u.setEmail("");
					u.setEnabled(true);
					u.setEntered(new Date());
					u.setLastLogin(new Date());
					u.setLocale("");
					u.setMobile("");
					u.setNickname(user.getNickname());
					u.setOrganizationId(orgId);
					u.setPassword("");
					u.setPhotourl(user.getHeadimgurl());
					u.setTimezone("");
					u.setProjectId(new Long(1));
					UUID uid = UUID.randomUUID();
					u.setToken(uid.toString());
					u.setUsername("");
					u.setUsernametype(eRegisterType.Weixin.ordinal());//0 username,1 email, 2 mobile,3 weixin, 4 QQ
					
					
				}
			
				
			
		}catch(Exception ex){
			return null;
		}
		
		return u;
	}
	public OsfUsers QQLogin(String accessToken,String openId,int orgId){
		OsfUsers u = null;
	
		try{
			String oauth_consumer_key = ConfigurationManager.GetInstance().GetOauth_consumer_key();
			String getUserInfoUrl = String.format(ConfigurationManager.GetInstance().GetQQUserInfoUrl(), accessToken,oauth_consumer_key,openId);
			String userInfo = Util.HttpGet(getUserInfoUrl);
			if(userInfo.isEmpty())
				return null;
			QQUserInfo user = Util.JsonToObject(userInfo, QQUserInfo.class);
			if(user == null || user.getRet() != 0)
				return null;
			u = new OsfUsers();
			u.setEmail("");
			u.setEnabled(true);
			u.setEntered(new Date());
			u.setLastLogin(new Date());
			u.setLocale("");
			u.setMobile("");
			u.setNickname(user.getNickname());
			u.setOrganizationId(orgId);
			u.setPassword("");
			u.setPhotourl(user.getFigureurl_qq_2());
			u.setTimezone("");
			u.setProjectId(new Long(1));
			UUID uid = UUID.randomUUID();
			u.setToken(uid.toString());
			u.setUsername("");
			u.setUsernametype(eRegisterType.QQ.ordinal());
			
			
		}catch(Exception ex){
			return null;
		}
		
		return u;
	}
	
	public AuthResponse Register(RegisterOption option, String deviceUniqueId, String deviceType, int orgId)
	{
		AuthResponse res = new AuthResponse();
		try {
			if(option == null || eRegisterType.Guest == option.getRegisterType()){
				OsfUsers user = divxUserDao.GetUserByDeviceUniqueId(deviceUniqueId, eRegisterType.Guest.ordinal());
				if(user == null){
					user = new OsfUsers();
					user.setEmail("");
					user.setEnabled(true);
					user.setEntered(new Date());
					user.setLastLogin(new Date());
					user.setLocale("");
					user.setMobile("");
					//SecureRandom random = new SecureRandom();  
					user.setNickname("");
					user.setOrganizationId(orgId);
					user.setPassword("");
					user.setPhotourl("");
					user.setTimezone("");
					user.setProjectId(new Long(1));
					UUID uid = UUID.randomUUID();
					user.setToken(uid.toString());
					user.setUsername("");
					user.setUsernametype(eRegisterType.Guest.ordinal());//0 username,1 email, 2 mobile,3 weixin, 4 QQ, 5 Guest
					user.setUsernameStatus(false);
					int userId = divxUserDao.CreateUser(user);	
					if(userId < 0){
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("fail to create user for guest");
						return res;
					}
					user.setNickname(String.format("游客%06d", userId));
					String username = String.format("Guest%06d", userId);
					if(divxUserDao.GetUserByUsername(orgId, username) != null){
						username = String.format("%s%d", username , System.currentTimeMillis()/1000);
					}
					user.setUsername(username);
					if(divxUserDao.UpdateUser(user) < 0){
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("fail to create user for guest");
						return res;
					}
					
				}
				
				res = generateAuthResponse(deviceUniqueId, user, deviceType);	
				res.setRegisterType(eRegisterType.Guest);
			}
			else
			{
				if (option.getPassword() == null || option.getPassword().isEmpty() 
						|| option.getRepassword() == null || option.getPassword().compareTo(option.getRepassword()) != 0) {
						res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_INVALID_PASSWORD);
						res.setResponseMessage("Invalid password");
						return res;
					}
	
				OsfUsers user = new OsfUsers();
				user.setUsername(option.getUsername().trim());
				user.setPassword(option.getPassword().trim());
				//user.setRePassword(option.getRepassword());
				user.setNickname(option.getUsername().trim());
				user.setEnabled(true);
				user.setEntered(new Date());
				user.setTimezone("");
				user.setLocale("");
				user.setProjectId(new Long(1));
				user.setOrganizationId(orgId);
				
				switch (option.getRegisterType()) {
					case username:
						user.setEmail("");
						user.setMobile("");
						user.setUsernameStatus(true);
						break;
					case email:
						user.setEmail(option.getUsername().trim());
						user.setMobile("");
						user.setUsernameStatus(false);
						break;
					case mobile:
						user.setEmail("");
						user.setMobile(option.getUsername().trim());
						user.setUsernameStatus(false);
						break;
				}
				user.setUsernametype(option.getRegisterType().ordinal());
					
				int nRet = this.CheckRegisterUsername(user, orgId);//userService.createUser(user);
				if (nRet != 0)
				{
					res.setResponseCode(nRet);
					String errMsg = String.format("Error Code(%d)", nRet);
					switch (nRet) {
						case ResponseCode.ERROR_REGISTER_USER_USERNAME_EXIST:
							errMsg = "Username exists.";
							break;
						case ResponseCode.ERROR_REGISTER_USER_EMAIL_EXIST:
							errMsg = "Email exists.";
							break;
						case ResponseCode.ERROR_REGISTER_USER_MOBILE_EXIST:
							errMsg = "Mobile exists.";
							break;
					}
					res.setResponseMessage(errMsg);
					return res;
				}
					
				UUID uid = UUID.randomUUID();
				user.setToken(uid.toString());
				user.setLastLogin(new Date());
				int nUserId = divxUserDao.CreateUser(user);
				if(nUserId > 0){
					DcpUserRole userRole = new DcpUserRole(); 
					userRole.setOsfUsers(user);
					DcpRole role = divxUserDao.GetRole(2);//1 admin 2 user
						
					userRole.setDcpRole(role);
					userRole.setCreateDate(new Date());
					userRole.setModifyDate(new Date());
					int roleId = divxUserDao.createUserRole(userRole);
						
					res = generateAuthResponse(deviceUniqueId, user, deviceType);
				}
				
				if (res.getResponseCode() == ResponseCode.SUCCESS)
				{	
					ImHelper.RegisterUser(nUserId, user.getNickname());
				}
			}
			ClearUserCache();
				
		}
		catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			log.error(option, ex);
		}	
		
		return res;
	}
	
	public CheckUserResponse CheckToken(DivXAuthToken token)
	{
		CheckUserResponse res = new CheckUserResponse();
		
		try
		{
			String authUsername = token.GetAuthUsername();
			DivXAuthUser user = (DivXAuthUser) userDetailsService.loadUserByUsername(authUsername);
			if (user == null)
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
				res.setResponseMessage("Invalid Token");
				return res;
			}
			DivXAuthToken oldToken = new DivXAuthToken();
			oldToken.setUserId(user.getUserId());
			oldToken.setUsername(user.getUsername());
			oldToken.setDcpToken(user.getToken().getToken());
			oldToken.setExpireDate(user.getToken().getExpiredate());
			//oldToken.setOrgId(user.get);
			//String aaaa = oldToken.GetAuthTokenString(user);
			if (user != null && DivXAuthToken.IsValidAuthToken(token, user)){
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");

				res.setUserId(user.getUserId());
				res.setUsername(user.getUsername());
				
				res.setDeviceType(user.getToken().getDevicetype());
				res.setDeviceGuid(user.getToken().getDeviceguid());
				res.setDeviceUniqueId(user.getToken().getDeviceuniqueid());
				res.setOwnId(0);

				res.setToken(token.GetAuthTokenString(user));
			}
			else
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
				res.setResponseMessage("Invalid Token");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
			Util.LogError(log, String.format("CheckToken(%s) exception", token.GetAuthUsername()), ex);
		}
		
		return res;
	}
	
	public ServiceResponse Logout(String strToken)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{	
			if (strToken == null || strToken.trim() == "")
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
				res.setResponseMessage("Invalid Token or Not Login");
				return res;
			}
			
			DcpToken token = tokenDao.GetToken(strToken);//userService.getUser(new Long(helper.getUserId()));
			if (token == null)
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
				res.setResponseMessage("Invalid Token or Not Login");
				return res;
			}
			
			res.setResponseMessage("Success");

			token.setIsactive(false);
			token.setDatemodified(new Date());
			tokenDao.UpdateToken(token);
			
			if (token.getDeviceguid() != null && !token.getDeviceguid().isEmpty())
			{
				MessageServiceHelper msh = new MessageServiceHelper();
				ServiceResponse sr = msh.UnregisterDevice(token.getDeviceguid());
				if (sr.getResponseCode() != 0)
				{
					res.setResponseMessage(String.format("Fail to unregister device on message service. ResponseCode(%d), %s", sr.getResponseCode(), sr.getResponseMessage()));
				}
			}
			
			res.setResponseCode(ResponseCode.SUCCESS);
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			log.error("Logout Exception", ex);
		}
		return res;
	}
	
	public StartupResponse Startup(String deviceUniqueId)
	{
		StartupResponse res;
		try
		{
			try
			{
				divxUserDao.LogLastAccess(deviceUniqueId);
				cacheListUsers.invalidateAll();
			}
			catch(Exception ee)
			{
				
			}
			
			String key = "startup";
			res = cacheStartup.getIfPresent(key);
			if (res != null)
				return res;
			
			res = new StartupResponse();
			
			ConfigurationManager mgr = ConfigurationManager.GetInstance();
			
			List<KeyValueStringPair>	baseUrls = new ArrayList<KeyValueStringPair>();
			
			baseUrls.add(new KeyValueStringPair("MediaServiceBaseUrl", mgr.MediaServiceBaseUrl()));
			baseUrls.add(new KeyValueStringPair("MessageServiceBaseUrl", mgr.MessageServiceBaseUrl()));
			baseUrls.add(new KeyValueStringPair("UserServiceBaseUrl", mgr.UserServiceBaseUrl()));
			baseUrls.add(new KeyValueStringPair("SocialServiceBaseUrl", mgr.SocialServiceBaseUrl()));
			baseUrls.add(new KeyValueStringPair("TranscodeServiceBaseUrl", mgr.TranscodeServiceBaseUrl()));
			baseUrls.add(new KeyValueStringPair("UploadMediaUrl", mgr.UploadMediaUrl()));		
			baseUrls.add(new KeyValueStringPair("UserPhotoUploadUrl", mgr.UserPhotoUploadUrl()));		
			baseUrls.add(new KeyValueStringPair("GroupPhotoUplaodUrl", mgr.GroupPhotoUplaodUrl()));		
			baseUrls.add(new KeyValueStringPair("MediaWebServerUrl", mgr.GetConfigValue("MediaWebServerUrl", "http://121.40.71.225/media")));	
			res.setBaseUrls(baseUrls);
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			
			cacheStartup.put(key, res);
		}
		catch(Exception e)
		{
			res = new StartupResponse();
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}
	private int CheckRegisterUsername(OsfUsers user, int orgId)
	{
		switch(user.getUsernametype())
		{
		case 0:	// username
			if (divxUserDao.GetUserByUsername(orgId, user.getUsername()) != null)
				return ResponseCode.ERROR_REGISTER_USER_USERNAME_EXIST;
			break;
		case 1:	// email
			if (divxUserDao.GetUserByEmail(orgId, user.getUsername()) != null)
				return ResponseCode.ERROR_REGISTER_USER_EMAIL_EXIST;
			break;
		case 2: // mobile
			if (divxUserDao.GetUserByMobile(orgId, user.getUsername()) != null)
				return ResponseCode.ERROR_REGISTER_USER_MOBILE_EXIST;
			break;
		}
		
		return 0;
	}
	
	public UserResponse UpdateUser(UserOption option,int userId)
	{
		UserResponse res = new UserResponse();
		try
		{	
			OsfUsers u = divxUserDao.GetUser(userId);
			if(u != null){
				switch(option.getType())
				{
					case username:
						{
							if(option.getValue().isEmpty()){
								res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
								res.setResponseMessage("Username cannot be null.");
								return res;
							}
							
							if(!u.getUsernameStatus()){
								if (!option.getValue().equals(u.getUsername()) && 
									divxUserDao.GetUserByUsername(u.getOrganizationId(), option.getValue()) != null){
									res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_USERNAME_EXIST);
									res.setResponseMessage("Username has been Exist.");
									return res;
								}else{
									if (u.getUsernametype() == eRegisterType.Guest.ordinal())
									{
										if(option.getPassword().isEmpty()){
											res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
											res.setResponseMessage("Password cannot be null.");
											return res;
										}
										u.setNickname(option.getValue());
										u.setPassword(option.getPassword());
										ImHelper.RegisterUser(u.getId().intValue(), u.getNickname());
									}else if(u.getUsernametype() == eRegisterType.QQ.ordinal() || u.getUsernametype() == eRegisterType.Weixin.ordinal()){
										if(u.getPassword().isEmpty() && option.getPassword().isEmpty()){
											res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
											res.setResponseMessage("Password cannot be null.");
											return res;
										}
										if (option.getPassword() != null && !option.getPassword().isEmpty())
											u.setPassword(option.getPassword());
										ImHelper.RegisterUser(u.getId().intValue(), u.getNickname());
									}
									u.setUsername(option.getValue());
									u.setUsernametype(OptionType.username.ordinal());
									u.setUsernameStatus(true);
								}
							}else{
								res.setResponseCode(ResponseCode.ERROR_UPDATE_USER_USERNAME_HAVE_SET);
								res.setResponseMessage("Username has been set.");
								return res;
							}
							break;
						}
					case email:
						{
							
						/*	if(user.getUsernametype() == OptionType.username.ordinal()){*/
								if(!option.getValue().isEmpty()){
									if (divxUserDao.GetUserByEmail(u.getOrganizationId(), option.getValue()) != null
										&& divxUserDao.GetUserByUsername(u.getOrganizationId(), option.getValue()) != null){
										res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_EMAIL_EXIST);
										res.setResponseMessage("Email exists");
										return res;
									}
								}
								if (u.getUsernametype() == eRegisterType.Guest.ordinal())
								{
									if(option.getPassword().isEmpty()){
										res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
										res.setResponseMessage("Password cannot be null.");
										return res;
									}
									u.setNickname(option.getValue());
									u.setPassword(option.getPassword());
									u.setUsername(option.getValue());
									ImHelper.RegisterUser(u.getId().intValue(), u.getNickname());
								}else if(u.getUsernametype() == eRegisterType.QQ.ordinal() || u.getUsernametype() == eRegisterType.Weixin.ordinal()){
									if(u.getPassword().isEmpty() && option.getPassword().isEmpty()){
										res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
										res.setResponseMessage("Password cannot be null.");
										return res;
									}
									if (option.getPassword() != null && !option.getPassword().isEmpty())
										u.setPassword(option.getPassword());
									ImHelper.RegisterUser(u.getId().intValue(), u.getNickname());
								}
								u.setEmail(option.getValue());
								u.setUsernametype(OptionType.email.ordinal());
								
							/*}else{
								res.setResponseCode(ResponseCode.ERROR_UPDATE_USER_USERNAME_NOT_SET);
								res.setResponseMessage("Username isn't set.");
								return res;
							}*/
							break;
						}
					case mobile:
						{
							/*if(user.getUsernametype() == OptionType.username.ordinal()){*/
								if(!option.getValue().isEmpty()){
									if (divxUserDao.GetUserByMobile(u.getOrganizationId(), option.getValue()) != null
										&& divxUserDao.GetUserByUsername(u.getOrganizationId(), option.getValue()) != null){
									res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_MOBILE_EXIST);
									res.setResponseMessage("mobile exists");
									return res;
									}
								}
								if (u.getUsernametype() == eRegisterType.Guest.ordinal())
								{
									if(option.getPassword().isEmpty()){
										res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
										res.setResponseMessage("Password cannot be null.");
										return res;
									}
									u.setNickname(option.getValue());
									u.setUsername(option.getValue());
									u.setPassword(option.getPassword());
									ImHelper.RegisterUser(u.getId().intValue(), u.getNickname());
								}else if(u.getUsernametype() == eRegisterType.QQ.ordinal() || u.getUsernametype() == eRegisterType.Weixin.ordinal()){
									if(u.getPassword().isEmpty() && option.getPassword().isEmpty()){
										res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
										res.setResponseMessage("Password cannot be null.");
										return res;
									}
									if (option.getPassword() != null && !option.getPassword().isEmpty())
										u.setPassword(option.getPassword());
									ImHelper.RegisterUser(u.getId().intValue(), u.getNickname());
								}
								u.setMobile(option.getValue());
								u.setUsernametype(OptionType.mobile.ordinal());
							/*}else{
								res.setResponseCode(ResponseCode.ERROR_UPDATE_USER_USERNAME_NOT_SET);
								res.setResponseMessage("Username isn't set.");
								return res;
							}*/
							break;
						}
				
					default:
						{
							res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
							res.setResponseMessage("Invalid Parameter");
							return res;
						}
				}
			}
			else{
				res.setResponseCode(ResponseCode.RESULT_USER_NOT_FOUND);
				res.setResponseMessage("user not found");
				return res;
			}
			int mid = divxUserDao.UpdateUser(u);
			if(mid > 0){
				cacheProfile.invalidate(userId);
				ClearUserCache();
				User user = new User();
				user.setUserId(u.getId().intValue());
				user.setUsername(u.getUsername());
				user.setNickname(u.getNickname());
				user.setPhotourl(u.getPhotourl());
				res.setUser(user);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to Update user");
				return res;
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}
	public ServiceResponse ResetPassword(UserOption.OptionType option,String value, int orgId) {
		ServiceResponse res = new ServiceResponse();
		try
		{	OsfUsers user = null;
			String emailadds = "";
			switch(option){
				case username:{
					user = divxUserDao.GetUserByUsername(orgId, value);
					if(user == null){
						res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
						res.setResponseMessage("Your username is not exist");
						return res;
					}
					emailadds = user.getEmail();
					if(emailadds.isEmpty()){
						res.setResponseCode(ResponseCode.ERROR_USER_EMAIL_NOT_EXIST);
						res.setResponseMessage("Your account is not set a email");
						return res;
					}
					break;
				}
				case email:{
					user = divxUserDao.GetUserByEmail(orgId, value);
					if(user == null){
						res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
						res.setResponseMessage("Your email is not exist");
						return res;
					}
					emailadds = user.getEmail();
					if (emailadds.isEmpty()){
						res.setResponseCode(ResponseCode.ERROR_USER_EMAIL_NOT_EXIST);
						res.setResponseMessage("Your input an error email address.");
						return res;
					}
					
					break;
				}
				case mobile:{
					user = divxUserDao.GetUserByMobile(orgId, value);
					if(user == null){
						res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
						res.setResponseMessage("Your mobile is not exist");
						return res;
					}
					emailadds = user.getEmail();
					if(emailadds.isEmpty()){
						res.setResponseCode(ResponseCode.ERROR_USER_EMAIL_NOT_EXIST);
						res.setResponseMessage("Your account is not set a email");
						return res;
					}
					break;
				}
				default:{
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid Parameter");
					return res;
				}
			}
			if(false){
				if(!threadMonitor.isAlive()) {
					threadMonitor = new Thread(monitor);
					threadMonitor.start();
				}
			}
			DcpEmailJob job = new DcpEmailJob();
			job.setStatus(false);
			job.setCreatedate(new Date());
			job.setModifydate(new Date());
			job.setUserId(user.getId().intValue());
			job.setEmailAddress(emailadds);
			job.setContent("");
			job.setAttempts(0);
			int emailJobId = divxUserDao.SaveEmailJob(job);
			if(emailJobId > 0){
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fali to send email to reset password");
			}
			
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}
	public ServiceResponse ChangePassword(ChangePasswordOption option,
			int userId) {
		ServiceResponse res = new ServiceResponse();
		try
		{	
			OsfUsers user = divxUserDao.GetUser(userId);
			if(option.getOldPassword().equals(user.getPassword())){
				if(!option.getNewPassword().equals(user.getPassword())){
					user.setPassword(option.getNewPassword());
				}else{
					res.setResponseCode(ResponseCode.ERROR_NEW_PASSWORD_CANNOT_BE_SAME_ORIGINAL_PASSWORD);
					res.setResponseMessage("Your new password cannot be same with the original password");
					return res;
				}
				
			}else{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Your oldPassword is not ture");
				return res;
			}
			
			int mid = divxUserDao.UpdateUser(user);
			if(mid > 0){
			 res.setResponseCode(ResponseCode.SUCCESS);
			 res.setResponseMessage("Success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to ChangePassword.");
				return res;
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}
	
	public UserProfileResponse GetMyProfile(int userId) {
		UserProfileResponse res = new UserProfileResponse();
		try
		{	
			UserProfile userProfile = cacheProfile.getIfPresent(userId);
			if (userProfile == null)
			{
				//OsfUsers user = divxUserDao.GetUser(userId);
				KeyValuePair<OsfUsers, DcpOrganization> ret = divxUserDao.GetUserInfo(userId);
				OsfUsers user = ret.getKey();
				DcpUserExt userExt = divxUserDao.GetUserExt(userId);
				if(user != null){
					userProfile = new UserProfile();
					
					if(userExt != null){
						userProfile.setRealname(userExt.getRealname());
						userProfile.setBirthday(userExt.getBirthday());
						userProfile.setComefrom(userExt.getComefrom());
						userProfile.setEmail2(userExt.getEmail2());
						userProfile.setGender(userExt.getGender());
						
						if(user.getOrganizationId() > 10000 && 
							user.getOrganizationId() < 20000 && 
							userExt.getHomepage() != null && 
							!userExt.getHomepage().trim().toLowerCase().startsWith("http")){
							//DcpOrganization org = divxUserDao.GetOrganization(user.getOrganizationId());
							DcpOrganization org = ret.getValue();
							if(org != null && org.getHomeurl() !=null && !org.getHomeurl().isEmpty()){
								userProfile.setHomepage(Util.UrlWithSlashes(org.getHomeurl()) + userExt.getHomepage());
							}
						}
						else
						{
							userProfile.setHomepage(userExt.getHomepage());
						}
						userProfile.setIm(userExt.getIm());
						userProfile.setIm2(userExt.getIm2());
						userProfile.setMobile2(userExt.getMobile2());
						userProfile.setSelfIntroduce(userExt.getSelfintroduce());
						userProfile.setSignature(userExt.getSignature());
						userProfile.setTdcImgUrl(userExt.getTdcImg());
						userProfile.setTelephone(userExt.getTelephone());	
					}

					userProfile.setRegisterType(eRegisterType.values()[user.getUsernametype()]);
					userProfile.setUserId(userId);
					userProfile.setNickname(user.getNickname());
					userProfile.setPhotourl(user.getPhotourl());
				}
				
				if (userProfile != null)
				{
					cacheProfile.put(userId, userProfile);
				}
			}
			
			if (userProfile != null)
			{
				res.setUserProfile(userProfile);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("fail to get user by Id ");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}
	public ServiceResponse SetMyProfile(UserProfile userProfile,int userId) {
		ServiceResponse res = new ServiceResponse();
		try
		{	
			if(userProfile != null){
				DcpUserExt userExt = new DcpUserExt();
				userExt.setUserId(userId);
				userExt.setRealname(userProfile.getRealname());
				userExt.setBirthday(userProfile.getBirthday());
				userExt.setComefrom(userProfile.getComefrom());
				userExt.setEmail2(userProfile.getEmail2());
				userExt.setGender(userProfile.getGender());
				userExt.setHomepage(userProfile.getHomepage());
				userExt.setIm(userProfile.getIm());
				userExt.setIm2(userProfile.getIm2());
				userExt.setMobile2(userProfile.getMobile2());
				userExt.setSelfintroduce(userProfile.getSelfIntroduce());
				userExt.setSignature(userProfile.getSignature());
				userExt.setTdcImg(userProfile.getTdcImgUrl());
				userExt.setTelephone(userProfile.getTelephone());
				
				OsfUsers user = divxUserDao.GetUser(userId);
				if(user != null){
					if(userProfile.getNickname() != null ){					
						user.setNickname(userProfile.getNickname());
					}else{
						user.setNickname(user.getUsername());
					}
					int uid = divxUserDao.UpdateUser(user);
					if(uid <= 0){
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("fail to update user.");
						return res;
					}
				}else{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("fail to get user by Id ");
					return res;
				}
				int mid = divxUserDao.SetUserExt(userExt);
				if(mid > 0){
					cacheProfile.invalidate(userId);
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("success");
				}else{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to SetMyProfile.");
				}
			}else{
				
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}
	public UserProfileResponse GetUserProfile(int orgId,String username) {
		UserProfileResponse res = new UserProfileResponse();
		try
		{	
			/*List<KeyValuePair<OsfUsers, DcpUserExt>> objs = divxUserDao.GetUserExtByUsername(orgId, username);
			if(objs != null && objs.size() > 0 ){
				for(KeyValuePair<OsfUsers, DcpUserExt> obj: objs)
				{
					UserProfile userProfile = new UserProfile();
					
					OsfUsers user = obj.getKey();
					DcpUserExt userExt = obj.getValue();
					if(user != null){
						if(userExt != null){
							userProfile.setRealname(userExt.getRealname());
							userProfile.setBirthday(userExt.getBirthday());
							userProfile.setComefrom(userExt.getComefrom());
							userProfile.setEmail2(userExt.getEmail2());
							userProfile.setGender(userExt.getGender());
							userProfile.setHomepage(userExt.getHomepage());
							userProfile.setIm(userExt.getIm());
							userProfile.setIm2(userExt.getIm2());
							userProfile.setMobile2(userExt.getMobile2());
							userProfile.setSelfIntroduce(userExt.getSelfintroduce());
							userProfile.setSignature(userExt.getSignature());
							userProfile.setTdcImgUrl(userExt.getTdcImg());
							userProfile.setTelephone(userExt.getTelephone());
						}
						userProfile.setUserId(user.getId().intValue());
						userProfile.setNickname(user.getNickname());
						res.setUserProfile(userProfile);
						res.setResponseCode(ResponseCode.SUCCESS);
						res.setResponseMessage("success");
					}else{
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("fail to get user.");
						return res;
					}
				}*/
			
			
			OsfUsers user = divxUserDao.GetUserByUsername(orgId, username);

			if(user != null){
				UserProfile userProfile = cacheProfile.getIfPresent(user.getId());
				if (userProfile == null)
				{
					userProfile = new UserProfile();
					DcpUserExt userExt = divxUserDao.GetUserExt(user.getId().intValue());
					if( userExt != null){
						userProfile.setRealname(userExt.getRealname());
						userProfile.setBirthday(userExt.getBirthday());
						userProfile.setComefrom(userExt.getComefrom());
						userProfile.setEmail2(userExt.getEmail2());
						userProfile.setGender(userExt.getGender());
						userProfile.setHomepage(userExt.getHomepage());
						userProfile.setIm(userExt.getIm());
						userProfile.setIm2(userExt.getIm2());
						userProfile.setMobile2(userExt.getMobile2());
						userProfile.setSelfIntroduce(userExt.getSelfintroduce());
						userProfile.setSignature(userExt.getSignature());
						userProfile.setTdcImgUrl(userExt.getTdcImg());
						userProfile.setTelephone(userExt.getTelephone());
					}
					userProfile.setUserId(user.getId().intValue());
					userProfile.setNickname(user.getNickname());
					cacheProfile.put(user.getId().intValue(), userProfile);
				}
				res.setUserProfile(userProfile);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			}
			else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("fail to get user.");
				return res;
			}
			
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}
	public ServiceResponse SetUserProfile(int orgId,String username,UserProfile userProfile) {
		ServiceResponse res = new ServiceResponse();
		try
		{	
			OsfUsers user = divxUserDao.GetUserByUsername(orgId, username);
			if(user != null){
				DcpUserExt userExt = new DcpUserExt();
				if(userProfile != null){
					userExt.setUserId(user.getId().intValue());
					userExt.setRealname(userProfile.getRealname());
					userExt.setBirthday(userProfile.getBirthday());
					userExt.setComefrom(userProfile.getComefrom());
					userExt.setEmail2(userProfile.getEmail2());
					userExt.setGender(userProfile.getGender());
					userExt.setHomepage(userProfile.getHomepage());
					userExt.setIm(userProfile.getIm());
					userExt.setIm2(userProfile.getIm2());
					userExt.setMobile2(userProfile.getMobile2());
					userExt.setSelfintroduce(userProfile.getSelfIntroduce());
					userExt.setSignature(userProfile.getSignature());
					userExt.setTdcImg(userProfile.getTdcImgUrl());
					userExt.setTelephone(userProfile.getTelephone());
					if(userProfile.getNickname() != null){
						user.setNickname(userProfile.getNickname());
					}else{
						user.setNickname(user.getUsername());
					}
				}
				else
				{
					userExt.setUserId(user.getId().intValue());
					user.setNickname(user.getUsername());
				}	
				int uid = divxUserDao.UpdateUser(user);
				if(uid <= 0){
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("fail to update user.");
					return res;					
				}
				
				int mid = divxUserDao.SetUserExt(userExt);
				if(mid > 0){
					cacheProfile.invalidate(user.getId());
					ImHelper.UpdateNickname(uid, user.getNickname());
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("success");
				}else{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to SetMyProfile.");
				}
			}else{
				res.setResponseCode(ResponseCode.RESULT_USER_NOT_FOUND);
				res.setResponseMessage("user not exist.");
				return res;
			}
			
		
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			return res;
		}
		return res;
	}

	public UsersResponse ListUsers(int orgId, int startPos, int endPos)
	{
		String key = String.format("lu_%d_%d_%d", orgId, startPos, endPos);
		UsersResponse res = cacheListUsers.getIfPresent(key);

		if (res != null)
			return res;
		try
		{
			res = new UsersResponse();
			
			List<User> users = new LinkedList<User>();
			List<OsfUsers> objs = divxUserDao.ListUsers(orgId, startPos, endPos);
			if (objs != null && objs.size() > 0)
			{
				for(OsfUsers obj: objs)
				{
					users.add(UserUtils.ToUser(obj, new User()));
				}
			}
			
			res.setUsers(users);
			res.setStartPos(startPos);
			res.setCount(users.size());
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			
			if (users.size() > 0)
				cacheListUsers.put(key, res);
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Exception. " + ex.getMessage());
			Util.LogError(log, String.format("ListUsers(%d, %d, %d", orgId, startPos, endPos), ex);
		}
		return res;
	}
	public List<OsfUsers> FindUser(FindUserOption.eFindOption option,String searchKey, int orgId)
	{
		String key = String.format("findUsers_%d_%d_%s", option.ordinal(), orgId, searchKey);
		
		List<OsfUsers> objs = cacheFindUsers.getIfPresent(key); 
		if (objs == null)
		{
			switch (option)
			{
			case username:
				objs = divxUserDao.FindUsersInUsername(orgId, searchKey);
				break;
			case nickname:
				objs = divxUserDao.FindUsersInNickname(orgId, searchKey);
				break;
			case mobile:
				objs = divxUserDao.FindUsersInMobile(orgId, searchKey);
				break;
			case email:
				objs = divxUserDao.FindUsersInEmail(orgId, searchKey);
				break;
			}
			if (objs != null && objs.size() > 0)
			{
				cacheFindUsers.put(key, objs);
			}
		}
		
		return objs;
	}

	public UserInfoResponse GetUserBaseInfo(int userId)
	{
		UserInfoResponse res = new UserInfoResponse();
		
		try
		{
		
			KeyValuePair<OsfUsers, DcpOrganization> ret = divxUserDao.GetUserInfo(userId);
			if(ret != null){
				OsfUsers user = ret.getKey();
				if (user != null)
				{
					res.setUserId(user.getId().intValue());
					res.setEmail(user.getEmail());
					res.setMobile(user.getMobile());
					switch(user.getUsernametype()){
						case 0:
							res.setRegisterType(RegisterType.username);
							break;
						case 1:
							res.setRegisterType(RegisterType.email);
							break;
						case 2:
							res.setRegisterType(RegisterType.mobile);
							break;
						default :
							res.setRegisterType(RegisterType.username);
							break;
					}
					
					res.setNickname(user.getNickname());
					if (user.getPhotourl() != null && !user.getPhotourl().isEmpty())
					{
						String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
						res.setPhotourl(configUrl + user.getPhotourl());
					}
					res.setUsername(user.getUsername());
					
					DcpOrganization dbOrg = ret.getValue();
					if (dbOrg != null)
					{
						Organization org = new Organization();
						org.setId(dbOrg.getId());
						org.setHomeurl(dbOrg.getHomeurl());
						org.setTitle(dbOrg.getTitle());
						
						res.setOrganization(org);
					}
					
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
					}
				}else{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("fail to get user by Id");
				}
			}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	public ServiceResponse UpdateUserPhoto(UserPhotoOption option){
		ServiceResponse res = new ServiceResponse();
		try{
			OsfUsers user = divxUserDao.GetUser(option.getUserId());
			if(null == user){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("can not find the user by userId.");
				return res;
			}
			
			user.setPhotourl(option.getPhotourl());
			int mid = divxUserDao.UpdateUser(user);
			if(mid > 0){
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("fail to get update user photo");
			}
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	public UserResponse GetUser(int userId) {
		UserResponse res = new UserResponse();

		try
		{
			OsfUsers obj = divxUserDao.GetUser(userId);
			
			if (obj != null)
			{
				com.divx.service.model.User u = new com.divx.service.model.User();
				u.setUserId(obj.getId().intValue());
				u.setNickname(obj.getNickname());
				u.setUsername(obj.getUsername());
				if (obj.getPhotourl() != null && !obj.getPhotourl().isEmpty())
				{
					String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
					u.setPhotourl(configUrl + obj.getPhotourl());
				}else{
					u.setPhotourl("");
				}
				res.setUser(u);
			
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.RESULT_USER_NOT_FOUND);
				res.setResponseMessage("Not find.");
			}
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return res;
	}

	private void ClearUserCache()
	{
		cacheListUsers.invalidateAll();
		cacheFindUsers.invalidateAll();
	}
	
}
