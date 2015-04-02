package com.divx.service.impl;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.osforce.connect.entity.system.User;
import org.osforce.connect.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.userInner;
import com.divx.service.auth.DivxAuthUserService;
import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.domain.manager.SiteManager;
import com.divx.service.domain.manager.UserManager;
import com.divx.service.model.CheckUserResponse;
import com.divx.service.model.OrgSite;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.UserPhotoOption;
import com.divx.service.model.UserProfile;
import com.divx.service.model.UserProfileResponse;
import com.divx.service.model.UserResponse;


public class userInnerImpl implements userInner {
	private UserService userService;
	private UserManager userManager;
	private SiteManager siteManager;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	@Autowired
	public void setSiteManager(SiteManager siteManager) {
		this.siteManager = siteManager;
	}
	
	@Override
	public Response GetUserProfile(int organizationId, String username) {
		
		return Util.ServiceResponseToResponse(userManager.GetUserProfile(organizationId, username));
	}

	@Override
	public Response SetUserProfile(int organizationId, String username,
			UserProfile profile) {
		
		return Util.ServiceResponseToResponse(userManager.SetUserProfile(organizationId, username,profile));
	}

	@Override
	public Response CheckUser(String token) {
		CheckUserResponse res = new CheckUserResponse();
		
		DivXAuthToken at = DivXAuthToken.FromAuthToken(token);
		if (at == null )
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(userManager.CheckToken(at));
	}

	@Override
	public Response GetUser(int userId) {
		UserResponse res = new UserResponse();

		try
		{
			User obj = userService.getUser(new Long(userId));
			
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
		
		return Util.ServiceResponseToResponse(res);
	}
	@Override
	public Response UpdateUserPhoto(UserPhotoOption option) {
		
		return Util.ServiceResponseToResponse(userManager.UpdateUserPhoto(option));
	}

	@Override
	public Response GetOrgSites(String searchKey, int startPos, int endPos) {
		return Util.ServiceResponseToResponse(siteManager.findOrgSite(searchKey, startPos, endPos));
	}

	@Override
	public Response SetOrgSite(OrgSite site) {
		return Util.ServiceResponseToResponse(siteManager.setOrgSite(site));
	}
}
