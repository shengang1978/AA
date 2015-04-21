package com.divx.service.domain.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpEmailTemplate;
import com.divx.service.domain.model.DcpFriendRequest;
import com.divx.service.domain.model.OsfMessages;
import com.divx.service.domain.model.OsfTeamMembers;

@Repository
public class FriendDaoImpl extends BaseDao 
	implements FriendDao 
{

	@Override
	public int RemoveUserFromGroup(int groupId, int adminUserId,
			int targetUserId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			String hql = String.format("FROM OsfTeamMembers s WHERE s.projectId = %d  and s.userId = %d", groupId,targetUserId );
			List<?> objs = session.createQuery(hql).list();
			
			if (objs != null && objs.size() > 0)
			{
				OsfTeamMembers tm = (OsfTeamMembers)objs.get(0);
				
				session.delete(tm);
				tx.commit();
				return tm.getId().intValue();
			}
					
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw ex;
			
		}finally{
			session.close();
		}
		return 0;
	}


	@Override
	public OsfTeamMembers GetUserInGroup(int groupId, int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			
			String hql = "";
			if (userId >= 0)
			{
				hql = String.format("FROM OsfTeamMembers s WHERE s.projectId = %d and s.userId = %d order by s.id desc", groupId, userId);
			}
			else if (userId == -1)
			{
				hql = String.format("FROM OsfTeamMembers s WHERE s.projectId = %d order by s.id desc", groupId);
			}
			List<?> shares = session.createQuery(hql).list();
			
			if (shares != null && shares.size() > 0)
				return (OsfTeamMembers)shares.iterator().next();
		}
		catch(Throwable ex)
		{
			tx = null;
			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			if (tx != null)
				tx.commit();
			session.close();
		}
		return null;
	}

	@Override
	public int SetUserToGroup(OsfTeamMembers obj) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("FROM OsfTeamMembers s WHERE s.projectId = %d and s.userId = %d", obj.getProjectId(), obj.getUserId());

			List<?> objs = session.createQuery(hql).list();
			
			if (objs != null && objs.size() > 0)
			{
				OsfTeamMembers tm = (OsfTeamMembers)objs.get(0);
			/*	tm.setNickname(obj.getNickname());
				tm.setUsername(obj.getUsername());
				tm.setPhotourl(obj.getPhotourl());*/
				session.update(tm);
				tx.commit();
				return tm.getId().intValue();
			}
			else
			{
				session.save(obj);
				tx.commit();
				return obj.getId().intValue();
			}			
		}
		catch(Throwable ex)
		{
			// Log exception
			if (tx != null)
			{
				tx.rollback();
			}
			
			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OsfTeamMembers> GetGroupsByUserId(int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM OsfTeamMembers s WHERE s.userId = %d", userId);
			return session.createQuery(hql).list();
			
		}catch(Exception ex){
			tx = null;
			throw ex;
			
		}finally{
			if (tx != null)
				tx.commit();
			session.close();
		}
		
	}


	@Override
	public int SetUserRoleInGroup(int groupId, int userId,
			int roleId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM OsfTeamMembers s WHERE s.userId = %d and s.projectId = %d", userId,groupId);
			List<?> objs = session.createQuery(hql).list();
			
			if (objs != null && objs.size() > 0)
			{
				OsfTeamMembers tm = (OsfTeamMembers)objs.get(0);
				tm.setRoleId(roleId);
				session.update(tm);
				tx.commit();
				return tm.getId().intValue();
			}else{
				tx.commit();
			}			
						
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw ex;
			
		}finally{
			session.close();
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OsfTeamMembers> GetUsers(int groupId, int roleId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			String hql = "";
			if(roleId ==0){
				hql = String.format("FROM OsfTeamMembers s WHERE s.projectId = %d and enabled=true",groupId);
			}else{
				hql = String.format("FROM OsfTeamMembers s WHERE s.projectId = %d and s.roleId = %d and enabled=true",groupId,roleId);
			}
			return session.createQuery(hql).list();
		}catch(Exception ex){
			tx = null;
			throw ex;
			
		}finally{
			if (tx != null)
				tx.commit();
			session.close();
		}
		
		
	}


	@Override
	public OsfTeamMembers GetMyFriend(int userId, int friendId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		OsfTeamMembers res;
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("SELECT m FROM OsfTeamMembers m, OsfProjects p WHERE   m.projectId = p.id  and p.categoryId = 1 and p.enteredById = %d and m.userId = %d and  m.enabled=true", userId, friendId);
			//session.createSQLQuery("SELECT ")
			List<?> shares = session.createQuery(hql).list();
			
			if (shares != null && shares.size() > 0)
				return (OsfTeamMembers)shares.iterator().next();
		}
		catch(Throwable ex)
		{
			tx = null;
			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			if (tx != null)
				tx.commit();
			session.close();
		}
		return null;
	}

	@Override
	public int SaveRequset(DcpFriendRequest friendRequest) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
				session.save(friendRequest);
				tx.commit();
				return friendRequest.getRequestId().intValue();
						
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw ex;
		}finally{
			session.close();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DcpFriendRequest> MyFriendResquests(int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			
			//String hql = String.format("FROM DcpFriendRequest d WHERE d.receiveUserid = %d and d.status = %d", userId, 0);
			String hql = String.format("select * from(select * from divx_social.dcp_friend_request as dd WHERE dd.receive_userid = %d and dd.status = %d order by dd.request_id desc) as temp group by temp.request_userid ", userId, 0);
			
			//String hql = String.format("select d FROM DcpFriendRequest as d, DcpFriendRequest as temp WHERE temp.requestId = d.requestId and temp.receiveUserid = %d and temp.status = %d  group by temp.requestUserid  ", userId, 0);
			//String hql = String.format("FROM DcpFriendRequest as d where d.requestId = all(select temp.requestId DcpFriendRequest as temp WHERE temp.receiveUserid = %d and temp.status = %d  order by temp.requestUserid) group by d.requestUserid", userId, 0);
			//return session.createQuery(hql).list();
			return session.createSQLQuery(hql).addEntity(DcpFriendRequest.class).list();
		}catch(Exception ex){
			 
			tx=null;
			throw new RuntimeException(ex);
		}finally{
			if (tx != null)
				tx.commit();
			session.close();
		}
		
	}

	@Override
	public DcpFriendRequest ProcessRquest(int requestId, int status) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		DcpFriendRequest fr = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM DcpFriendRequest d WHERE d.requestId = %d", requestId);
			List<?> objs = session.createQuery(hql).list();
			
			if (objs != null && objs.size() > 0)
			{
				fr = (DcpFriendRequest)objs.get(0);	
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String updatehql = String.format("UPDATE DcpFriendRequest d SET d.status = %d,d.dateresponse = '%s' WHERE d.requestUserid = %d and d.receiveUserid = %d", status,sdf.format(new Date()),fr.getRequestUserid(),fr.getReceiveUserid());
				int mid = session.createQuery(updatehql).executeUpdate();
				
				tx.commit();
				return fr;
			}		
			
			
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			};
			
			throw ex;
		}finally{
			
			session.close();
		}
		return fr;
	}

	@Override
	public int SaveInviteInfo(OsfMessages invitemsg) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
				session.save(invitemsg);
				tx.commit();
				return invitemsg.getId().intValue();
						
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw ex;
		}finally{
			session.close();
		}
		
	}


	@Override
	public DcpEmailTemplate GetEmailTemplate() {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM DcpEmailTemplate");
			List<?> mails = session.createQuery(hql).list();
			
			if (mails != null && mails.size() > 0)
				return (DcpEmailTemplate)mails.iterator().next();
		}catch(Exception ex){
			tx = null;
			throw ex;
			
		}finally{
			if (tx != null)
				tx.commit();
			session.close();
		}
		
		return null;
	}


	@Override
	public int AddUsersToGroup(int groupId,String groupIds,String userIds,String emails,String mobiles) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			//String hql = String.format("{CALL f_groupAddUsers(%d,'%s','%s','%s','%s')}",groupId,groupIds,userIds,emails,mobiles);
			//session.createSQLQuery(hql);
			SQLQuery query = session.createSQLQuery("{CALL f_groupAddUsers(?,?,?,?,?)}");
			query.setInteger(0, groupId);
			query.setString(1, groupIds);
			query.setString(2, userIds);
			query.setString(3, emails);
			query.setString(4, mobiles);			
			
			List<Object> ret = query.list();
			if(ret != null){
				return Integer.parseInt(ret.get(0).toString());
			}else{
				return -1;
			}
		}catch(Exception ex){
			tx = null;
			throw ex;
			
		}finally{
			if (tx != null)
				tx.commit();
			session.close();
		}
	}


	@Override
	public OsfTeamMembers GetUser(int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("select s FROM OsfProjects p, OsfTeamMembers s WHERE p.id = s.projectId and p.enteredById = s.userId and p.categoryId = 1 and s.roleId = 1 and s.userId = %d order by s.id desc", userId);

			List<?> shares = session.createQuery(hql).list();
			
			if (shares != null && shares.size() > 0)
				return (OsfTeamMembers)shares.iterator().next();
		}
		catch(Throwable ex)
		{
			tx = null;
			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			if (tx != null)
				tx.commit();
			session.close();
		}
		return null;
	}


	@Override
	public List<DcpEmailJob> GetUnSendEmailJobs() {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("From DcpEmailJob j WHERE j.status = 0 and j.attempts < 5");
			
			return session.createQuery(hql).list();
		}catch(Exception ex ){
			tx = null;
			throw ex;
		}finally{
			if (tx != null)
				tx.commit();
			session.close();
		}
	}


	@Override
	public int SaveEmailJob(DcpEmailJob job) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("From DcpEmailJob j WHERE j.userId = %d and j.emailAddress = '%s' and j.status = 0",job.getUserId(),job.getEmailAddress());
			List<?> objs = session.createQuery(hql).list();
			if(objs != null && objs.size() > 0){
				DcpEmailJob  obj = (DcpEmailJob) objs.get(0);
				obj.setModifydate(new Date());
				obj.setStatus(job.isStatus());
				obj.setAttempts(job.getAttempts());
				session.update(obj);
				return obj.getId();
			}else{
				session.save(job);
				return  job.getId();
			}
		   
		}
		catch(Throwable ex)
		{
			tx = null;
			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			if (tx != null)
				tx.commit();
			session.close();
		}
		
	}
 

	@Override
	public int  UnbindFriend(OsfTeamMembers osfTeamMembers) {
		Session session=getSessionFactory().openSession();
		Transaction transaction=null;
		try {
			transaction=session.beginTransaction();
			osfTeamMembers.setEnabled(false);
			session.update(osfTeamMembers);
			return 1;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException(e);
		}finally{
			if (transaction != null)
				transaction.commit();
			session.close();
		}
	}


	@Override
	public boolean CheackEmailJob(int userId, String emailAddress) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("From DcpEmailJob j WHERE j.userId = %d and j.emailAddress = '%s' and j.status = 1",userId,emailAddress);
			List<?> objs = session.createQuery(hql).list();
			if(objs != null && objs.size() > 0){
			return true;
			}else{
			return  false;
			}
		   
		}
		catch(Throwable ex)
		{
			tx = null;
			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			if (tx != null)
				tx.commit();
			session.close();
		}
	}


	 
}
