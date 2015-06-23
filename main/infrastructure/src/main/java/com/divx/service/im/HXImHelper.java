package com.divx.service.im;

import java.util.List;

import com.divx.service.Util;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.im.HxGroup;
import com.divx.service.model.im.HxResponse;
import com.divx.service.model.im.HxUser;
import com.divx.service.model.im.HxUserResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class HXImHelper {

	public static HxUserResponse<List<HxUser>> RegisterUsers(List<HxUser> users)
	{
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode arrayNode = factory.arrayNode();
		
		for(HxUser user: users)
		{
			ObjectNode datanode = factory.objectNode();
			datanode.put("username", user.getUsername());
	        datanode.put("password", user.getPassword());
	        datanode.put("nickname", user.getNickname());
	        arrayNode.add(datanode);
		}
		
        ObjectNode ret = HXImUserHelper.createNewIMUserBatch(arrayNode);

        return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxUserResponse<List<HxUser>> RegisterUser(HxUser user)
	{
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username", user.getUsername());
        datanode.put("password", user.getPassword());
        datanode.put("nickname", user.getNickname());
        ObjectNode ret = HXImUserHelper.createNewIMUserSingle(datanode);;

        return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}

	public static HxUserResponse<List<HxUser>> UpdateNickname(HxUser user)
	{
		ObjectNode ret = HXImUserHelper.updateIMUserNickname(user.getUsername(), user.getNickname());
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxUserResponse<List<HxUser>> GetUser(String username)
	{
		ObjectNode ret = HXImUserHelper.getIMUsersByUserName(username);

		return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxUserResponse<List<HxUser>> DeleteUser(String username)
	{
		ObjectNode ret = HXImUserHelper.deleteIMUserByuserName(username);

		return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxUserResponse<List<HxUser>> AddFriend(String username, String friendUsername)
	{
		ObjectNode ret = HXImUserHelper.addFriendSingle(username, friendUsername);
		
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxUserResponse<List<HxUser>> DeleteFriend(String username, String friendUsername)
	{
		ObjectNode ret = HXImUserHelper.deleteFriendSingle(username, friendUsername);
		
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxUserResponse<List<HxUser>> GetUsers(int limit, String cursor)
	{
		ObjectNode ret = HXImUserHelper.getUsers(limit, cursor);
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxUserResponse<List<HxUser>>>(){}.getType());
	}
	
	public static HxResponse<HxGroup> CreateGroup(HxGroup info)
	{
		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		dataObjectNode.put("groupname", info.getGroupname());
		dataObjectNode.put("desc", info.getDesc());
		dataObjectNode.put("approval", info.isApproval());
		dataObjectNode.put("public", info.isPublic());
		dataObjectNode.put("maxusers", info.getMaxusers());
		dataObjectNode.put("owner", info.getOwner());
//		ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
//		arrayNode.add("xiaojianguo002");
//		arrayNode.add("xiaojianguo003");
//		dataObjectNode.put("members", arrayNode);
		
		ObjectNode ret = HXImGroupHelper.creatChatGroups(dataObjectNode);

		return ObjectNodeToServiceResponse(ret, new TypeToken<HxResponse<HxGroup>>(){}.getType());
	}
	
	public static HxResponse<HxGroup> DeleteGroup(String hxGroupId)
	{
		ObjectNode ret = HXImGroupHelper.deleteChatGroups(hxGroupId);
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxResponse<HxGroup>>(){}.getType());
	}
	
	public static HxResponse<HxGroup> AddUserToGroup(String groupId, String username)
	{
		ObjectNode ret = HXImGroupHelper.addUserToGroup(groupId, username);
		
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxResponse<HxGroup>>(){}.getType());
	}
	
	public static HxResponse<HxGroup> DeleteUserFromGroup(String groupId, String username)
	{
		ObjectNode ret = HXImGroupHelper.deleteUserFromGroup(groupId, username);
		
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxResponse<HxGroup>>(){}.getType());
	}
	
	public static HxResponse<List<HxGroup>> GetAllGroups()
	{
		ObjectNode ret = HXImGroupHelper.getAllChatgroupids();
		
		return ObjectNodeToServiceResponse(ret, new TypeToken<HxResponse<List<HxGroup>>>(){}.getType());
	}
	
	private static <T extends ServiceResponse> T ObjectNodeToServiceResponse(ObjectNode ret, Type typeOfT)
	{
		T res = null;
		
		if (null != ret) {
			res = Util.JsonToObject(ret.toString(), typeOfT);
        	JsonNode jn = ret.get("statusCode");
        	if (jn == null)
        	{
        		res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
        		return res;
        	}
        	
        	switch(jn.asInt())
        	{
        	case 200:
        		res.setResponseCode(ResponseCode.SUCCESS);
        		res.setResponseMessage("Success");
        		break;
        	case 404:
        		res.setResponseCode(ResponseCode.AUTH_ERROR_USERNAME_OR_PASSWORD_INVALID);
        		res.setResponseMessage(ret.toString());
        		break;
        	case 401:
        		res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
        		res.setResponseMessage(ret.toString());
        	default:
        		res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
        		res.setResponseMessage(ret.toString());
        		break;
        	}
        }
        else
        {
        	try {
				res = (T) typeOfT.getClass().newInstance();
	        	res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
	        	res.setResponseMessage("ObjectNodeToServiceResponse(ObjectNode is null)");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return res;
	}
	
	private static <T extends ServiceResponse> T ObjectNodeToServiceResponse(ObjectNode ret, Class<T> clsType)
	{
		T res = null;
		
		if (null != ret) {
			res = Util.JsonToObject(ret.toString(), clsType);
        	JsonNode jn = ret.get("statusCode");
        	if (jn == null)
        	{
        		res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
        		return res;
        	}
        	
        	switch(jn.asInt())
        	{
        	case 200:
        		res.setResponseCode(ResponseCode.SUCCESS);
        		res.setResponseMessage("Success");
        		break;
        	case 404:
        		res.setResponseCode(ResponseCode.AUTH_ERROR_USERNAME_OR_PASSWORD_INVALID);
        		res.setResponseMessage(ret.toString());
        		break;
        	case 401:
        		res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
        		res.setResponseMessage(ret.toString());
        	default:
        		res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
        		res.setResponseMessage(ret.toString());
        		break;
        	}
        }
        else
        {
        	res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
        	res.setResponseMessage("Fail to call createNewIMUserSingle()");
        }
		return res;
	}
}
