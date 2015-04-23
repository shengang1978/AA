package com.divx.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.group;
import com.divx.service.domain.manager.GroupManager;
import com.divx.service.model.*;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.group", portName = "groupImplPort", serviceName = "groupImplService")
public class groupImpl implements group {

	private GroupManager groupManager;

	@Autowired
	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	
	
	@Override
	public Response CreateGroup(GroupOption option) {
		
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			GroupResponse res = new GroupResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		GroupResponse res = groupManager.CreateGroup(helper.getUserId(), option);
		if(res.getGroupId()>0){
			groupManager.AddUserToGroup(helper.getUserId(), res.getGroupId(), helper.getUserId());
		}
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response MyGroups(QueryOption.QueryType option, int startPos, int endPos) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			MyGroupsResponse res = new MyGroupsResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(groupManager.MyGroups(helper.getUserId(), option, startPos, endPos));
	}

	@Override
	public Response AddUserToGroup(int groupId, int userId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(groupManager.AddUserToGroup(helper.getUserId(), groupId, userId));
	}

	@Override
	public Response RemoveUser(int groupId, int userId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(groupManager.RemoveUserFromGroup(helper.getUserId(), groupId, userId));
	}

	@Override
	public Response GetUsers(int groupId, QueryUserOption.QueryType option) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest() && !helper.isService())
		{
			UsersResponse res = new UsersResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(groupManager.GetUsersOfGroup(helper.getUserId(), groupId, option));
	}

	@Override
	public Response SetRoleToUser(int groupId, int userId, GroupRole role) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(groupManager.SetUserRole(helper.getUserId(), groupId, userId, role));
	}

	@Override
	public Response GetGroupRoles() {
		
		GroupRolesResponse res = new GroupRolesResponse();
		List<String> roles = new ArrayList<String>();
		
		roles.add(GroupRole.groupAdmin.toString());
		roles.add(GroupRole.groupMember.toString());
		roles.add(GroupRole.groupGuest.toString());
		
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		res.setRoles(roles);
		
		return Util.ServiceResponseToResponse(res);
	}


	@Override
	public Response DeleteGroup(int groupId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			GroupResponse res = new GroupResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(groupManager.DeleteGroup(helper.getUserId(),groupId));
	}
	
	@Override
	public Response UpdateGroup(Group group) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			GroupResponse res = new GroupResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(groupManager.UpdateGroup(helper.getUserId(),group));
	}


	

	@Override
	public Response AddUsersToGroup(UsersOption usersOption) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(groupManager.AddUsersToGroup(helper.getUserId(),usersOption));
	}


	@Override
	public Response GetMyRoleInGroup(int groupId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			MyRoleInGroupResponse res = new MyRoleInGroupResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(groupManager.GetMyRoleInGroup(helper.getUserId(), groupId));
	}


	@Override
	public Response SetGroupPhotoUrl(Group group) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			MyRoleInGroupResponse res = new MyRoleInGroupResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(groupManager.SetGroupPhotoUrl(helper.getUserId(),group));
	}

}
