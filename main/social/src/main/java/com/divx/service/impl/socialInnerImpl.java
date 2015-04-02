package com.divx.service.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.socialInner;
import com.divx.service.domain.manager.FriendManager;
import com.divx.service.domain.manager.GroupManager;
import com.divx.service.domain.manager.ShareManager;
import com.divx.service.model.QueryUserOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;

public class socialInnerImpl implements socialInner {
	private FriendManager friendManager;
	private GroupManager groupManager;
	private ShareManager shareManager;

	@Autowired
	public void setFriendManager(FriendManager friendManager) {
		this.friendManager = friendManager;
	}
	
	@Autowired
	public void setGroupManager(GroupManager groupManager) {
		this.groupManager = groupManager;
	}
	@Autowired
	public void setSharemanager(ShareManager shareManager) {
		this.shareManager = shareManager;
	}

	@Override
	public Response UserFriends(int userId) {
		// TODO Auto-generated method stub
		return Util.ServiceResponseToResponse(friendManager.GetMyFriends(userId));
	}

	@Override
	public Response UsersInGroup(int groupId) {

		return Util.ServiceResponseToResponse(groupManager.GetUsersOfGroup(-1, groupId, QueryUserOption.QueryType.all));
	}

	@Override
	public Response InnerShareMedia(ShareOption option) {
		AuthHelper helper = new AuthHelper();
		return Util.ServiceResponseToResponse(shareManager.ShareMedia(helper.getUserId(), option));
	}

	

	
}
