package com.divx.service.domain.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.ShareDao;
import com.divx.service.domain.model.DcpShare;
import com.divx.service.model.KeyValueTriplePair;



@Repository
public class ShareDaoImpl  extends BaseDao implements ShareDao {
	
	@Override
	public int CreateShare(DcpShare obj) {

		Session session = getSessionFactory().openSession();
		
		int shareId = -1;
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("FROM DcpShare s where s.mediaId = %d and s.groupId = %d", obj.getMediaId(),obj.getGroupId());
			List<DcpShare> shares =  session.createQuery(hql).list();
			if(shares !=null && shares.size() > 0){
				tx.commit();
				return shares.get(0).getShareId();
			}
			session.save(obj);
			tx.commit();
			shareId = obj.getShareId();
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
		
		return shareId;
	}

	@Override
	public List<DcpShare> GetShares(int userId, int option) {
		
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			String hql = "";
			switch(option){
			case 0://all
				hql = String.format("select s FROM DcpShare s,OsfTeamMembers tm  WHERE s.groupId = tm.projectId and tm.userId = %d order by s.shareId desc",userId);
				 break;
			case 1://mine
				 hql = String.format("select s FROM DcpShare s,OsfProjects g  WHERE s.groupId = g.id and g.enteredById = %d and g.categoryId = 2  order by s.shareId desc", userId);
				break;
			case 2://friend
				hql = String.format("FROM DcpShare s WHERE s.groupId in (SELECT g.id FROM OsfProjects g ,OsfTeamMembers tm where g.id = tm.projectId and tm.userId = %d and g.categoryId <> 1 and g.enteredById <> %d)  order by s.shareId desc", userId, userId);
				break;
			case 3://p
				hql = String.format("select s FROM DcpShare s,OsfProjects g  WHERE s.groupId = g.id and g.enteredById = %d and g.categoryId = 3 order by s.shareId desc", userId);
				break;
			}
			
			List<DcpShare> res = session.createQuery(hql).list();
			
			tx.commit();
			
			return res;
		}
		catch(Throwable ex)
		{
			// Log exception

			throw new ExceptionInInitializerError(ex);
		}
		finally
		{
			session.close();
		}
	}

	@Override
	public List<DcpShare> GetSharesOfGroup(int groupId) {
		
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("FROM DcpShare s WHERE s.groupId = %d order by s.shareId desc", groupId);
			return session.createQuery(hql).list();
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
	
	
	/*@Override
	public List<DcpShare> GetSharesOfFriend(int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("FROM DcpShare s WHERE s.sharetype = 1 AND s.groupId in (SELECT g.id FROM OsfProjects g ,OsfTeamMembers tm where g.id = tm.projectId and tm.userId = %d and g.categoryId <> 1)", userId);
			
			return session.createQuery(hql).list();
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
	}*/

	@Override
	public List<KeyValueTriplePair<Integer, Integer, DcpShare>> GetSharesOfGroups(int userId, int groupId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		List<KeyValueTriplePair<Integer, Integer, DcpShare>> rets = new LinkedList<KeyValueTriplePair<Integer, Integer, DcpShare>>();
		try
		{
			tx = session.beginTransaction();
	
			String hql = String.format("select s from DcpShare s where s.groupId = %d order by s.shareId desc", groupId);
			String hql2 = String.format("select s.share_id,sum(case c._activitytype when 1 then 1 else 0 end) ,sum(case c._activitytype when 0 then 1 else 0 end) from dcp_share s left join osf_comments c on s.share_id = c._linked_id where s.group_id = %d group by s.share_id order by s.share_id desc", groupId);
			 List<DcpShare> shares = session.createQuery(hql).list();
			 List<?> counts = session.createSQLQuery(hql2).list();
			 if(null != shares &&shares.size() > 0 && counts != null){
				 		for(int i = 0;i < shares.size();i++){
				 			Object[] count = (Object[])counts.get(i);
				 			if(shares.get(i).getShareId() == Integer.parseInt(count[0].toString())){
				 				rets.add(new KeyValueTriplePair<Integer, Integer, DcpShare>(Integer.parseInt(count[1].toString()),Integer.parseInt(count[2].toString()),shares.get(i)));
				 			}else{
				 				rets.add(new KeyValueTriplePair<Integer, Integer, DcpShare>(0,0,shares.get(i)));
				 			}
							
				 		}
					
					
			 }
			 return rets;
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
	public List<DcpShare> GetPublicShares(List<Integer> contentTypes) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Query query;
			String hql;
			if (contentTypes != null && contentTypes.size() > 0)
			{
				hql = String.format("FROM DcpShare s WHERE s.sharetype = 0 and contenttype IN (:contentTypes) order by s.shareId DESC");
				query = session.createQuery(hql);
				query.setParameterList("contentTypes", contentTypes);
			}
			else
			{
				hql = String.format("FROM DcpShare s WHERE s.sharetype = 0 order by s.shareId DESC");
				query = session.createQuery(hql);
			}
			return (List<DcpShare>)query.list();
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
	public int SetShareByTitle(DcpShare obj) {
		Session session = getSessionFactory().openSession();
		
		int shareId = -1;
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("FROM DcpShare s where s.assettype = %d and s.mediatitle = '%s'", obj.getAssettype(), obj.getMediatitle());
			List<DcpShare> shares =  session.createQuery(hql).list();
			if(shares !=null && shares.size() > 0){
				tx.commit();
				return shares.get(0).getShareId();
			}
			session.save(obj);
			tx.commit();
			shareId = obj.getShareId();
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
		
		return shareId;
	}

	@Override
	public int DeleteShare(int userId, int shareId) {
		Session session = getSessionFactory().openSession();
		
		
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("UPDATE DcpShare s SET s.deleted = 1 where s.shareId = %d and s.userId = %d", shareId, userId);
			int mid = session.createQuery(hql).executeUpdate();
			
			return mid;
		
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
			tx.commit();
			session.close();
		}
		
		
	}

	@Override
	public int UpdateDcpShareStatus(int mediaId, int status) {
		Session session = getSessionFactory().openSession();
		
		
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("UPDATE DcpShare s SET s.status = %d where s.mediaId = %d ", status, mediaId);
			int mid = session.createQuery(hql).executeUpdate();
			
			return mid;
		
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
			tx.commit();
			session.close();
		}
	}
}
