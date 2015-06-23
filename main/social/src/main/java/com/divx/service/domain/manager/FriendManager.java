package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MessageServiceHelper;
import com.divx.service.UserHelper;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.dao.GroupDao;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpFriendRequest;
import com.divx.service.domain.model.OsfProjects;
import com.divx.service.domain.model.OsfTeamMembers;
import com.divx.service.model.FriendRequest;
import com.divx.service.model.FriendRequestOption;
import com.divx.service.model.FriendResquests;
import com.divx.service.model.GroupRole;
import com.divx.service.model.InviteOption;
import com.divx.service.model.MessageCategory;
import com.divx.service.model.RequestStatus;
import com.divx.service.model.RespondOperate;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.SysMessage;
import com.divx.service.model.User;
import com.divx.service.model.UsersResponse;

@Service
public class FriendManager {
	public static Logger log = Logger.getLogger(FriendManager.class);
		
	private FriendDao friendDao;
	private GroupDao groupDao;

	@Autowired
	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	@Autowired
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	static com.divx.service.domain.manager.SendEmailHelper.WatchMonitor monitor;
	static Thread threadMonitor;
	//public  static boolean sendEmailEnable = ConfigurationManager.GetInstance().GetConfigValue("SendEmail_Enable", false);
	static {
		if(false){
			SendEmailHelper helper = new SendEmailHelper();
			monitor = helper.new WatchMonitor();

			threadMonitor = new Thread(monitor);
			threadMonitor.start();
		}
		
	}

	public UsersResponse GetMyFriends(int userId) {
		UsersResponse res = new UsersResponse();
		try {
			List<User> friends = CacheManager.GetInstance().GetCacheMyFriends(userId);
			
			if (friends == null)
			{
				friends = new LinkedList<User>();
			
				OsfProjects group = GetMyFriendGroup(userId);
				if (group == null) {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to create MyFriends group.");
					return res;
				}
	
				List<OsfTeamMembers> tms = friendDao.GetUsers(group.getId()
						.intValue(), GroupRole.friend.ordinal());
				if (tms != null && tms.size() > 0) {
					for (OsfTeamMembers tm : tms) {
						User u = UserHelper.GetUser((int) tm.getUserId());
						friends.add(u);
					}
				}
				
				if (friends.size() > 0)
				{
					CacheManager.GetInstance().SetCacheMyFriend(userId, friends);
				}
			}
			res.setUsers(friends);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}
	public ServiceResponse RequestFriend(int userId, FriendRequest friend) {
		ServiceResponse res = new ServiceResponse();
		try {
			DcpFriendRequest friendRequest = new DcpFriendRequest();
			friendRequest.setStatus(RequestStatus.untreated.ordinal());
			friendRequest.setDatecreated(new Date());
			friendRequest.setDateresponse(new Date());
			friendRequest.setRequestUserid(userId);
			friendRequest.setReceiveUserid(friend.getUserId());
			if ( friend.getUserId()==userId) {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("不能添加自己为好友.");
				return res;
			}
			User friendUser = UserHelper.GetUser(friend.getUserId());
			
			if (friendUser == null || friendUser.getUserId() <= 0) {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format(
						"Fail to get user(%d) info by UserService.", userId));
				return res;
			}
			User userSelf = UserHelper.GetUser(userId);
			if (userSelf == null || userSelf.getUserId() <= 0) {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format(
						"Fail to get user(%d) info by UserService.", userId));
				return res;
			}
			// 验证是否已经是好友
			OsfTeamMembers osfTeamMembers= friendDao.GetMyFriend(userId, friend.getUserId());
			if (osfTeamMembers!=null) {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);// 好友重复添加
				res.setResponseMessage(String.format("对方已经是您的好友.",
						userId));
				return res;
			}

			String content = String.format("%s想邀请您为好友。 ",
					userSelf.getNickname());
			if (friend.getContent() != null && !friend.getContent().isEmpty())
				content += friend.getContent();

			friendRequest.setContent(content);

