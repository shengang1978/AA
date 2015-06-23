package com.divx.service.domain.dao;

import java.util.List;

import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpEmailTemplate;
import com.divx.service.domain.model.DcpFriendRequest;
import com.divx.service.domain.model.OsfMessages;
import com.divx.service.domain.model.OsfTeamMembers;


public interface FriendDao {

	//List<OsfTeamMembers> GetUsersInGroup(int groupId, String status);
	
	int RemoveUserFromGroup(int groupId, int adminUserId, int targetUserId);
	
	int SetUserToGroup(OsfTeamMembers obj);
	
	OsfTeamMembers GetUserInGroup(int groupId, int userId);
	
	List<OsfTeamMembers> GetGroupsByUserId(int userId);
	
	int SetUserRoleInGroup(int groupId, int userId,int roleId);
	
	List<OsfTeamMembers> GetUsers(int groupId,int roleId);

	OsfTeamMembers GetMyFriend(int userId, int friendId);
	
	int UnbindFriend(OsfTeamMembers osfTeamMembers);
	
	int UnbindFriend(int userId, int friendId);

	int SaveRequset(DcpFriendRequest friendRequest);
	
	List<DcpFriendRequest> MyFriendResquests(int userId);
	
	DcpFriendRequest ProcessRquest(int requestId, int status);
	
	//OsfMailSettings GetUserMailSetting();
	
	DcpEmailTemplate GetEmailTemplate();
	
	int SaveInviteInfo(OsfMessages invitemsg);
	
	int AddUsersToGroup(int groupId,String groupIds,String userIds,String emails,String mobiles);
	
	OsfTeamMembers GetUser(int userId);
	
	List<DcpEmailJob> GetUnSendEmailJobs();
	
	int  SaveEmailJob(DcpEmailJob job);
	
	boolean CheackEmailJob(int userId,String emailAddress);
	
	
}
