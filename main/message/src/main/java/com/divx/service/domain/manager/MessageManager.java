package com.divx.service.domain.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.SocialServiceHelper;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.model.DcpMessage;
import com.divx.service.domain.model.DcpRecver;
import com.divx.service.domain.model.DcpSender;
import com.divx.service.domain.repository.DeviceDao;
import com.divx.service.domain.repository.MessageDao;
import com.divx.service.model.*;

@Service
public class MessageManager {
	private Logger log = Logger.getLogger(MessageManager.class);
	
	public enum eSenderType
	{
		User,
		Group,
		System
	}
	
	public enum eMessageStatus
	{
		Normal
	}
	private MessageDao	messageDao;
	private DeviceDao	deviceDao;
	
	@Autowired
	public void setMessageDao(MessageDao messageDao)
	{
		this.messageDao = messageDao;
	}
	
	@Autowired
	public void setDeviceDao(DeviceDao deviceDao)
	{
		this.deviceDao = deviceDao;
	}
	static Runnable monitor;
	static Thread threadMonitor;
	
	static{
		monitor = new WatchMonitor();
		
		threadMonitor = new Thread(monitor);
		threadMonitor.start();
	  }
	
	public ServiceResponse AddMessage(Message msg, int userId)
	{
		ServiceResponse res = new ServiceResponse();
		
		try
		{
			DcpSender sender = messageDao.GetSender(eSenderType.User.ordinal(), userId);
			if (sender == null)
			{
				sender = new DcpSender();
				sender.setDatecreated(new Date());
				sender.setType(eSenderType.User.ordinal());
				sender.setLinkId(userId);
				DcpRecver recver = deviceDao.GetRecver(userId);
				if (recver != null)
				{
					sender.setNickname(recver.getNickname());
					sender.setDescription(recver.getUsername());
				}
				messageDao.AddSender(sender);
			}
			
			DcpMessage dm = new DcpMessage();
			List<Integer> userIds = new LinkedList<Integer>();
			dm.setSenderId(sender.getSenderId());
			dm.setDatecreated(new Date());
			dm.setDeleted(false);
			dm.setIssysmessage(false);
			dm.setMessagetype(msg.getMessageType().ordinal());
			dm.setStatus(eMessageStatus.Normal.ordinal());
			dm.setContent(msg.getContent());
			
			switch (msg.getMessageType()) {
			case ToPerson:				
				dm.setMessageCategory(MessageCategory.USER_MESSAGE_TO_FRIEND);
				DcpRecver target = deviceDao.GetRecver(msg.getTargetId());
				if (target == null)
				{
					UserServiceHelper ush = new UserServiceHelper();
					UserServiceHelper.UserResponse ur = ush.GetUser(msg.getTargetId());
					target = new DcpRecver();
					target.setDatecreated(new Date());
					target.setIsactive(true);
					target.setNickname(ur.getUser().getNickname());
					target.setPhotourl(ur.getUser().getPhotourl());
					target.setUserId(ur.getUser().getUserId());
					target.setUsername(ur.getUser().getUsername());
					
					deviceDao.AddRecver(target);
				}
				userIds.add(msg.getTargetId());
				break;
			case ToGroup:
			{
				dm.setMessageCategory(MessageCategory.USER_MESSAGE_TO_GROUP);
				SocialServiceHelper ssh = new SocialServiceHelper();
				SocialServiceHelper.UsersResponse ur = ssh.GetUsersOfGroup(msg.getTargetId());
				if (ur.getResponseCode() != ResponseCode.SUCCESS)
				{
					res.setResponseCode(ur.getResponseCode());
					res.setResponseMessage(ur.getResponseMessage());
					return res;
				}
				
				for(SocialServiceHelper.User u : ur.getUsers())
				{
					userIds.add(u.getUserId());
				}

				//userIds.add(msg.getTargetId());
				break;
			}
			case ToMyFriends:
			{
				dm.setMessageCategory(MessageCategory.USER_MESSAGE_TO_ALLFRIENDS);
				SocialServiceHelper ssh = new SocialServiceHelper();
				SocialServiceHelper.UsersResponse ur = ssh.UserFriends(msg.getTargetId());
				if (ur.getResponseCode() != ResponseCode.SUCCESS)
				{
					res.setResponseCode(ur.getResponseCode());
					res.setResponseMessage(ur.getResponseMessage());
					return res;
				}
				
				for(SocialServiceHelper.User u : ur.getUsers())
				{
					userIds.add(u.getUserId());
				}
				break;
			}
			default:
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid Message Type.");
				return res;
			}
			
			messageDao.AddMessage(dm, userIds);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			//e.printStackTrace();
			Util.LogError(log, String.format("AddMessage(%d) Exception", userId), e);
		}
		return res;
	}
	
