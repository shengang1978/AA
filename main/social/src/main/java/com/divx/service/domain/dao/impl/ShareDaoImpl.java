package com.divx.service.domain.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.ShareDao;
import com.divx.service.domain.model.DcpLikeStat;
import com.divx.service.domain.model.DcpShare;
import com.divx.service.domain.model.DcpShareUser;
import com.divx.service.domain.model.ShareDbInfo;
import com.divx.service.model.KeyValueFourPair;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;
import com.divx.service.model.QueryOption;
import com.divx.service.model.ShareOption.ShareType;



@Repository
public class ShareDaoImpl  extends BaseDao implements ShareDao {
	
	@Override
	public int CreateShare(DcpShare obj,int groupId) {

		Session session = getSessionFactory().openSession();
		
		int shareId = -1;
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			String hql = String.format("select s.* FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id where s.media_id = %d and s.sharetype = %d and dsu.group_id = %d", obj.getMediaId(),obj.getSharetype(),groupId);
			List<DcpShare> shares =  session.createSQLQuery(hql).addEntity(DcpShare.class).list();
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
	public List<ShareDbInfo>  GetShares(int userId, int option) {
		
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		List<ShareDbInfo> ret = new LinkedList<ShareDbInfo>();
		try
		{
			tx = session.beginTransaction();
			String hql = "";
			switch(option){
			case 0://all
				hql = String.format("select s.*,dsu.*, IFNULL(ls.media_id,0) as media_id, IFNULL(ls.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point FROM dcp_share as s "
									+ " join dcp_share_user as dsu on s.share_id = dsu.share_id" 
									+ " join osf_team_members as tm on dsu.group_id = tm._project_id "
									+ " left join dcp_like_stat ls on ls.media_id = s.media_id"
									+ " WHERE tm._user_id = %d order by s.share_id desc",userId);
				 break;
			case 1://mine
				 hql = String.format("select s.*,dsu.* , IFNULL(ls.media_id,0) as media_id, IFNULL(ls.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point "
									+ " FROM dcp_share as s "
									+ " join dcp_share_user as dsu on s.share_id = dsu.share_id" 
									+ " join osf_projects as g on dsu.group_id = g._id" 
									+ " left join dcp_like_stat ls on ls.media_id = s.media_id"
									+ " WHERE g._entered_by_id = %d and g._category_id = 3  order by s.share_id desc", userId);
				break;
			case 2://friend
				hql = String.format("select s.*,dsu.* , IFNULL(ls.media_id,0) as media_id, IFNULL(ls.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point" 
									+ " FROM dcp_share as s "
									+ " join dcp_share_user as dsu on s.share_id = dsu.share_id" 
									+ " left join dcp_like_stat ls on ls.media_id = s.media_id"
									+ " WHERE dsu.group_id in (SELECT g._id FROM osf_projects as g join osf_team_members as tm where g._id = tm._project_id and tm._user_id = %d and g._category_id <> 1 and g._entered_by_id <> %d)  order by s.share_id desc", userId, userId);
				break;
			case 3://p
				hql = String.format("select s.*,dsu.* , IFNULL(ls.media_id,0) as media_id, IFNULL(ls.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point"
									+ " FROM dcp_share as s "
									+ " join dcp_share_user as dsu on s.share_id = dsu.share_id" 
									+ " join osf_projects as g on dsu.group_id = g._id "
									+ " left join dcp_like_stat ls on ls.media_id = s.media_id"
									+ " WHERE g._entered_by_id = %d and g._category_id = 3 order by s.share_id desc", userId);
				break;
			}
			List<?> objs = session.createSQLQuery(hql)
									.addEntity("s", DcpShare.class)
									.addEntity("dsu", DcpShareUser.class)
									.addEntity(DcpLikeStat.class)
									.list();
			if (objs != null && objs.size() > 0)
			{
				Iterator<?> it = objs.iterator();
				while(it.hasNext())
				{					
					Object[] ary = (Object[])it.next();
					ret.add(new ShareDbInfo((DcpShare)ary[0], (DcpShareUser)ary[1], 0, 0, ((DcpLikeStat)ary[2]).getTotalLikePoint()));
				}
			}
				
			tx.commit();
			
			return ret;
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

	/*@Override
	public List<KeyValuePair<DcpShare,DcpShareUser>> GetSharesOfGroup(int groupId) {
		
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		List<KeyValuePair<DcpShare,DcpShareUser>> ret = new LinkedList<KeyValuePair<DcpShare,DcpShareUser>>();
		try
		{
			tx = session.beginTransaction();
			
			String hql = String.format("select s.*,dsu.* FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id WHERE dsu.group_id = %d order by s.share_id desc", groupId);
			List<?> objs = session.createSQLQuery(hql)
									.addEntity("s", DcpShare.class)
									.addEntity("dsu", DcpShareUser.class)
									.list();
			if (objs != null && objs.size() > 0)
			{
				Iterator<?> it = objs.iterator();
				while(it.hasNext())
				{					
					Object[] ary = (Object[])it.next();
					
					ret.add(new KeyValuePair<DcpShare, DcpShareUser>((DcpShare)ary[0], (DcpShareUser)ary[1]));
				}
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
	public List<ShareDbInfo> GetSharesOfGroups(int userId, int groupId) {
		Session session = getSessionFactory().openSession();
		
		Transaction tx = null;
		List<ShareDbInfo> rets = new LinkedList<ShareDbInfo>();
		try
		{
			tx = session.beginTransaction();
	
			String hql = String.format("select s.*, su.*, IFNULL(ls.media_id,0) as media_id, IFNULL(ls.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point from dcp_share s join dcp_share_user su on s.share_id = su.share_id left join dcp_like_stat ls on s.media_id = ls.media_id where group_id = %s and (s.deleted = 0 or s.deleted is null) order by s.share_id desc", groupId);
			String hql2 = String.format("select s.share_id,sum(case c._activitytype when 1 then 1 else 0 end) ,sum(case c._activitytype when 0 then 1 else 0 end) from dcp_share s join dcp_share_user su on s.share_id = su.share_id left join osf_comments c on s.share_id = c._linked_id where su.group_id = %d group by s.share_id order by s.share_id desc", groupId);
			
			 List<?> counts = session.createSQLQuery(hql2).list();
			 List<Object[]> lsObjs = session.createSQLQuery(hql)
					 			.addEntity(DcpShare.class)
					 			.addEntity(DcpShareUser.class)
					 			.addEntity(DcpLikeStat.class).list();
			 
			 Iterator itObj = lsObjs.iterator();
			 Iterator itCount = counts.iterator();
			 
			 Object[] objCount = null;
			 Object[] lsObj = null;
			 
			 while(itObj.hasNext() && itCount.hasNext())
			 {
				 if (lsObj == null)
					 lsObj = (Object[])itObj.next();
				 if (objCount == null)
					 objCount = (Object[])itCount.next();

				 DcpShare share = (DcpShare)lsObj[0];
				 DcpShareUser user = (DcpShareUser)lsObj[1];
				 DcpLikeStat stat = (DcpLikeStat)lsObj[2];
				 
				 if (share.getShareId().intValue() == ((Integer)objCount[0]).intValue())
				 {
					 rets.add(new ShareDbInfo(
							 share,
							 user,
							 Integer.parseInt(objCount[2].toString()),
							 Integer.parseInt(objCount[1].toString()),
							 stat.getTotalLikePoint()));
					 lsObj = null;
					 objCount = null;
				 }
				 else if (share.getShareId().intValue() < ((Integer)objCount[0]).intValue())
				 {
					 rets.add(new ShareDbInfo(
							 share,
							 user,
							 0,
							 0,
							 stat.getTotalLikePoint()));
					 lsObj = null;
				 }
				 else
				 {
					 objCount = null;
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
	public List<ShareDbInfo> GetPublicShares(List<Integer> contentTypes,QueryOption.SearchType searchOption) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			List<ShareDbInfo> ret = new LinkedList<ShareDbInfo>();
			SQLQuery query;
			String hql;
			switch(searchOption){
				case hottest:
					hql = String.format("select s.*,dsu.*, IFNULL(dl.media_id,0) as media_id, IFNULL(dl.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id left join dcp_like_stat as dl on s.media_id = dl.media_id WHERE s.sharetype = 0 and (s.deleted is null or s.deleted = 0) and contenttype IN (:contentTypes) order by dl.total_like_point DESC, s.share_id DESC");
					break;
				case newest:
//					hql = String.format("select s.*,dsu.*, IFNULL(dl.media_id,0) as media_id, IFNULL(dl.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id left join dcp_like_stat as dl on s.media_id = dl.media_id WHERE s.sharetype = 0 and contenttype IN (:contentTypes) order by s.share_id DESC");
//					break;
				default:
					hql = String.format("select s.*,dsu.*, IFNULL(dl.media_id,0) as media_id, IFNULL(dl.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id left join dcp_like_stat as dl on s.media_id = dl.media_id WHERE s.sharetype = 0 and (s.deleted is null or s.deleted = 0) and contenttype IN (:contentTypes) order by s.share_id DESC");
					break;
			}
			if (contentTypes != null && contentTypes.size() > 0)
			{
				//hql = String.format("select s.*,dsu.* FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id WHERE s.sharetype = 0 and contenttype IN (:contentTypes) order by s.share_id DESC");
				query = session.createSQLQuery(hql);
				query.setParameterList("contentTypes", contentTypes);
			}
			else
			{
				hql = String.format("select s.*,dsu.*, IFNULL(dl.media_id,0) as media_id, IFNULL(dl.total_like_point,0) as total_like_point, IFNULL(total_dislike_point,0) as total_dislike_point FROM dcp_share as s join dcp_share_user as dsu on s.share_id = dsu.share_id left join dcp_like_stat as dl on s.media_id = dl.media_id WHERE s.sharetype = 0 and (s.deleted is null or s.deleted = 0) order by s.share_id DESC");
				query = session.createSQLQuery(hql);
			}
			
			List<?> objs = query.addEntity(DcpShare.class)
								.addEntity(DcpShareUser.class)
								.addEntity(DcpLikeStat.class)
								.list();
			if (objs != null && objs.size() > 0)
			{
				Iterator<?> it = objs.iterator();
				while(it.hasNext())
				{					
					Object[] ary = (Object[])it.next();
					ret.add(new ShareDbInfo((DcpShare)ary[0], (DcpShareUser)ary[1], 0, 0, ((DcpLikeStat)ary[2]).getTotalLikePoint()));
				}
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
			String hql = String.format("UPDATE DcpShare s SET s.deleted = 1 where s.shareId = %d", shareId);
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



	@Override
	public int CreateShareUser(ShareType shareType, DcpShareUser shareUser) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();

			switch(shareType){
			case all:
			case friend:				
			case group:
				List<?> objs = session.createCriteria(DcpShareUser.class)
										.add(Restrictions.eq("userId", shareUser.getUserId()))
										.add(Restrictions.eq("friendId", shareUser.getFriendId()))
										.add(Restrictions.eq("groupId", shareUser.getGroupId()))
									.createCriteria("dcpShare")
										.add(Restrictions.eq("shareId", shareUser.getDcpShare().getShareId()))
										.list();
				if(objs != null && objs.size() > 0){
					shareUser = (DcpShareUser)objs.get(0);
					shareUser.setModifyDate(new Date());
					session.update(shareUser);
				}else{
					session.save(shareUser);
				}
										
				return shareUser.getId();	
			case myFirends:
				SQLQuery query = session.createSQLQuery("{CALL f_share_user(?,?,?)}");
				query.setInteger(0, shareUser.getDcpShare().getShareId());
				query.setInteger(1, shareUser.getUserId());
				query.setInteger(2, shareUser.getGroupId());
				List<Object> ret = query.list();
				if(ret != null){
					getSessionFactory().getCache().evictEntityRegion(DcpShareUser.class);
					return Integer.parseInt(ret.get(0).toString());
				}else{
					return -1;
				}				
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
			tx.commit();
			session.close();
		}
		
		return 0;
	}

	@Override
	public DcpShareUser GetDcpShareUser(int shareId) {
		Session session = getSessionFactory().openSession();
		
		
		Transaction tx = null;
		DcpShareUser shareUser = null;
		try
		{
			tx = session.beginTransaction();
			
			List<?> objs = session.createCriteria(DcpShareUser.class)
									.createCriteria("dcpShare")
										.add(Restrictions.eq("shareId", shareId)).list();
			if(objs != null && objs.size() > 0){
				shareUser = (DcpShareUser)objs.get(0);
			}
			
			return shareUser;
		
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