			int requestId = friendDao.SaveRequset(friendRequest);
			if (requestId > 0) {
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
//				MessageServiceHelper msh = new MessageServiceHelper();

//				SysMessage msg = new SysMessage();
//				msg.setMessageType(SysMessage.eSysMessageType.ToPerson);
//				msg.setMessageCategory(MessageCategory.SOCIAL_SERVICE_MESSAGE_REQUEST_FRIEND);
//				msg.setTargetId(friend.getUserId());
//				msg.setContent(content);
//				msg.setSenderId(userId);
//				ServiceResponse sr = msh.SendSysMessage(msg);
//				if (sr.getResponseCode() != 0) {
//					res.setResponseMessage(String.format(
//							"Fail to send message. (%d) %s",
//							sr.getResponseCode(), sr.getResponseMessage()));
//				}
			}else {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to save the request.");
			}
			
		} catch (Exception ex) {
			Util.LogError(log, String.format("RequestFriend(%d, %s)", userId, Util.ObjectToJson(friend)), ex);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Internal Error. Exception " + ex.getMessage());
			ex.printStackTrace();
		}
		return res;

	}

	protected OsfProjects GetMyFriendGroup(int userId) {
		OsfProjects group = null;
		try {
			group = groupDao.GetMyFriendGroup(userId);

		} 
		catch (Exception ex) {
			Util.LogError(log, String.format("Exception to GetMyFriendGroup(%d)", userId), ex);
			ex.printStackTrace();
		}

		return group;
	}

	public FriendResquests MyFriendRequests(int userId) {
		FriendResquests res = new FriendResquests();
		try {
			List<DcpFriendRequest> frList = friendDao.MyFriendResquests(userId);
			if (frList.size() > 0) {
				List<FriendRequestOption> frs = new LinkedList<FriendRequestOption>();
				for (DcpFriendRequest obj : frList) {
					FriendRequestOption fr = new FriendRequestOption();
					fr.setRequestId(obj.getRequestId());
					fr.setUser(UserHelper.GetUser(obj.getRequestUserid()));
					fr.setContent(obj.getContent());

					frs.add(fr);
				}
				res.setRequestList(frs);
			}

			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	public ServiceResponse RespondRequestFriend(int userId, RespondOperate oper) {

		ServiceResponse res = new ServiceResponse();
		try {
			DcpFriendRequest request = null;
			switch (oper.getRespondType()) {
			case approve: {
				request = friendDao.ProcessRquest(oper.getRequestId(),
						RequestStatus.approve.ordinal());
				if (request == null) {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to RespondRequestFriend");
					return res;
				}
				OsfProjects fromGroup = GetMyFriendGroup(userId);
				if (fromGroup == null) {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to GetMyFriendGroup");
					return res;
				}
				OsfProjects toGroup = GetMyFriendGroup(request
						.getRequestUserid());
				if (toGroup == null) {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to GetMyFriendGroup");
					return res;
				}

				OsfTeamMembers fromTm = new OsfTeamMembers();
				fromTm.setUserId(userId);
				fromTm.setEnabled(true);
				fromTm.setProjectId(toGroup.getId());
				fromTm.setRoleId(GroupRole.friend.ordinal());
				fromTm.setStatus("");

				int ret = friendDao.SetUserToGroup(fromTm);
				if (ret <= 0) {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to SetUserToGroup");
					break;
				}
				OsfTeamMembers toTm = new OsfTeamMembers();
				toTm.setUserId(request.getRequestUserid());
				toTm.setEnabled(true);
				toTm.setProjectId(fromGroup.getId());
				toTm.setRoleId(GroupRole.friend.ordinal());
				toTm.setStatus("");

				int ret2 = friendDao.SetUserToGroup(toTm);
				if (ret2 > 0) {
					ImHelper.AddFriend(userId, request.getRequestUserid());
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to RespondRequestFriend");
				}
				
				CacheManager.GetInstance().ClearCacheMyFriend(userId);
				CacheManager.GetInstance().ClearCacheMyFriend(request.getRequestUserid());
				
				break;
			}

			case refuse: {
				request = friendDao.ProcessRquest(oper.getRequestId(),
						RequestStatus.refuse.ordinal());
				if (request != null) {
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to RespondRequestFriend");
				}
				break;
			}
			default:
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format("Invalid RequestType(%s)", oper.getRespondType().toString()));
				break;
			}

		} 
		catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	public ServiceResponse InviteUser(int userId, InviteOption option) {
		ServiceResponse res = new ServiceResponse();
		try {
			if(false){
				if (!threadMonitor.isAlive()) {
					threadMonitor = new Thread(monitor);
					threadMonitor.start();
				}
			}
			boolean bEnableInvitedEmail = ConfigurationManager.GetInstance().GetConfigValue("Social_Enable_InviteUser", false);
			if (!bEnableInvitedEmail)
			{
				res.setResponseCode(ResponseCode.ERROR_NOT_IMPLEMENTED);
				res.setResponseMessage("Invite user feature is disabled.");
				return res;
			}
			
			/*boolean checkStatus = friendDao.CheackEmailJob(userId, option.getIdentify());
			if(checkStatus){
				res.setResponseCode(ResponseCode.EMAIL_HAS_BEEN_SENT);
				res.setResponseMessage("The email address has been invited.");
				return res;
			}*/
			OsfProjects group = groupDao.GetGroup(option.getGroupId());
			if(group == null){
				res.setResponseCode(ResponseCode.ERROR_GROUP_NOT_EXIST);
				res.setResponseMessage("The group is not exist for group id " + option.getGroupId());
				return res;
			}
			UserServiceHelper ush = new UserServiceHelper();
			
			UserServiceHelper.eFindOption ef = null;
			if (option.getInviteType().ordinal() == InviteOption.InviteType.email
					.ordinal()) {
				ef = UserServiceHelper.eFindOption.email;
			} else {
				ef = UserServiceHelper.eFindOption.mobile;
			}
			UserServiceHelper.FindUsersResponse fur = ush.FindUser(ef,
					option.getIdentify());
			if (fur.getResponseCode() == 0) {
				if (fur.getUsers() != null && fur.getUsers().size() > 0) {
					/*res.setResponseCode(ResponseCode.ERROR_REGISTER_USER_EMAIL_EXIST);
					res.setResponseMessage("email has existed.");*/
					OsfTeamMembers obj = new OsfTeamMembers();
					obj.setProjectId(new Long(option.getGroupId()));
					obj.setUserId(fur.getUsers().get(0).getUserId());
					obj.setEnabled(true);
					obj.setStatus("");
					obj.setRoleId(GroupRole.groupMember.ordinal());
					int mid = friendDao.SetUserToGroup(obj);
					if (mid > 0) {
						res.setResponseCode(ResponseCode.SUCCESS);
						res.setResponseMessage("Success");
					} else {
						res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
						res.setResponseMessage("Fali to Invite User to the Group");
					}
					return res;
				}else{
					UserServiceHelper.UserResponse ur = new UserServiceHelper().registerUser(option.getInviteType().toString(),option.getIdentify());
					if(ur.getResponseCode() == ResponseCode.SUCCESS && ur.getUser() != null){
						OsfTeamMembers obj = new OsfTeamMembers();
						obj.setProjectId(new Long(option.getGroupId()));
						obj.setUserId(ur.getUser().getUserId());
						obj.setEnabled(true);
						obj.setStatus("");
						obj.setRoleId(GroupRole.groupMember.ordinal());
						int aa = friendDao.SetUserToGroup(obj);
					}
				}
			} else {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage(String.format(
						"Fail to FindUsersResponse. %s %s", fur.getResponseCode(),
						fur.getResponseMessage()));
				return res;
				
			}
			
			
			
			DcpEmailJob job = new DcpEmailJob();
			job.setStatus(false);
			job.setCreatedate(new Date());
			job.setModifydate(new Date());
			job.setUserId(userId);
			job.setEmailAddress(option.getIdentify());
			job.setContent(group.getTitle());
			job.setAttempts(0);
			int emailJobId = friendDao.SaveEmailJob(job);
			if (emailJobId > 0) {
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fali to InviteUser");
			}

		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	public ServiceResponse RespondInviteUser(int formUserId, int toUserId) {
		ServiceResponse res = new ServiceResponse();
		try {
			OsfProjects fromGroup = GetMyFriendGroup(formUserId);
			if (fromGroup == null) {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to GetMyFriendGroup");
				return res;
			}
			OsfProjects toGroup = GetMyFriendGroup(toUserId);
			if (toGroup == null) {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to GetMyFriendGroup");
				return res;
			}

			OsfTeamMembers fromTm = new OsfTeamMembers();
			fromTm.setUserId(formUserId);
			fromTm.setEnabled(true);
			fromTm.setProjectId(toGroup.getId());
			fromTm.setRoleId(GroupRole.friend.ordinal());
			fromTm.setStatus("");

			int midFrom = friendDao.SetUserToGroup(fromTm);
			if (midFrom <= 0) {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to add user to group");
				return res;
			}

			OsfTeamMembers toTm = new OsfTeamMembers();
			toTm.setUserId(toUserId);
			toTm.setEnabled(true);
			toTm.setProjectId(fromGroup.getId());
			toTm.setRoleId(GroupRole.friend.ordinal());
			toTm.setStatus("");

			int midTo = friendDao.SetUserToGroup(toTm);
			if (midTo > 0) {
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			} else {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to add user to group");
			}
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}

		return res;
	}

	public ServiceResponse UnbindFriend(int userId, int friendId) {
		ServiceResponse res = new ServiceResponse();
		try {
			int nRet = friendDao.UnbindFriend(userId, friendId);
			if (nRet > 0) {
				ImHelper.RemoveFriend(userId, friendId);
				CacheManager.GetInstance().ClearCacheMyFriend(userId);
				CacheManager.GetInstance().ClearCacheMyFriend(friendId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			} 
			else {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid friend Id");
			}
		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("Exception to UnbindFriend(%d, %d)", userId, friendId), ex);
		}
		return res;
	}
}
