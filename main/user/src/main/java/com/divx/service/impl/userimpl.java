package com.divx.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.divx.service.*;
import com.divx.service.auth.DivXAuthenticationToken;
import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.auth.model.DivXAuthUser;
import com.divx.service.domain.manager.UserManager;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.model.*;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.user", portName = "userimplPort", serviceName = "userimplService")
public class userimpl implements user {

	private UserManager userManager;

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public Response Register(RegisterOption option) {

		ServiceHeaderHelper helper = new ServiceHeaderHelper();

		String deviceUniqueId = helper.getDeviceUniqueId() ;
		String deviceType = helper.getDeviceType();
		if (deviceUniqueId == null || deviceUniqueId.isEmpty() ||
				deviceType == null || deviceType.isEmpty())
		{
			AuthResponse res = new AuthResponse();
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("DeviceUniqueId or DeviceType is invalid.");
			return Util.ServiceResponseToResponse(res);
		}
		
		
		return Util.ServiceResponseToResponse(userManager.Register(option, deviceUniqueId, deviceType, helper.getOrganizationId()));
	}

	@Override
	public Response Login(String username, String password) {
		
		ServiceHeaderHelper helper = new ServiceHeaderHelper();

		String deviceUniqueId = helper.getDeviceUniqueId() ;
		String deviceType = helper.getDeviceType();
		if (deviceUniqueId == null || deviceUniqueId.isEmpty() ||
				deviceType == null || deviceType.isEmpty())
		{
			AuthResponse res = new AuthResponse();
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("DeviceUniqueId or DeviceType is invalid.");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(userManager.Login(username.trim(), password, deviceUniqueId, deviceType, helper.getOrganizationId()));

	}
	

	@Override
	public Response OAuthLogin(OAuthOption option) {
		ServiceHeaderHelper helper = new ServiceHeaderHelper();

		String deviceUniqueId = helper.getDeviceUniqueId() ;
		String deviceType = helper.getDeviceType();
		String token = helper.getHeader("Token");
		if (deviceUniqueId == null || deviceUniqueId.isEmpty() ||
				deviceType == null || deviceType.isEmpty())
		{
			AuthResponse res = new AuthResponse();
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("DeviceUniqueId or DeviceType is invalid.");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(userManager.OAuthLogin(option, deviceUniqueId, deviceType, helper.getOrganizationId(),token));
	}

	@Override
	public Response ListUsers(int startPos, int endPos) {
		ServiceHeaderHelper helper = new ServiceHeaderHelper();
		
		int orgId = helper.getOrganizationId();
		
		return Util.ServiceResponseToResponse(userManager.ListUsers(orgId, startPos, endPos));
	}

	@Override
	public Response FindUsers(FindUserOption.eFindOption option,String searchKey, int startPos, int endPos) {
		ServiceHeaderHelper helper = new ServiceHeaderHelper();
		
		int orgId = helper.getOrganizationId();
		
		UsersResponse res = new UsersResponse();
		
		try
		{
			List<OsfUsers> users = this.userManager.FindUser(option, searchKey, orgId);
	
			if (users != null && users.size() > 0) {
				List<OsfUsers> subUsers = users;
				if (endPos != 0)
				{
					if (endPos >= users.size())
					{
						endPos = users.size() - 1;
					}
					
					if (startPos > endPos)
					{
						startPos = endPos;
					}
					subUsers = users.subList(startPos, endPos);
				}
				List<User> retUsers = new LinkedList<User>();
				
				for (OsfUsers u: subUsers) {
					User obj = new User();
					obj.setUserId(u.getId().intValue());
					obj.setUsername(u.getUsername());
					obj.setNickname(u.getNickname());

					if (u.getPhotourl() != null && !u.getPhotourl().isEmpty())
					{
						String configUrl = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX());
						obj.setPhotourl(configUrl + u.getPhotourl());
					}else{
						obj.setPhotourl("");
					}

					retUsers.add(obj);
				}
				res.setUsers(retUsers);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("User Not Found.");
			}
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			e.printStackTrace();
		}
		
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response Logout() {
		//ServiceHeaderHelper helper = new ServiceHeaderHelper();

		//return Util.ServiceResponseToResponse(userManager.Logout(helper.getHeader("Token")));
		DivXAuthUser user = DivXAuthUser.FromSecurityContent();
		String token = "";
		if (user != null && user.getToken() != null)
		{
			token = user.getToken().getToken();
		}
		return Util.ServiceResponseToResponse(userManager.Logout(token));
	}

	@Override
	public Response UpdateUsername(UserOption option) {
		DivXAuthUser user = DivXAuthUser.FromSecurityContent();
		
		return Util.ServiceResponseToResponse(userManager.UpdateUser(option,user.getUserId()));
	}

	@Override
	public Response ResetPassword(UserOption.OptionType option,String value) {
		ServiceHeaderHelper helper = new ServiceHeaderHelper();
		
		return Util.ServiceResponseToResponse(userManager.ResetPassword(option,value, helper.getOrganizationId()));
	}

	@Override
	public Response ChangePassword(ChangePasswordOption option) {
		DivXAuthUser user = DivXAuthUser.FromSecurityContent();
//		ServiceResponse res = new ServiceResponse();
//		if(option.getOldPassword().trim().isEmpty() || option.getNewPassword().trim().isEmpty() || option.getRepeatNewPassword().trim().isEmpty() || !option.getNewPassword().equals(option.getRepeatNewPassword())){
//			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
//			res.setResponseMessage("Invalid Parameter");
//			return Util.ServiceResponseToResponse(res);
//		}
//		ServiceHeaderHelper helper = new ServiceHeaderHelper();
//		String strToken = helper.getHeader("Token");
//		if (strToken == null || strToken.trim() == "")
//		{
//			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
//			res.setResponseMessage("Invalid Token");
//			return Util.ServiceResponseToResponse(res);
//		}
//		CheckUserResponse cur = userManager.CheckToken(strToken);
//	
//		if(cur.getResponseCode() == 0){
			return Util.ServiceResponseToResponse(userManager.ChangePassword(option, user.getUserId()));
//		}else{
//			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
//			res.setResponseMessage("Invalid Token");
//			return Util.ServiceResponseToResponse(res);
//		}
	}
	
	@Override
	public Response Startup() {
		ServiceHeaderHelper helper = new ServiceHeaderHelper();
		
		return Util.ServiceResponseToResponse(userManager.Startup(helper.getDeviceUniqueId()));
	}

	@Override
	public Response GetMyProfile() {
		DivXAuthUser user = DivXAuthUser.FromSecurityContent();
//		ServiceHeaderHelper helper = new ServiceHeaderHelper();
//		UserProfileResponse res = new UserProfileResponse();
//		String strToken = helper.getHeader("Token");
//		if (strToken == null || strToken.trim() == "")
//		{
//			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
//			res.setResponseMessage("Invalid Token");
//			return Util.ServiceResponseToResponse(res);
//		}
//		CheckUserResponse cur = userManager.CheckToken(strToken);
	
//		if(cur.getResponseCode() == 0){
			return Util.ServiceResponseToResponse(userManager.GetMyProfile(user.getUserId()));
//		}else{
//			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
//			res.setResponseMessage("Invalid Token");
//			return Util.ServiceResponseToResponse(res);
//		}
	}

	@Override
	public Response SetMyProfile(UserProfile profile) {
//		ServiceHeaderHelper helper = new ServiceHeaderHelper();
//		ServiceResponse res = new ServiceResponse();
//		String strToken = helper.getHeader("Token");
//		if (strToken == null || strToken.trim() == "")
//		{
//			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
//			res.setResponseMessage("Invalid Token");
//			return Util.ServiceResponseToResponse(res);
//		}
		DivXAuthUser user = DivXAuthUser.FromSecurityContent();
//		CheckUserResponse cur = userManager.CheckToken(user);
//	
//		if(cur.getResponseCode() == 0){
			return Util.ServiceResponseToResponse(userManager.SetMyProfile(profile, user.getUserId()));
//		}else{
//			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
//			res.setResponseMessage("Invalid Token");
//			return Util.ServiceResponseToResponse(res);
//		}
	}

	@Override
	public Response GetUserInfo() {
		DivXAuthUser user = DivXAuthUser.FromSecurityContent();

		return Util.ServiceResponseToResponse(userManager.GetUserBaseInfo(user.getUserId()));
	}

	@Override
	public Response ValidateToken() {
		ValidateTokenResponse res = new ValidateTokenResponse();
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		
		ServiceHeaderHelper helper = new ServiceHeaderHelper();
		String strToken = helper.getHeader("Token");
		if (strToken == null || strToken.trim() == "")
		{
			res.setTokenValid(false);
			return Util.ServiceResponseToResponse(res);
		}
		
		DivXAuthToken at = DivXAuthToken.FromAuthToken(strToken);
		if (at == null )
		{
			res.setTokenValid(false);
			return Util.ServiceResponseToResponse(res);
		}
		
		CheckUserResponse cuRes = userManager.CheckToken(at);
		if (cuRes.getResponseCode() != ResponseCode.SUCCESS)
			res.setTokenValid(false);
		else
			res.setTokenValid(true);
		
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response GetUser(int userId) {
		
		
		return Util.ServiceResponseToResponse(userManager.GetUser(userId));
	}

	
	
}
