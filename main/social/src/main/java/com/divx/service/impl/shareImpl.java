package com.divx.service.impl;

import javax.jws.WebService;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.share;
import com.divx.service.domain.manager.ShareManager;
import com.divx.service.domain.model.OsfComments;
import com.divx.service.model.ActivitiesResponse;
import com.divx.service.model.Activity;
import com.divx.service.model.LikeOption;
import com.divx.service.model.QueryOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;
import com.divx.service.model.SharesResponse;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.share", portName = "shareImplPort", serviceName = "shareImplService")
public class shareImpl implements share {

	private ShareManager shareManager;

	@Autowired
	public void setShareManager(ShareManager shareManager) {
		this.shareManager = shareManager;
	}
	
	@Override
	public Response History(int shareId,int startPos,int endPos) {
		
		ActivitiesResponse res = new ActivitiesResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest()) {
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(shareManager.GetShareHistory(helper.getUserId(), shareId, -1,startPos,endPos));		
	}

	@Override
	public Response Comment(int shareId, String comment) {
		ServiceResponse res = new ServiceResponse();
		
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(shareManager.AddShareActivity(helper.getUserId(), shareId, Activity.ActionType.comment, comment));

	}

	@Override
	public Response Like(int shareId, String like) {
		ServiceResponse res = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(shareManager.AddShareActivity(helper.getUserId(), shareId, Activity.ActionType.like, like.toLowerCase()));
	}

	@Override
	public Response MyShares(QueryOption.QueryType option) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			SharesResponse res = new SharesResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(shareManager.GetShares(helper.getUserId(), option));
	}

	@Override
	public Response SharesInGroup(int groupId, int startPos, int endPos) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			SharesResponse res = new SharesResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}

		return Util.ServiceResponseToResponse(shareManager.GetSharesInGroup(
				helper.getUserId(), groupId, startPos, endPos));
	}

	@Override
	public Response Remove(int shareId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(shareManager.RemoveShare(helper.getUserId(), shareId));
	}

	@Override
	public Response ShareMedia(ShareOption option) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}	
		

		return Util.ServiceResponseToResponse(shareManager.ShareMedia(helper.getUserId(), option));
	}

	@Override
	public Response GetPublicShares(int startPos, int endPos) {
		
		return Util.ServiceResponseToResponse(shareManager.GetPublicShares( startPos, endPos));
	}
}
