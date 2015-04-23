package com.divx.service.domain.repository.impl;


import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.DcpOauthUsers;
import com.divx.service.domain.model.DcpRole;
import com.divx.service.domain.model.DcpUserExt;
import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.DcpUserRole;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.model.KeyValuePair;
@Repository
public class DivxUserDaoImpl extends BaseDao implements DivxUserDao {

	@Override
	public OsfUsers GetUser(int userId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		OsfUsers obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.id = %d", userId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (OsfUsers)objs.get(0);
			}
			trans.commit();
		
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int CreateUser(OsfUsers user) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(user);
			trans.commit();
		
			return user.getId().intValue();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
	}

	@Override
	public int UpdateUser(OsfUsers user) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.update(user);
			trans.commit();
		
			return user.getId().intValue();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
	}

	@Override
	public OsfUsers GetUserByUsername(int orgId, String username) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		OsfUsers obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.username = '%s' and u.organizationId = %d", username, orgId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (OsfUsers)objs.get(0);
			}
			trans.commit();
		
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public OsfUsers GetUserByMobile(int orgId, String mobile) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		OsfUsers obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.mobile = '%s' and u.organizationId = %d", mobile, orgId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (OsfUsers)objs.get(0);
			}
			trans.commit();
		
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public OsfUsers GetUserByEmail(int orgId, String email) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		OsfUsers obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.email = '%s' and u.organizationId = %d", email, orgId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (OsfUsers)objs.get(0);
			}
			trans.commit();
		
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	@Override
	public DcpUserExt GetUserExt(int userId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			String 	hql = String.format("FROM DcpUserExt u WHERE u.userId = %d", userId);
			List<DcpUserExt> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				return  (DcpUserExt)objs.get(0);
			}
		
			return  null;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			trans.commit();
			ss.close();
		}
	}

	@Override
	public int SetUserExt(DcpUserExt userExt) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			String 	hql = String.format("FROM DcpUserExt u WHERE u.userId = %d", userExt.getUserId());
			List<DcpUserExt> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				DcpUserExt due = objs.get(0);
				due.setUserId(userExt.getUserId());
				due.setRealname(userExt.getRealname());
				due.setBirthday(userExt.getBirthday());
				due.setComefrom(userExt.getComefrom());
				due.setEmail2(userExt.getEmail2());
				due.setGender(userExt.getGender());
				due.setHomepage(userExt.getHomepage());
				due.setIm(userExt.getIm());
				due.setIm2(userExt.getIm2());
				due.setMobile2(userExt.getMobile2());
				due.setSelfintroduce(userExt.getSelfintroduce());
				due.setSignature(userExt.getSignature());
				due.setTdcImg(userExt.getTdcImg());
				due.setTelephone(userExt.getTelephone());
				due.setDatemodified(new Date());
				ss.update(due);
				
			}else{
				userExt.setDatecreated(new Date());
				userExt.setDatemodified(new Date());
				ss.save(userExt);
			}
			return userExt.getUserId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			trans.commit();
			ss.close();
		}
	}

	/*@Override
	public List<KeyValuePair<OsfUsers, DcpUserExt>> GetUserExtByUsername(int orgId, String username) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			List<KeyValuePair<OsfUsers, DcpUserExt>> ret = new LinkedList<KeyValuePair<OsfUsers, DcpUserExt>>();
			trans = ss.beginTransaction();
			String 	hql = String.format("SELECT u,due FROM OsfUsers u, DcpUserExt due where u.id = due.userId and u.username = '%s' and u.organizationId = %d)",username, orgId);
			//String 	hql = String.format("SELECT u.*,due.* FROM Osf_users u left join dcp_user_ext due on u._id = due.user_id where  u._username = '%s' and u._organization_id = %d",username, orgId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				Iterator<?> it = objs.iterator();
				while(it.hasNext())
				{					
					Object[] ary = (Object[])it.next();
					
					ret.add(new KeyValuePair<OsfUsers, DcpUserExt>((OsfUsers)ary[0], (DcpUserExt)ary[1]));
				}
			}
			
			return ret;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			trans.commit();
			ss.close();
		}
	}

	@Override
	public int SetUserExtByUsername(int orgId, String username,
			DcpUserExt userExt) {
		// TODO Auto-generated method stub
		return 0;
	}*/

	@Override
	public List<OsfUsers> FindUsersInUsername(int orgId, String username) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.organizationId = %d and u.username LIKE:username ORDER BY u.username", orgId);
			Query query = ss.createQuery(hql);
			query.setParameter("username", username + '%');
			List<OsfUsers> objs = query.list();
			
			trans.commit();
		
			return objs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public List<OsfUsers> FindUsersInNickname(int orgId, String nickname) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.organizationId = %d and u.nickname LIKE:nickname ORDER BY u.username", orgId);
			Query query = ss.createQuery(hql);
			query.setParameter("nickname", nickname + '%');
			List<OsfUsers> objs = query.list();
			
			trans.commit();
		
			return objs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public List<OsfUsers> FindUsersInEmail(int orgId, String email) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.organizationId = %d and u.email LIKE:email ORDER BY u.username", orgId);
			Query query = ss.createQuery(hql);
			query.setParameter("email", email + '%');
			List<OsfUsers> objs = query.list();
			
			trans.commit();
		
			return objs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public List<OsfUsers> FindUsersInMobile(int orgId, String mobile) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM OsfUsers u WHERE u.organizationId = %d and u.mobile LIKE:mobile ORDER BY u.username", orgId);
			Query query = ss.createQuery(hql);
			query.setParameter("mobile", mobile + '%');
			List<OsfUsers> objs = query.list();
			
			trans.commit();
		
			return objs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpOrganization GetOrganization(int orgId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpOrganization obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpOrganization o WHERE o.id = %d", orgId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (DcpOrganization)objs.get(0);
			}
			trans.commit();
		
			return obj;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public KeyValuePair<OsfUsers, DcpOrganization> GetUserInfo(int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		KeyValuePair<OsfUsers, DcpOrganization> ret = null;
		try
		{			
			
			tx = session.beginTransaction();
			
			String hql = String.format("select u, o FROM OsfUsers u, DcpOrganization o WHERE u.organizationId = o.id and u.id = %d", userId);

			List<?> objs = session.createQuery(hql).list();
			
			if (objs != null && objs.size() > 0)
			{
				Object[] ary = (Object[])objs.get(0);
				ret = new KeyValuePair<OsfUsers, DcpOrganization>((OsfUsers)ary[0], (DcpOrganization)ary[1]);	
				
			}
			return ret;
			
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
			String hql = String.format("From DcpEmailJob j WHERE j.userId = %d and j.emailAddress = '%s'",job.getUserId(),job.getEmailAddress());
			List<?> objs = session.createQuery(hql).list();
			if(objs != null && objs.size() > 0){
				DcpEmailJob  obj = (DcpEmailJob) objs.get(0);
				obj.setModifydate(new Date());
				obj.setStatus(job.getStatus());
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
	public int createOauthUser(DcpOauthUsers oauthUser) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			List<?> objs = ss.createCriteria(DcpOauthUsers.class)
					.add(Restrictions.and(Restrictions.eq("openId", oauthUser.getOpenId()), Restrictions.eq("oauthType", oauthUser.getOauthType())))
					.list();
			if(objs != null && objs.size() > 0){
				DcpOauthUsers obj = (DcpOauthUsers)objs.get(0);
				obj.setAccessToken(oauthUser.getAccessToken());
				obj.setModifyDate(oauthUser.getModifyDate());
				ss.update(obj);
			}else{
				ss.save(oauthUser);
			}
			
			trans.commit();
		
			return oauthUser.getId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
		
	}

	@Override
	public DcpOauthUsers GetDcpOauthUser(String openId, int oauthType) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpOauthUsers oauthUser = null;
		try
		{
			trans = ss.beginTransaction();
			
			//String hql = String.format("FROM DcpOauthUsers ou WHERE ou.openId = '%s' and ou.oauthType = %d", openId, oauthType); 	
			List<?> objs = ss.createCriteria(DcpOauthUsers.class)
					.add(Restrictions.and(Restrictions.eq("openId", openId), Restrictions.eq("oauthType", oauthType)))
					.list();
			if(objs != null && objs.size() > 0){
				oauthUser = (DcpOauthUsers)objs.get(0);
			}

			return oauthUser;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			trans.commit();
			ss.close();
		}	
	}

	@Override
	public int createUserRole(DcpUserRole userRole) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(userRole);
			return userRole.getId();
		}catch(Exception ex){
			
			if (trans != null)
				trans.rollback();
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			trans.commit();
			ss.close();
		}	
	}

	@Override
	public DcpRole GetRole(int roleId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpRole role = null;
		try
		{
			trans = ss.beginTransaction();
			List<?> objs = ss.createCriteria(DcpRole.class)
							 	.add(Restrictions.eq("roleId", roleId)).list();
			if(objs != null && objs.size() > 0){
				role = (DcpRole)objs.get(0);
			}
		}catch(Exception ex){
			
			if (trans != null)
				trans.rollback();
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			trans.commit();
			ss.close();
		}	
		return role;
	}

	@Override
	public DcpUserRole GetRoleByUserId(int userId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpUserRole userRole = null;
		try
		{
			trans = ss.beginTransaction();
			//String hql = String.format("SELECT *FROM dcp_role as r WHERE r.role_id = (SELECT u.role_id FROM dcp_user_role as u WHERE u.user_id = %d)", userId);
			
			Criteria c = ss.createCriteria(DcpUserRole.class);
			c.createCriteria("dcpRole");
			List<?> objs = c.createCriteria("osfUsers")
			.add(Restrictions.eq("id", new Long(userId))).list();
//			List<?> objs = ss.createCriteria(DcpUserRole.class)
//					.createCriteria("dcpRole")
//							 .createCriteria("osfUsers")
//								.add(Restrictions.eq("id", new Long(userId))).list();

			if(objs != null && objs.size() > 0){
				userRole = (DcpUserRole)objs.get(0);
				
			}
		}catch(Exception ex){
			
			if (trans != null)
				trans.rollback();
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			trans.commit();
			ss.close();
		}	
		return userRole;
	}

	


}
