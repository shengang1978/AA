package com.divx.service.domain.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.security.Credential.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.Email;
import com.divx.service.ConfigurationManager;
import com.divx.service.MessageServiceHelper;
import com.divx.service.Util;
import com.divx.service.auth.DivxAuthUserService;
import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.auth.model.DivXAuthUser;
import com.divx.service.domain.manager.SendEmailHelper.WatchMonitor;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.DcpUserExt;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.domain.repository.TokenDao;
import com.divx.service.model.AuthResponse;
import com.divx.service.model.ChangePasswordOption;
import com.divx.service.model.CheckUserResponse;
import com.divx.service.model.EmailTemplate;
import com.divx.service.model.FindUserOption;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueStringPair;
import com.divx.service.model.MailSetting;
import com.divx.service.model.Organization;
import com.divx.service.model.RegisterOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.User;
import com.divx.service.model.UserInfoResponse;
import com.divx.service.model.UserInfoResponse.RegisterType;
import com.divx.service.model.UserOption;
import com.divx.service.model.UserOption.OptionType;
import com.divx.service.model.StartupResponse;
import com.divx.service.model.UserPhotoOption;
import com.divx.service.model.UserProfile;
import com.divx.service.model.UserProfileResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;


@Service
public class UserManager {
	public static final Cache<Integer, UserProfile> cacheProfile = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(6, TimeUnit.HOURS).build();
	public static final Cache<String, List<OsfUsers>> cacheFindUsers = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(6, TimeUnit.HOURS).build();
	
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
	static com.divx.service.domain.manager.SendEmailHelper.WatchMonitor monitor;
	static Thread threadMonitor;
	
	static{
		SendEmailHelper helper = new SendEmailHelper();
		monitor = helper.new WatchMonitor();
		
		threadMonitor = new Thread(monitor);
		threadMonitor.start();
	  }
	public AuthResponse Login(String username, String password, String deviceUniqueId, String deviceType, int orgId)
	{
		AuthResponse res = new AuthResponse();
		try
		{
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
			
			if(user!=null && StringUtils.equals(password, user.getPassword())) {

				UUID uid = UUID.randomUUID();
				user.setToken(uid.toString());
				user.setLastLogin(new Date());
				divxUserDao.UpdateUser(user);
				res.setResponseMessage("Success");

				DcpToken token = tokenDao.GetToken(deviceUniqueId, user.getId().intValue());
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
						res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
						res.setResponseMessage("Invalid DeviceType " + deviceType);
						return res;
					}
					token.setDeviceguid(Util.EncryptDeviceUniqueId(deviceUniqueId, user.getId()));
					token.setIsactive(true);
					token.setUserId(user.getId().intValue());
					token.setToken(uid.toString());
					token.setExpiredate(Util.GetDate(new Date(), Calendar.MONTH, 6));
					tokenDao.CreateToken(token);
				}
				else
				{
					token.setDatemodified(new Date());
					token.setIsactive(true);
					token.setUserId(user.getId().intValue());
					token.setToken(uid.toString());
					token.setExpiredate(Util.GetDate(new Date(), Calendar.MONTH, 6));
					tokenDao.UpdateToken(token);
				}
				
				MessageServiceHelper helper = new MessageServiceHelper();
				ServiceResponse sr = helper.RegisterDevice(token.getDeviceguid(), 
						token.getDevicetype(), 
						user.getNickname().isEmpty() ? username : user.getNickname(), 
						user.getNickname(), 
						user.getPhotourl());
				if (sr.getResponseCode() != 0)
				{
					res.setResponseMessage(String.format("Fail to register device on message service. ResponseCode(%d), %s", sr.getResponseCode(), sr.getResponseMessage()));
				}
				res.setDeviceGuid(token.getDeviceguid());
				res.setToken(new DivXAuthToken(user, token).GetAuthTokenString(new DivXAuthUser(user)));		
				
				res.setResponseCode(ResponseCode.SUCCESS);				
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			//ex.printStackTrace();
			log.error(String.format("Login(%s)", username), ex);
		}
			
