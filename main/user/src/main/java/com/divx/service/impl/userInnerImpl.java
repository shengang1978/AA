package com.divx.service.impl;



import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import com.divx.service.Util;
import com.divx.service.userInner;
import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.domain.manager.SiteManager;
import com.divx.service.domain.manager.UserManager;
import com.divx.service.model.CheckUserResponse;
import com.divx.service.model.OrgSite;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.UserPhotoOption;
import com.divx.service.model.UserProfile;


public class userInnerImpl implements userInner {
	//private UserService userService;
	private UserManager userManager;
	private SiteManager siteManager;
/*
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
*/
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
		
		
		return Util.ServiceResponseToResponse(userManager.GetUser(userId));
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
