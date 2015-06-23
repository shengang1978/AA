package com.divx.service.domain.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.GroupDao;
import com.divx.service.domain.model.DcpShare;
import com.divx.service.domain.model.OsfProjects;
import com.divx.service.domain.model.OsfTeamMembers;
import com.divx.service.model.GroupRole;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;

@Repository
public class GroupDaoImpl  extends BaseDao implements GroupDao {

	@Override
	public OsfProjects GetMyFriendGroup(int userId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("FROM OsfProjects s WHERE s.enteredById = %d and s.categoryId = 1", userId);
			List<?> shares = session.createQuery(hql).list();

			if (shares != null && shares.size() > 0)
			{
				for (Iterator<?> it = shares.iterator(); it.hasNext(); )
				{
					return ((OsfProjects)it.next());
				}
			}
			else
			{
				OsfProjects group = new OsfProjects();
				group.setCategoryId(1);
				group.setEnabled(true);
				group.setEntered(new Date());
				group.setModified(new Date());
				group.setModifiedById(new Long(userId));
				group.setEnteredById(new Long(userId));
				group.setTitle("MyFriends");
				group.setUniqueId(String.format("MyFriend-%d", userId));
				
				session.save(group);
				
				OsfTeamMembers obj = new OsfTeamMembers();
				obj.setEnabled(true);
				obj.setStatus("");
				obj.setProjectId(group.getId());
				obj.setRoleId(GroupRole.admin.ordinal());
				obj.setUserId(userId);
				
				session.save(obj);
				
				return group;
			}
		}
		catch(Throwable ex)
		{
			// Log exception
			if (tx != null)
			{
				tx.rollback();
			}
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
	public int CreateGroup(OsfProjects obj) {
		Session session = getSessionFactory().openSession();
		
		int groupId = -1;
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			groupId = obj.getId().intValue();
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
	public List<KeyValueTriplePair<Integer, Integer, OsfProjects>>  GetMyGroups(int userId,int queryType) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		List<KeyValueTriplePair<Integer, Integer, OsfProjects>> rets = new LinkedList<KeyValueTriplePair<Integer, Integer, OsfProjects>>();
		try{
			tx = session.beginTransaction();
			String hql ="";
			//SELECT m FROM OsfTeamMembers m, OsfProjects p WHERE m.projectId = p.id and p.categoryId = 1 and p.enteredById = %d and m.userId = %d
			switch(queryType){
			case 0:
				//hql = String.format("SELECT p FROM OsfProjects p,OsfTeamMembers m WHERE m.projectId = p.id and p.categoryId <> 1 and m.userId = %d  and p.enabled = %d order by p.id desc", userId,1);
				hql = String.format("SELECT p.* FROM osf_projects p join osf_team_members m on m._project_id = p._id where p._category_id != 1 and m._user_id = %d  and p._enabled = %d order by p._id desc", userId,1);
				break;
			case 1:
				hql = String.format("SELECT p.* FROM osf_projects p WHERE p._entered_by_id = %d and p._category_id = 2 and p._enabled = %d  order by p._id desc", userId,1);
				break;
			case 2:	
				hql = String.format("SELECT p.* FROM osf_projects p join osf_team_members m  WHERE m._project_id = p.id and p._category_id = 2 and m._user_id = %d and p._entered_by_id != %d and p._enabled = %d  order by p._id desc", userId, userId,1);
				break;
			case 3:
				hql = String.format("SELECT p.* FROM osf_projects p join osf_team_members m  WHERE m._project_id = p.id and p._category_id = 3 and m._user_id = %d and p._enabled = %d  order by p._id desc", userId,1);
				break;
			
			}
		
			List<OsfProjects> objs = session.createSQLQuery(hql).addEntity(OsfProjects.class).list();
			hql = hql.replace("p.*", "p._id");
			String hql2 = String.format("select p._id,count(d._project_id) from osf_projects p left join osf_team_members d on d._project_id = p._id where p._id in (%s) group by p._id order by p._id desc",hql);	
			String hql3 = String.format("select p._id,count(d.group_id) from osf_projects p left join dcp_share_user d on d.group_id = p._id where p._id in (%s) group by p._id order by p._id desc",hql);	
			List<?> userCounts = session.createSQLQuery(hql2).list();
			List<?> shareCounts = session.createSQLQuery(hql3).list();
		    if (objs != null && objs.size() > 0 && userCounts != null && shareCounts!= null)
			{
		    	for(int i = 0; i < objs.size(); i++){
		    		Object[] user = (Object[])userCounts.get(i);
		    		Object[] share = (Object[])shareCounts.get(i);
		    		if(objs.get(i).getId().intValue() == Integer.parseInt(user[0].toString()) && objs.get(i).getId().intValue() == Integer.parseInt(share[0].toString())){
		    		rets.add(new KeyValueTriplePair<Integer, Integer, OsfProjects>(Integer.parseInt(user[1].toString()) ,Integer.parseInt(share[1].toString()),objs.get(i)));	
		    		}else if(objs.get(i).getId().intValue() == Integer.parseInt(user[0].toString())){
		    			rets.add(new KeyValueTriplePair<Integer, Integer, OsfProjects>(Integer.parseInt(user[1].toString()) ,0,objs.get(i)));		
		    		}else if(objs.get(i).getId().intValue() == Integer.parseInt(share[0].toString())){
		    			rets.add(new KeyValueTriplePair<Integer, Integer, OsfProjects>(0 ,Integer.parseInt(share[1].toString()),objs.get(i)));		
		    		}else{
		    			rets.add(new KeyValueTriplePair<Integer, Integer, OsfProjects>(0 ,0,objs.get(i)));		
		    		}
		    	}		
				
			}
			return rets;
		
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
	public OsfProjects GetGroup(int groupId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM OsfProjects s WHERE s.id = %d and s.enabled = %d", groupId, 1);	
			List<?> group  = session.createQuery(hql).list();
			if (group.size() > 0)
			{
				for (Iterator<?> it = group.iterator(); it.hasNext(); )
				{
					return ((OsfProjects)it.next());
				}
			}
			
				
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
	public int DeleteGroup(int groupId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			String hql = String.format("FROM OsfProjects s WHERE s.id = %d", groupId);
			List<?> group = session.createQuery(hql).list();
			
			if (group != null && group.size() > 0)
			{
				OsfProjects obj = (OsfProjects)group.get(0);
				obj.setEnabled(false);
				session.update(obj);
				tx.commit();
				return obj.getId().intValue();
			}else{
				tx.commit();
			}
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
		}finally{
			session.close();
		}
		return 0;
	}

	@Override
	public int UpdateGroup(OsfProjects group) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			group.setModified(new Date());
			session.update(group);
			return group.getId().intValue();
		}catch(Exception ex){
			if (tx != null)
			{
				tx.rollback();
			}
		}finally{
			tx.commit();
			session.close();
		}
		return 0;
	}

	

}
