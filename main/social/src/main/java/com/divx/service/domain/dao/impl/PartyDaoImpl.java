package com.divx.service.domain.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.PartyDao;
import com.divx.service.domain.model.DcpParty;
import com.divx.service.domain.model.DcpPartyUser;
import com.divx.service.domain.model.OsfProjects;

@Repository
public class PartyDaoImpl extends BaseDao implements PartyDao {

	@Override
	public int CreateParty(DcpParty obj) {
		
		Session session = getSessionFactory().openSession();
		
		int groupId = -1;
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			groupId = obj.getPartyId().intValue();
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
		
		return groupId;
	}

	@Override
	public int UpdateParty(DcpParty obj) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			obj.setDatemodified(new Date());
			session.update(obj);
			return obj.getPartyId().intValue();
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ExceptionInInitializerError(ex);
		}finally{
			tx.commit();
			session.close();
		}
	}

	@Override
	public int DeleteParty(int partyId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM DcpParty s WHERE s.partyId = %d", partyId);
			List<?> group = session.createQuery(hql).list();
			
			if (group != null && group.size() > 0)
			{
				DcpParty obj = (DcpParty)group.get(0);
				session.delete(obj);
				tx.commit();
				return obj.getPartyId();
			}else{
				tx.commit();
			}
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ExceptionInInitializerError(ex);
		}finally{
			session.close();
		}
		return 0;
	}

	@Override
	public DcpParty GetParty(int partyId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM DcpParty s WHERE s.partyId = %d", partyId);
			List<?> group = session.createQuery(hql).list();
			
			if (group != null && group.size() > 0)
			{
				DcpParty obj = (DcpParty)group.get(0);
				session.delete(obj);
				tx.commit();
				return obj;
			}else{
				tx.commit();
			}
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ExceptionInInitializerError(ex);
		}finally{
			session.close();
		}
		return null;
	}

	@Override
	public List<DcpParty> GetParties(int userId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql;
//			if (queryType == 0)
			{
				hql = String.format("FROM DcpParty s WHERE s.userId = %d", userId);
			}
//			else
//			{
//				hql = String.format("FROM DcpParty s WHERE s.userId = %d and s.partytype = %d", userId, queryType);
//			}
			@SuppressWarnings("unchecked")
			List<DcpParty> group = session.createQuery(hql).list();
			tx.commit();
			
			return group;
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ExceptionInInitializerError(ex);
		}finally{
			session.close();
		}

	}

	@Override
	public List<DcpPartyUser> GetUsersInParty(int partyId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			List<DcpPartyUser> objs = (List<DcpPartyUser>)session.createCriteria(DcpPartyUser.class)
										.createCriteria("dcpParty")
										.add(Restrictions.eq("partyId", partyId)).list();

			tx.commit();
			
			return objs;
		}
		catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ExceptionInInitializerError(ex);
		}
		finally{
			session.close();
		}
	}

	@Override
	public int RemoveUser(int partyId, int userType, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int AddUsers(int partyId, List<DcpPartyUser> users) {
		// TODO Auto-generated method stub
		return 0;
	}

}
