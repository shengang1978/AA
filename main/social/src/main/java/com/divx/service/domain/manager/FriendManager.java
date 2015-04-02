package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MessageServiceHelper;
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
import com.divx.service.model.User;
import com.divx.service.model.UsersResponse;

@Service
public class FriendManager {
	private FriendDao friendDao;
	private GroupDao groupDao;
	private UserHelper userHelper = new UserHelper();

	@Autowired
	public void setFriendDao(FriendDao friendDao) {
		this.friendDao = friendDao;
	}

	@Autowired
	public void setFriendDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	static com.divx.service.domain.manager.SendEmailHelper.WatchMonitor monitor;
	static Thread threadMonitor;

	static {
		SendEmailHelper helper = new SendEmailHelper();
		monitor = helper.new WatchMonitor();

		threadMonitor = new Thread(monitor);
		threadMonitor.start();
	}

	public UsersResponse GetMyFriends(int userId) {
		UsersResponse res = new UsersResponse();
		try {
			List<User> friends = new LinkedList<User>();

			OsfProjects group = GetMyFriendGroup(userId);
			if (group == null) {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to create MyFriends group.");
				return res;
			}
			// List<OsfTeamMembers> tms =
			// friendDao.GetUsersInGroup(group.getId().intValue(), "");
			List<OsfTeamMembers> tms = friendDao.GetUsers(group.getId()
					.intValue(), GroupRole.friend.ordinal());
			if (tms != null && tms.size() > 0) {
				for (OsfTeamMembers tm : tms) {
					// if (tm.getStatus() == null || tm.getStatus() == "")
					{
						/*
						 * User u = new User();
						 * u.setUserId((int)tm.getUserId());
						 * u.setPhotoUrl(tm.getPhotourl());
						 * u.setNickname(tm.getNickname());
						 * u.setUsername(tm.getUsername());
						 */

						User u = userHelper.GetUser((int) tm.getUserId());
						friends.add(u);
					}
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
	 public static Logger log = Logger.getLogger(FriendManager.class);
	
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
				res.setResponseMessage("Cannot add self.");
				return res;
			}
			User friendUser = userHelper.GetUser(friend.getUserId());
			
			if (friendUser == null || friendUser.getUserId() <= 0) {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format(
						"Fail to get user(%d) info by UserService.", userId));
				return res;
			}
			User userSelf = userHelper.GetUser(userId);
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
				res.setResponseMessage(String.format("Repeat to add friend.",
						userId));
				return res;
			}

			String content = String.format("%s wants to be friend with you. ",
					userSelf.getNickname());
			if (friend.getContent() != null && !friend.getContent().isEmpty())
				content += friend.getContent();

			friendRequest.setContent(content);

			int requestId = friendDao.SaveRequset(friendRequest);
			if (requestId > 0) {
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				MessageServiceHelper msh = new MessageServiceHelper();

				ServiceResponse sr = msh.SendSysMessage(
						MessageServiceHelper.eSysMessageType.ToPerson,
						MessageCategory.SOCIAL_SERVICE_MESSAGE_REQUEST_FRIEND,
						friend.getUserId(), content);
				if (sr.getResponseCode() != 0) {
					res.setResponseMessage(String.format(
							"Fail to send message. (%d) %s",
							sr.getResponseCode(), sr.getResponseMessage()));
				}
			}else {
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			}
			
		} catch (Exception ex) {
			log.error(ex.getMessage());
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;

	}

