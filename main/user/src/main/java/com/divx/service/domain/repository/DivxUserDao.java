package com.divx.service.domain.repository;


import java.util.List;


import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpOauthUsers;
import com.divx.service.domain.model.DcpRole;
import com.divx.service.domain.model.DcpUserExt;



import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpUserRole;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.model.KeyValuePair;

public interface DivxUserDao {
	OsfUsers GetUserByUsername(int orgId, String username);
	OsfUsers GetUserByMobile(int orgId, String mobile);
	OsfUsers GetUserByEmail(int orgId, String email);
	
	OsfUsers GetUser(int userId);
	
	int CreateUser(OsfUsers user);
	int UpdateUser(OsfUsers user);

	
	DcpUserExt GetUserExt(int userId);
	
	int SetUserExt(DcpUserExt userExt);
	
	//List<KeyValuePair<OsfUsers, DcpUserExt>>  GetUserExtByUsername(int orgId, String username);
	
	//int SetUserExtByUsername(int orgId, String username,DcpUserExt userExt);

	
	List<OsfUsers> FindUsersInUsername(int orgId, String username);
	List<OsfUsers> FindUsersInNickname(int orgId, String nickname);
	List<OsfUsers> FindUsersInEmail(int orgId, String email);
	List<OsfUsers> FindUsersInMobile(int orgId, String mobile);
	
	List<OsfUsers> ListUsers(int orgId, int startPos, int endPos);
	
	DcpOrganization GetOrganization(int orgId);
	
	KeyValuePair<OsfUsers, DcpOrganization>  GetUserInfo(int userId);
	
	List<DcpEmailJob> GetUnSendEmailJobs();
	
	int  SaveEmailJob(DcpEmailJob job);
	
	int createOauthUser(DcpOauthUsers oauthUser);
	
	DcpOauthUsers GetDcpOauthUser(String openId,int oauthType);
	
	int createUserRole(DcpUserRole userRole);
	
	DcpUserRole GetRoleByUserId(int userId);
	
	DcpRole GetRole(int roleId);
	
	OsfUsers GetUserByDeviceUniqueId(String deviceUniqueId,int registType);
	
	int LogLastAccess(String deviceUniqueId);
}