		return  res;
	}
	
	public AuthResponse Register(RegisterOption option, String deviceUniqueId, String deviceType, int orgId)
	{
		AuthResponse res = new AuthResponse();

		if (option.getPassword() == null || option.getPassword().isEmpty() 
			|| option.getRepassword() == null || option.getPassword().compareTo(option.getRepassword()) != 0) {
			res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_INVALID_PASSWORD);
			res.setResponseMessage("Invalid password");
			return res;
		}

		OsfUsers user = new OsfUsers();
		user.setUsername(option.getUsername());
		user.setPassword(option.getPassword());
		//user.setRePassword(option.getRepassword());
		user.setNickname(option.getUsername());
		user.setEnabled(true);
		user.setEntered(new Date());
		user.setTimezone("");
		user.setLocale("");
		user.setProjectId(new Long(1));
		user.setOrganizationId(orgId);
		try {
			switch (option.getRegisterType()) {
			case username:
				user.setEmail("");
				user.setMobile("");
				break;
			case email:
				user.setEmail(option.getUsername());
				user.setMobile("");
				break;
			case mobile:
				user.setEmail("");
				user.setMobile(option.getUsername());
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
			
			DcpToken token = tokenDao.GetToken(deviceUniqueId, user.getId().intValue());
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
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid DeviceType " + deviceType);
					return res;
				}
				token.setDeviceguid(Util.EncryptDeviceUniqueId(deviceUniqueId, user.getId()));
				token.setIsactive(true);
				token.setUserId(user.getId().intValue());
				token.setToken(uid.toString());
				token.setExpiredate(Util.GetDate(new Date(), Calendar.MONTH, 6));
				tokenDao.CreateToken(token);
			}
			else
			{
				token.setDatemodified(new Date());
				token.setIsactive(true);
				token.setUserId(user.getId().intValue());
				token.setToken(uid.toString());
				token.setExpiredate(Util.GetDate(new Date(), Calendar.MONTH, 6));
				tokenDao.UpdateToken(token);
			}
			MessageServiceHelper helper = new MessageServiceHelper();
			ServiceResponse sr = helper.RegisterDevice(token.getDeviceguid(), 
					token.getDevicetype(), 
					option.getUsername(), 
					user.getNickname().isEmpty() ? option.getUsername() : user.getNickname(), 
					user.getPhotourl());
			if (sr.getResponseCode() != 0)
			{
				res.setResponseMessage(String.format("Fail to register device on message service. ResponseCode(%d), %s", sr.getResponseCode(), sr.getResponseMessage()));
			}
			res.setDeviceGuid(token.getDeviceguid());

			res.setToken(new DivXAuthToken(user, token).GetAuthTokenString(new DivXAuthUser(user)));
			cacheFindUsers.invalidateAll();
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
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
	
	public StartupResponse Startup()
	{
		StartupResponse res = new StartupResponse();
		try
		{
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
			res.setBaseUrls(baseUrls);
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
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
	
	public ServiceResponse UpdateUser(UserOption option,int userId)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{	
			OsfUsers user = divxUserDao.GetUser(userId);
			if(user != null){
				switch(option.getType())
				{
					case username:
						{
							if(option.getValue().isEmpty()){
								res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
								res.setResponseMessage("Username cannot be null.");
								return res;
							}
							
							if(user.getUsernametype() != OptionType.username.ordinal()){
								if (!option.getValue().equals(user.getUsername()) && 
									divxUserDao.GetUserByUsername(user.getOrganizationId(), option.getValue()) != null){
									res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_USERNAME_EXIST);
									res.setResponseMessage("Username has been Exist.");
									return res;
								}else{
									user.setUsername(option.getValue());
									user.setUsernametype(OptionType.username.ordinal());
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
							
							if(user.getUsernametype() == OptionType.username.ordinal()){
								if(!option.getValue().isEmpty()){
									if (divxUserDao.GetUserByEmail(user.getOrganizationId(), option.getValue()) != null){
										res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_EMAIL_EXIST);
										res.setResponseMessage("Email exists");
										return res;
									}
								}
								user.setEmail(option.getValue());
								
							}else{
								res.setResponseCode(ResponseCode.ERROR_UPDATE_USER_USERNAME_NOT_SET);
								res.setResponseMessage("Username isn't set.");
								return res;
							}
							break;
						}
					case mobile:
						{
							if(user.getUsernametype() == OptionType.username.ordinal()){
								if(!option.getValue().isEmpty()){
									if (divxUserDao.GetUserByMobile(user.getOrganizationId(), user.getUsername()) != null){
									res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_MOBILE_EXIST);
									res.setResponseMessage("mobile exists");
									return res;
									}
								}
								user.setMobile(option.getValue());
								
							}else{
								res.setResponseCode(ResponseCode.ERROR_UPDATE_USER_USERNAME_NOT_SET);
								res.setResponseMessage("Username isn't set.");
								return res;
							}
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
			int mid = divxUserDao.UpdateUser(user);
			if(mid > 0){
				cacheProfile.invalidate(userId);
				cacheFindUsers.invalidateAll();
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
			
			if(!threadMonitor.isAlive()) {
				threadMonitor = new Thread(monitor);
				threadMonitor.start();
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
						log.info("success");
						log.error("error");
					}
					userProfile.setUserId(userId);
					userProfile.setNickname(user.getNickname());
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
}