	protected OsfProjects GetMyFriendGroup(int userId) {
		OsfProjects group = null;
		try {
			group = groupDao.GetMyFriendGroup(userId);
			if (group == null) {
				group = new OsfProjects();
				group.setCategoryId(1);
				group.setEnabled(true);
				group.setEntered(new Date());
				group.setModified(new Date());
				group.setModifiedById(new Long(userId));
				group.setEnteredById(new Long(userId));
				group.setTitle("MyFriends");
				group.setUniqueId(String.format("MyFriend-%d-%d", userId,
						new Date().getTime()));
				int tmid = groupDao.CreateGroup(group);
				if (tmid > 0) {

					OsfTeamMembers obj = new OsfTeamMembers();
					obj.setEnabled(true);
					obj.setStatus("");
					obj.setProjectId(tmid);
					obj.setRoleId(GroupRole.admin.ordinal());
					obj.setUserId(userId);
					/*
					 * User user = userHelper.GetUser(userId);
					 * obj.setNickname(user.getNickname());
					 * obj.setPhotourl(user.getPhotoUrl());
					 * obj.setUsername(user.getUsername());
					 */
					friendDao.SetUserToGroup(obj);
				}
			}
		} catch (Exception ex) {

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
					fr.setUserId(obj.getRequestUserid());
					fr.setContent(obj.getContent());
					fr.setUserPhoto(userHelper.GetUser(obj.getRequestUserid())
							.getPhotoUrl());
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

				/*
				 * User fromUser = userHelper.GetUser(userId); if (fromUser ==
				 * null) {
				 * res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				 * res.setResponseMessage("Fail to UserService"); return res; }
				 * 
				 * User toUser = userHelper.GetUser(request.getRequestUserid());
				 * if (toUser == null) {
				 * res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				 * res.setResponseMessage("Fail to UserService"); return res; }
				 */

				OsfTeamMembers fromTm = new OsfTeamMembers();
				fromTm.setUserId(userId);
				fromTm.setEnabled(true);
				fromTm.setProjectId(toGroup.getId());
				fromTm.setRoleId(GroupRole.friend.ordinal());
				fromTm.setStatus("");
				/*
				 * fromTm.setUsername(fromUser.getUsername());
				 * fromTm.setNickname(fromUser.getNickname());
				 * fromTm.setPhotourl(fromUser.getPhotoUrl());
				 */
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
				/*
				 * toTm.setUsername(toUser.getUsername());
				 * toTm.setNickname(toUser.getNickname());
				 * toTm.setPhotourl(toUser.getPhotoUrl());
				 */

				int ret2 = friendDao.SetUserToGroup(toTm);
				if (ret2 > 0) {
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to RespondRequestFriend");
				}
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
				break;
			}

		} catch (Exception ex) {
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return res;
	}

	public ServiceResponse InviteUser(int userId, InviteOption option) {
		ServiceResponse res = new ServiceResponse();
		try {
			if (!threadMonitor.isAlive()) {
				threadMonitor = new Thread(monitor);
				threadMonitor.start();
			}
			
			boolean bEnableInvitedEmail = ConfigurationManager.GetInstance().GetConfigValue("Social_Enable_InviteUser", false);
			if (!bEnableInvitedEmail)
			{
				res.setResponseCode(ResponseCode.ERROR_NOT_IMPLEMENTED);
				res.setResponseMessage("Invite user feature is disabled.");
				return res;
			}
			
			boolean checkStatus = friendDao.CheackEmailJob(userId, option.getIdentify());
			if(checkStatus){
				res.setResponseCode(ResponseCode.EMAIL_HAS_BEEN_SENT);
				res.setResponseMessage("The email address has been invited.");
				return res;
			}
			
			DcpEmailJob job = new DcpEmailJob();
			job.setStatus(false);
			job.setCreatedate(new Date());
			job.setModifydate(new Date());
			job.setUserId(userId);
			job.setEmailAddress(option.getIdentify());
			job.setContent(option.getMessage());
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
			/*
			 * User fromUser = userHelper.GetUser(formUserId); if (fromUser ==
			 * null) { res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			 * res.setResponseMessage("Fail to UserService"); return res; }
			 * 
			 * User toUser = userHelper.GetUser(toUserId); if (toUser == null) {
			 * res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			 * res.setResponseMessage("Fail to UserService"); return res; }
			 */

			OsfTeamMembers fromTm = new OsfTeamMembers();
			fromTm.setUserId(formUserId);
			fromTm.setEnabled(true);
			fromTm.setProjectId(toGroup.getId());
			fromTm.setRoleId(GroupRole.friend.ordinal());
			fromTm.setStatus("");
			/*
			 * fromTm.setUsername(fromUser.getUsername());
			 * fromTm.setNickname(fromUser.getNickname());
			 * fromTm.setPhotourl(fromUser.getPhotoUrl());
			 */
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
			/*
			 * toTm.setUsername(toUser.getUsername());
			 * toTm.setNickname(toUser.getNickname());
			 * toTm.setPhotourl(toUser.getPhotoUrl());
			 */

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

	public UsersResponse UnbindFriend(int userId, int friendId) {
		UsersResponse res = new UsersResponse();
		int result = 0;
		try {
			OsfTeamMembers teamMembers = friendDao
					.GetMyFriend(userId, friendId);//
			if (teamMembers != null) {
				result = friendDao.UnbindFriend(teamMembers);
				if (result > 0) {
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
				} else {
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to unbind the friend");
				}
			} else {
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid friend Id");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;

	}

}
