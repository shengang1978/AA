package com.divx.service.impl;

import javax.jws.WebService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.friend;
import com.divx.service.domain.manager.FriendManager;
import com.divx.service.model.FriendRequest;
import com.divx.service.model.FriendResquests;
import com.divx.service.model.InviteOption;
import com.divx.service.model.RespondOperate;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.UsersResponse;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.friend", portName = "friendImplPort", serviceName = "friendImplService")
public class friendImpl implements friend {

	private FriendManager friendManager;

	@Autowired
	public void setFriendManager(FriendManager friendManager) {
		this.friendManager = friendManager;
	}

	@Override
	public Response RequestFriend(FriendRequest request) {

		AuthHelper helper = new AuthHelper();
		if (helper.isGuest()) {
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}

		return Util.ServiceResponseToResponse(friendManager.RequestFriend(
				helper.getUserId(), request));
	}

	@Override
	public Response MyFriendRequests() {

		AuthHelper helper = new AuthHelper();
		if (helper.isGuest()) {
			FriendResquests res = new FriendResquests();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		return Util.ServiceResponseToResponse(friendManager
				.MyFriendRequests(helper.getUserId()));
	}

	@Override
	public Response RespondRequestFriend(RespondOperate oper) {

		AuthHelper helper = new AuthHelper();
		if (helper.isGuest()) {
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		return Util.ServiceResponseToResponse(friendManager
				.RespondRequestFriend(helper.getUserId(), oper));
	}

	@Override
	public Response InviteUser(InviteOption option) {

		AuthHelper helper = new AuthHelper();
		ServiceResponse res = new ServiceResponse();
		if (helper.isGuest()) {

			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		

		return Util.ServiceResponseToResponse(friendManager.InviteUser(
				helper.getUserId(), option));
	}

	@Override
	public Response MyFriends() {
		AuthHelper helper = new AuthHelper();

		if (helper.isGuest()) {
			UsersResponse res = new UsersResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		return Util.ServiceResponseToResponse(friendManager.GetMyFriends(helper
				.getUserId()));
	}

	@Override
	public Response UnbindFriend(int friendId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest()) {
			UsersResponse res = new UsersResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		ServiceResponse res = new ServiceResponse();
		res.setResponseCode(0);
		res.setResponseMessage("Success");

		return Util.ServiceResponseToResponse(friendManager.UnbindFriend(helper.getUserId(),friendId));
	}

	@Override
	public Response RespondInviteUser(String username, String password) {
		UserServiceHelper ush = new UserServiceHelper();
		ServiceResponse res = new UsersResponse();
		int fromUserId;
		try {
			username = Util.DecryptBase64(username);
			password = Util.DecryptBase64(password);
			fromUserId = Integer.parseInt(username.substring(0,
					username.indexOf("|")));
			username = username.substring(username.indexOf("|") + 1);
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("Invalid username or password.");
			ex.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(res).build();
		}

		UserServiceHelper.AuthResponse ars = ush.RespondInviteUser(
				UserServiceHelper.eRegisterType.email, username, password,
				password);
		if (ars.getResponseCode() != 0) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(String.format(
					"Fail to RespondInviteUser. %s %s", ars.getResponseCode(),
					ars.getResponseMessage()));
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(res).build();
		}

		AuthHelper helper = new AuthHelper(ars.getToken());
		if (helper.isGuest()) {
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage(String.format("Fail to parse token. %s",
					ars.getToken()));
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}

		return Util.ServiceResponseToResponse(friendManager.RespondInviteUser(
				fromUserId, helper.getUserId()));
	}
}
