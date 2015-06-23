package com.divx.service.domain.manager;

import java.util.List;

import com.divx.service.im.HXImHelper;
import com.divx.service.model.im.HxGroup;
import com.divx.service.model.im.HxResponse;
import com.divx.service.model.im.HxUser;
import com.divx.service.model.im.HxUserResponse;

public class ImHelper {
	private static boolean enableIm = false;
	
	public static void CreateGroup(int userId, String groupName, String desc)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		HxResponse<HxGroup> res = HXImHelper.CreateGroup(new HxGroup(username, groupName, desc, 200));
	}
	
	public static void DeleteGroup(int groupId)
	{
		if (!enableIm) return;
		String hxGroupId = "";
		HxResponse<HxGroup> res = HXImHelper.DeleteGroup(hxGroupId);
	}
	
	public static void AddUserToGroup(int groupId, int userId)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		String hxGroupId = "";
		HxResponse<HxGroup> res = HXImHelper.AddUserToGroup(hxGroupId, username);
	}
	
	public static void RemoveUserFromGroup(int groupId, int userId)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		String hxGroupId = "";
		HxResponse<HxGroup> res = HXImHelper.DeleteUserFromGroup(hxGroupId, username);
	}
	
	public static void AddFriend(int userId, int friendUserId)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		String friendUsername = String.format("tyty_%d", friendUserId);
		HxUserResponse<List<HxUser>> res = HXImHelper.AddFriend(username, friendUsername);
	}
	
	public static void RemoveFriend(int userId, int friendUserId)
	{
		if (!enableIm) return;
		String username = String.format("tyty_%d", userId);
		String friendUsername = String.format("tyty_%d", friendUserId);
		HxUserResponse<List<HxUser>> res = HXImHelper.DeleteFriend(username, friendUsername);
	}
}
