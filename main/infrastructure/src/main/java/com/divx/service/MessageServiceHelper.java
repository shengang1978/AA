package com.divx.service;

import java.util.Date;

import com.divx.service.model.ResponseCode;

public class MessageServiceHelper{
	class ServiceResponse
	{
		private int code;
		
		public int getResponseCode()
		{
			return code;
		}
		
		public void setResponseCode(int nCode)
		{
			code = nCode;
		}
		
		private String message;
		
		public String getResponseMessage()
		{
			return message;
		}
		
		public void setResponseMessage(String msg)
		{
			message = msg;
		}
	}	
	
	public enum eMessageType
	{
		ToPerson,		//Message to person/friend
		ToGroup,		//Message to the group
		ToMyFriends,	//Message to all my friends
	}
	
	class Message
	{	
		private eMessageType messageType;
		private int	targetId;
		private String	content;
		public eMessageType getMessageType() {
			return messageType;
		}
		public void setMessageType(eMessageType messageType) {
			this.messageType = messageType;
		}
		public int getTargetId() {
			return targetId;
		}
		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
	
	public enum eSysMessageType
	{
		ToAll,
		ToPerson,
		ToGroup
	}
	
	class SysMessage {
		private eSysMessageType messageType;
		private String content;
		private int targetId;
		private int messageCategory;
		public eSysMessageType getMessageType() {
			return messageType;
		}
		public void setMessageType(eSysMessageType messageType) {
			this.messageType = messageType;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public int getTargetId() {
			return targetId;
		}
		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}
		public int getMessageCategory() {
			return messageCategory;
		}
		public void setMessageCategory(int messageCategory) {
			this.messageCategory = messageCategory;
		}
	}
	
	class RegisterDeviceOption {
		private String deviceGuid;
		private String username;
		private String nickname;
		private String photourl;
		private int deviceType;
		public String getDeviceGuid() {
			return deviceGuid;
		}
		public void setDeviceGuid(String deviceGuid) {
			this.deviceGuid = deviceGuid;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getPhotourl() {
			return photourl;
		}
		public void setPhotourl(String photourl) {
			this.photourl = photourl;
		}
		public int getDeviceType() {
			return deviceType;
		}
		public void setDeviceType(int deviceType) {
			this.deviceType = deviceType;
		}
	}
	
	RegisterDeviceOption	RegisterDeviceOption;
	SysMessage	SysMessage;
	Message	Message;
	
	class AnsycCall implements Runnable
	{
		public AnsycCall(String method, String url, MessageServiceHelper msh)
		{
			this.method = method;
			this.reqUrl = url;
			this.msh = msh;
		}
		private String method;
		private String reqUrl;
		private MessageServiceHelper msh;

