package com.divx.service.domain.repository;


import java.util.List;



import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpUserExt;



import com.divx.service.domain.model.DcpOrganization;
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
	
	DcpOrganization GetOrganization(int orgId);
	
	KeyValuePair<OsfUsers, DcpOrganization>  GetUserInfo(int userId);
	
	List<DcpEmailJob> GetUnSendEmailJobs();
	
	int  SaveEmailJob(DcpEmailJob job);

}