	public ServiceResponse AddMessage(SysMessage msg)
	{
		ServiceResponse res = new ServiceResponse();
		
		try
		{
			DcpSender sender = messageDao.GetSender(eSenderType.System.ordinal(), 0);
			if (sender == null)
			{
				sender = new DcpSender();
				sender.setDatecreated(new Date());
				sender.setType(eSenderType.System.ordinal());
				sender.setLinkId(0);
//				DcpRecver recver = deviceDao.GetRecver(userId);
//				if (recver != null)
				{
					sender.setNickname("System Message");
					sender.setDescription("System Message");
				}
				messageDao.AddSender(sender);
			}
			
			DcpMessage dm = new DcpMessage();
			List<Integer> userIds = new ArrayList<Integer>();
			dm.setSenderId(sender.getSenderId());
			dm.setDatecreated(new Date());
			dm.setDeleted(false);
			dm.setIssysmessage(true);
			dm.setMessagetype(msg.getMessageType().ordinal());
			dm.setStatus(eMessageStatus.Normal.ordinal());
			dm.setContent(msg.getContent());
			dm.setMessageCategory(msg.getMessageCategory());
			
			switch (msg.getMessageType()) {
			case ToPerson:				
				DcpRecver target = deviceDao.GetRecver(msg.getTargetId());
				if (target == null)
				{
					UserServiceHelper ush = new UserServiceHelper();
					UserServiceHelper.UserResponse ur = ush.GetUser(msg.getTargetId());
					target = new DcpRecver();
					target.setDatecreated(new Date());
					target.setIsactive(true);
					target.setNickname(ur.getUser().getNickname());
					target.setPhotourl(ur.getUser().getPhotourl());
					target.setUserId(ur.getUser().getUserId());
					target.setUsername(ur.getUser().getUsername());
					
					deviceDao.AddRecver(target);
				}
				userIds.add(msg.getTargetId());
				break;
			case ToGroup:
				SocialServiceHelper ssh = new SocialServiceHelper();
				SocialServiceHelper.UsersResponse ur = ssh.GetUsersOfGroup(msg.getTargetId());
				if (ur.getResponseCode() != ResponseCode.SUCCESS)
				{
					res.setResponseCode(ur.getResponseCode());
					res.setResponseMessage(ur.getResponseMessage());
					return res;
				}
				
				for(SocialServiceHelper.User u : ur.getUsers())
				{
					userIds.add(u.getUserId());
				}

				break;
			case ToAll:
				// If the messageType is ToAll & IsSysMessage, MyMessages() API will get this message.
				// Nothing need be done.
				break;
			default:
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid Message Type.");
				return res;
			}
			
			messageDao.AddMessage(dm, userIds);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			//e.printStackTrace();
			Util.LogError(log, String.format("AddSysMessage() Exception"), e);
		}
		return res;
	}
	
	public MyMessagesResponse GetMessages(String devGuid)
	{
		if(!threadMonitor.isAlive()) {
			threadMonitor = new Thread(monitor);
			threadMonitor.start();
		}
		MyMessagesResponse res = new MyMessagesResponse();
		
		try
		{
			List<MyMessage> msgs = new ArrayList<MyMessage>();
			
			List<DcpMessage> dms = messageDao.GetUnsendMessages(devGuid);
			
			for(DcpMessage m : dms)
			{
				MyMessage msg = new MyMessage();
				//msg.setFrom("System");
				msg.setContent(m.getContent());
				msg.setDate(m.getDatecreated());
				msg.setMessageId(m.getMessageId());
				msg.setMessageCategory(m.getMessageCategory());
				
				msgs.add(msg);
			}			
			
			res.setMyMessages(msgs);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			//e.printStackTrace();
			Util.LogError(log, String.format("GetMessages(%s) Exception", devGuid), e);
		}
		return res;
	}

	public ServiceResponse SetMessageStatus(int userId, List<Integer> messageIds)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			messageDao.SetMessageSended(messageIds, userId);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			//e.printStackTrace();
			Util.LogError(log, String.format("SetMessageStatus(%d) Exception", userId), e);
		}
		return res;
	}
	
	public boolean HasMessage(String deviceGuid)
	{
		try
		{
			List<DcpMessage> dms = messageDao.GetUnsendMessages(deviceGuid);
			return dms != null && !dms.isEmpty();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			Util.LogError(log, String.format("HasMessage(%s) Exception", deviceGuid), e);
			return false;
		}
	}
}