		@Override
		public void run() {
			try
			{
				String ret = "";
				switch(method)
				{
				case "GET":
					ret = Util.HttpGet(reqUrl);
					break;
				case "POST":
					ret = Util.HttpPostJson(reqUrl, msh);
					break;
				case "PUT":
					break;
				case "DELETE":
					break;
				}
				
				if (ret == null || ret.isEmpty())
				{
					//Log Error
				}
				else
				{
					MessageServiceHelper.ServiceResponse sr = Util.JsonToObject(ret, MessageServiceHelper.ServiceResponse.class);
					if (sr.getResponseCode() != ResponseCode.SUCCESS)
					{
						//Log error
					}
				}
			}
			catch(Exception e)
			{
				//Log error
			}			
		}		
	}
	//Login
	public com.divx.service.model.ServiceResponse RegisterDevice(String deviceGuid, int deviceType, String username, String nickname, String photoUrl)
	{
		com.divx.service.model.ServiceResponse res = new com.divx.service.model.ServiceResponse();
		
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MessageServiceBaseUrl();
			String reqUrl = baseUrl + "/message/RegisterDevice";
			
			MessageServiceHelper msh = new MessageServiceHelper();
			RegisterDeviceOption rdo = new RegisterDeviceOption();
			rdo.setDeviceGuid(deviceGuid);
			rdo.setDeviceType(deviceType);
			rdo.setUsername(username);
			rdo.setNickname(nickname);
			rdo.setPhotourl(photoUrl);
			msh.RegisterDeviceOption = rdo;
			
			new Thread(new AnsycCall("POST", reqUrl, msh)).start();
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
//			String ret = Util.HttpPostJson(reqUrl, msh);
//			if (ret.isEmpty())
//			{
//				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
//				res.setResponseMessage("Fail to call RegisterDevice()");
//				return res;
//			}
//			
//			MessageServiceHelper.ServiceResponse sr = Util.JsonToObject(ret, MessageServiceHelper.ServiceResponse.class);
//			res.setResponseCode(sr.getResponseCode());
//			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		return res;
	}
	//Logout
	public com.divx.service.model.ServiceResponse UnregisterDevice(String deviceGuid)
	{
		com.divx.service.model.ServiceResponse res = new com.divx.service.model.ServiceResponse();
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MessageServiceBaseUrl();
			String reqUrl = baseUrl + "/message/UnregisterDevice/" + deviceGuid;
			
			new Thread(new AnsycCall("GET", reqUrl, null)).start();
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			
//			String ret = Util.HttpGet(reqUrl);
//			if (ret.isEmpty())
//			{
//				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
//				res.setResponseMessage("Fail to call UnregisterDevice()");
//				return res;
//			}
//			MessageServiceHelper.ServiceResponse sr = Util.JsonToObject(ret, MessageServiceHelper.ServiceResponse.class);
//			res.setResponseCode(sr.getResponseCode());
//			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	
	public com.divx.service.model.ServiceResponse SendSysMessage(eSysMessageType msgType, int messageCategory, int targetId, String content)
	{
		com.divx.service.model.ServiceResponse res = new com.divx.service.model.ServiceResponse();
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MessageServiceBaseUrl();
			String reqUrl = baseUrl + "/message/SysMessage";
			MessageServiceHelper msh = new MessageServiceHelper();
			
			SysMessage msg = new SysMessage();
			msg.setContent(content);
			msg.setMessageType(msgType);
			msg.setTargetId(targetId);
			msg.setMessageCategory(messageCategory);
			msh.SysMessage = msg;
			
			new Thread(new AnsycCall("POST", reqUrl, msh)).start();
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			
//			String ret = Util.HttpPostJson(reqUrl, msh);
//			if (ret.isEmpty())
//			{
//				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
//				res.setResponseMessage("Fail to call SysMessage()");
//				return res;
//			}
//			MessageServiceHelper.ServiceResponse sr = Util.JsonToObject(ret, MessageServiceHelper.ServiceResponse.class);
//			res.setResponseCode(sr.getResponseCode());
//			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}
	
	public com.divx.service.model.ServiceResponse SendUserMessage(eMessageType msgType, int targetId, String content)
	{
		com.divx.service.model.ServiceResponse res = new com.divx.service.model.ServiceResponse();
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MessageServiceBaseUrl();
			String reqUrl = baseUrl + "/message/UserMessage";
			MessageServiceHelper msh = new MessageServiceHelper();
			
			Message msg = new Message();
			msg.setContent(content);
			msg.setMessageType(msgType);
			msg.setTargetId(targetId);
			
			msh.Message = msg;
			
			new Thread(new AnsycCall("POST", reqUrl, msh)).start();
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			
//			String ret = Util.HttpPostJson(reqUrl, msh);
//			if (ret.isEmpty())
//			{
//				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
//				res.setResponseMessage("Fail to call UserMessage()");
//				return res;
//			}
//			MessageServiceHelper.ServiceResponse sr = Util.JsonToObject(ret, MessageServiceHelper.ServiceResponse.class);
//			res.setResponseCode(sr.getResponseCode());
//			res.setResponseMessage(sr.getResponseMessage());
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

}
