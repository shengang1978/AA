package com.divx.service.domain.repository.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.Util;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.model.*;
import com.divx.service.domain.repository.*;
import com.divx.service.model.*;
import com.divx.service.model.DcpBaseType.eAppType;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.MediaBaseType.eFileType;
import com.divx.service.model.edu.ScoreStat;

@Repository
public class MediaDaoImpl extends BaseDao  implements MediaDao {
	private static final Logger log = Logger.getLogger(MediaDaoImpl.class);
	
	@Override
	public int CreateMedia(DcpMedia obj) {
		
		Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.save(obj);
			
			trans.commit();
			
			return obj.getMediaId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int CreateKeywords(List<DcpMediaKeywords> words) {

		if (words != null && words.size() > 0)
		{
			Session ss = getSession();
			
			Transaction trans = null;
			
			try
			{
				trans = ss.beginTransaction();
				for(int i = 0; i < words.size(); ++i)
				{
					ss.save(words.get(i));
				}
				trans.commit();
				
				return words.size();
			}
			catch(Exception e)
			{
				if (trans != null)
					trans.rollback();
				throw e;
			}
			finally
			{
				ss.close();
			}
		}
		
		return 0;
	}
	
	private SessionFactory sessionFactory;
	
	private Session getSession() {
		if (sessionFactory == null) {
			//sessionFactory = new Configuration().configure().buildSessionFactory(); //getFactory();//
			sessionFactory = getSessionFactory();
		}

		return sessionFactory.openSession();
	}

	@Override
	public int UpdateMedia(DcpMedia obj) {

		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.update(obj);
			
			trans.commit();
		
			return obj.getMediaId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int DeleteMedia(DcpMedia obj) {
		obj.setDeleted(true);
		obj.setDatemodified(new Date());
		
		return UpdateMedia(obj);
	}

	@Override
	public int UpdateUploadInfo(DcpOriginalasset obj) {
		Session ss = getSession();
		Transaction trans = null;
		try
		{			
			trans = ss.beginTransaction();
			if (obj.getId() != null && obj.getId() > 0)
			{
				ss.update(obj);
			}
			else
			{
				ss.save(obj);
			}
			
			trans.commit();
			
			return obj.getId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpOriginalasset GetUploadInfo(int mediaId) {
		DcpOriginalasset asset = null;
		Session ss = getSession();
		try
		{
			
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpOriginalasset a WHERE a.mediaId = %d and a.deleted = 0 order by a.id desc", mediaId);
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				asset = (DcpOriginalasset)it.next();
				break;
			}
			
			trans.commit();			
		}
		catch(Exception e)
		{
		}
		finally
		{
			ss.close();
		}
		
		return asset;
	}	

	@Override
	public DcpOriginalasset GetOriginalasset(int id) {
		DcpOriginalasset asset = null;
		Session ss = getSession();
		try
		{
			
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpOriginalasset a WHERE a.id = %d", id);
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				asset = (DcpOriginalasset)it.next();
				break;
			}
			
			trans.commit();			
		}
		finally
		{
			ss.close();
		}
		return asset;
	}

	@Override
	public DcpMedia GetMedia(int mediaId) {
		Session ss = getSession();
		try
		{	
			DcpMedia obj = null;
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpMedia m WHERE m.mediaId = %d and m.deleted = 0", mediaId);
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				obj = (DcpMedia)it.next();
				break;
			}
			
			trans.commit();
			return obj;
		}
		finally
		{
			ss.close();
		}
	}
	
	@Override
	public DcpMedia GetAncestorMedia(int mediaId)
	{
		DcpMedia m = GetMedia(mediaId);
		if (m == null || m.getParentId() == null || m.getParentId() == 0)
			return m;
		
		return GetAncestorMedia(m.getParentId());
	}
	@Override
	public List<KeyValuePair<DcpMedia,DcpDivxassets>> GetMyMedias(List<Integer> contentType, int userId, int startPos, int endPos) {
		Session ss = getSession();
		try
		{
			KeyValuePair<DcpMedia,DcpDivxassets> ret= null;
			Transaction trans = ss.beginTransaction();
			//String hql = String.format("FROM DcpMedia m, DcpDivxassets d WHERE m.mediaId = d.mediaId and m.userId = %d and m.deleted = 0 order by m.mediaId desc group by m.mediaId", userId);
			String hql = "";
			if (contentType == null || contentType.size() == 0)
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.user_id = %d and m.deleted = 0 group by m.media_id order by m.media_id desc", userId);
			else if(contentType.get(0) == MediaBaseType.eContentType.EduStory.ordinal()){
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.user_id = %d and m.deleted = 0 and  m.contenttype = %d order by m.media_id desc", 
						userId,MediaBaseType.eContentType.EduStory.ordinal());
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				sb.append(" m.contenttype in (");
				for(Integer ct: contentType)
				{
					sb.append(ct);
					sb.append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.user_id = %d and m.deleted = 0 and %s group by m.media_id order by m.media_id desc", 
										userId,
										sb.toString());
			}
			int maxRestult = endPos - startPos;
			//List<?> objs = ss.createQuery(hql).setFirstResult(startPos).setMaxResults(maxRestult).list();
			List<?> objs = ss.createSQLQuery(hql)
							.addEntity("m", DcpMedia.class)
							.addEntity("d", DcpDivxassets.class)
							.setFirstResult(startPos).setMaxResults(maxRestult).list();
			
			List<KeyValuePair<DcpMedia,DcpDivxassets>> mediaList = new LinkedList<>();
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				Object[] ary = (Object[])it.next();
				ret = new KeyValuePair<DcpMedia,DcpDivxassets>((DcpMedia)ary[0],(DcpDivxassets)ary[1]);
				mediaList.add(ret);
			}
			
			trans.commit();
			return mediaList;
		}
		finally
		{
			ss.close();
		}
	}
	
	@Override
	public List<KeyValuePair<DcpMedia,DcpDivxassets>> GetPublicMedias(List<Integer> contentType)
	{
		Session ss = getSession();
		try
		{
			KeyValuePair<DcpMedia,DcpDivxassets> ret= null;
			Transaction trans = ss.beginTransaction();
			String hql = "";
			if (contentType == null || contentType.size() == 0)
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.status = 6 and m.ispublic = 1 and m.deleted = 0 group by m.media_id order by m.weight desc, m.media_id desc");
			else if(contentType.get(0) == MediaBaseType.eContentType.EduStory.ordinal()){
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.status = 6 and m.ispublic = 1 and m.deleted = 0 and  m.contenttype = %d order by m.weight desc, m.media_id desc", 
						MediaBaseType.eContentType.EduStory.ordinal());
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				sb.append(" m.contenttype in (");
				for(Integer ct: contentType)
				{
					sb.append(ct);
					sb.append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.status = 6 and m.ispublic = 1 and m.deleted = 0 and %s group by m.media_id order by m.weight desc, m.media_id desc", 
										sb.toString());
			}
			
			List<?> objs = ss.createSQLQuery(hql)
							.addEntity("m", DcpMedia.class)
							.addEntity("d", DcpDivxassets.class)
							.list();
			
			List<KeyValuePair<DcpMedia,DcpDivxassets>> mediaList = new LinkedList<>();
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				Object[] ary = (Object[])it.next();
				ret = new KeyValuePair<DcpMedia,DcpDivxassets>((DcpMedia)ary[0],(DcpDivxassets)ary[1]);
				mediaList.add(ret);
			}
			
			trans.commit();
			return mediaList;
		}
		finally
		{
			ss.close();
		}
	}	

	@Override
	public int CreateDivxAsset(DcpDivxassets obj) {
		Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			if (obj.getAssetsId() != null && obj.getAssetsId() > 0)
			{
				ss.update(obj);
			}
			else
			{
				ss.save(obj);
			}
			trans.commit();
			return obj.getAssetsId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public List<DcpDivxassets> GetDivxAsset(int mediaId, MediaBaseType.eFileType fileType) {
		Session ss = getSession();
		
		try
		{
			List<DcpDivxassets> assets = new LinkedList<DcpDivxassets>();
			Transaction trans = ss.beginTransaction();
			//String hql = String.format("FROM DcpDivxassets m WHERE m.mediaId = %d and m.videoformat = %d and m.status = 0 order by m.assetsId desc", mediaId, videoformat);
			String hql = "";
			if (fileType == null || fileType == eFileType.Auto)
			{
				hql = String.format("FROM DcpDivxassets m WHERE m.mediaId = %d and m.status = 0 order by m.assetsId desc", mediaId);
			}
			else
			{
				hql = String.format("FROM DcpDivxassets m WHERE m.mediaId = %d and m.status = 0 and m.videoformat = %d order by m.assetsId desc", mediaId, fileType.ordinal());
			}
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpDivxassets obj = (DcpDivxassets)it.next();
				boolean needAdd = true;
				for(DcpDivxassets asset: assets)
				{
					if (obj.getVideoformat() == asset.getVideoformat())
					{
						needAdd = false;
						break;
					}
				}
				if (needAdd)
				{
					assets.add(obj);
				}
			}
			
			trans.commit();
			return assets;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int createMediaSign(DcpMediaSign mediaSign) {
		Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			ss.saveOrUpdate(mediaSign);
			
			trans.commit();
			return mediaSign.getId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpMediaSign GetMediaSign(String sign) {
		Session ss = getSession();
		DcpMediaSign mediaSign = null;
		try
		{
		
			Transaction trans = ss.beginTransaction();
			
			List<?> objs = ss.createCriteria(DcpMediaSign.class).add(Restrictions.eq("sign", sign)).createCriteria("dcpMedia").list();
			if(objs != null && objs.size() > 0){
				mediaSign = (DcpMediaSign)objs.get(0);
			}
			trans.commit();
		}catch(Exception ex){
			
		}
		finally
		{
			
			ss.close();
		}
		return mediaSign;
	}


	@Override
	public List<DcpScore> GetScores(int userId, int mediaId) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			List<DcpScore> objs = ss.createCriteria(DcpScore.class).
								add(Restrictions.eq("userId", userId)).
								createCriteria("dcpLesson")
								.createCriteria("dcpMedia")
								.add(Restrictions.eq("mediaId", mediaId)).list();
			trans.commit();
			
			return objs;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public int SetScore(DcpScore score) {
		Session ss  = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			String hql = "CALL f_setScore(?,?,?,?,?,?,?,?,?,?)";
			Query query = ss.createSQLQuery(hql);
			query.setInteger(0, score.getScoreId() == null ? 0 : score.getScoreId());
			query.setInteger(1, score.getUserId());
			query.setInteger(2, score.getDcpLesson().getLessonId());
			query.setInteger(3, score.getListen());
			query.setInteger(4, score.getRead());
			query.setInteger(5, score.getRecord());
			query.setInteger(6, score.getListenduration());
			query.setInteger(7, score.getReadduration());
			query.setInteger(8, score.getRecordduration());
			query.setInteger(9, score.getReadcount());
			
			List<Object> lstRet = query.list();
			
			if (lstRet != null && lstRet.size() > 0)
			{
				//clear the hibernate second level cache.
				getSessionFactory().getCache().evictEntityRegion(DcpScore.class);
				getSessionFactory().getCache().evictEntityRegion(DcpScorestat.class);
				getSessionFactory().getCache().evictEntityRegion(DcpTotalstat.class);
				
				Object[] objs = (Object[])lstRet.get(0);
				int code = Integer.parseInt(objs[0].toString());
				String message = objs[1].toString();
				if (code < 0)
				{
					//Error
					log.error(String.format("SetScore(%s) = (%d,%s)", Util.ObjectToJson(score), code, message));
				}
				return code;
			}
			trans.commit();
			
			return 0;
		}
		catch(Exception ex)
		{
			if (trans != null)
				trans.rollback();
			
			throw ex;
		}
		finally
		{	
			ss.close();
		}
	}

	@Override
	public DcpScore GetScore(int scoreId) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			DcpScore obj = (DcpScore)ss.createCriteria(DcpScore.class).
								add(Restrictions.eq("scoreId", scoreId)).
								createCriteria("dcpLesson").uniqueResult();
			trans.commit();
			
			return obj;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public DcpScore GetScore(int userId, int lessonId) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			DcpScore obj = (DcpScore)ss.createCriteria(DcpScore.class).
								add(Restrictions.eq("userId", userId)).
								createCriteria("dcpLesson")
								.add(Restrictions.eq("lessonId", lessonId)).uniqueResult();
			trans.commit();
			
			return obj;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public DcpLesson GetLesson(int lessonId) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			Criteria lesson = ss.createCriteria(DcpLesson.class);
			lesson.add(Restrictions.eq("lessonId", lessonId));
			lesson.createCriteria("dcpMedia");			
			lesson.createCriteria("dcpLessonAssets").addOrder(Order.asc("assettype"));
			DcpLesson obj = (DcpLesson)lesson.uniqueResult();
			
			if (obj != null && obj.getDcpLessonAssets().size() > 0)
			{
				for(Object o: obj.getDcpLessonAssets())
				{
					DcpLessonAsset dla = (DcpLessonAsset)o;
					dla.getContent();
				}
			}
			
			trans.commit();
			return obj;
		}
		finally
		{	
			ss.close();
		}
	}
	

	@Override
	public DcpLesson GetLesson(int mediaId, String category, int number) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			DcpLesson obj = (DcpLesson)ss.createCriteria(DcpLesson.class).
								add(Restrictions.eq("category", category)).
								add(Restrictions.eq("number", number)).
								createCriteria("dcpMedia")
								.add(Restrictions.eq("mediaId", mediaId)).uniqueResult();
			
			if (obj == null)
			{
				DcpMedia m = (DcpMedia)ss.createCriteria(DcpMedia.class)
						.add(Restrictions.eq("mediaId", mediaId)).uniqueResult();
		
				if (m != null && m.getParentId() > 0)
				{
					trans.commit();
					return GetLesson(m.getParentId(), category, number);
				}
			}
			trans.commit();
			
			return obj;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public List<DcpScore> GetScoresByUser(int userId) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			List<DcpScore> objs = ss.createCriteria(DcpScore.class).
								add(Restrictions.eq("userId", userId)).
								createCriteria("dcpLesson").list();
			trans.commit();
			
			return objs;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public List<DcpScorestat> GetStats(int userId, ScoreStat.eStatDuration st) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date now = new Date();
			Date dtBegin = now;
			Date dtEnd = new Date(now.getTime() + 60*60*24*1000);
			switch(st)
			{
			case Today:
			default:
				try {
					dtBegin = sdf.parse(sdf.format(now));
					dtEnd = sdf.parse(sdf.format(dtEnd));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			List<DcpScorestat> objs = ss.createCriteria(DcpScorestat.class).
									add(Restrictions.eq("userId", userId)).
									add(Restrictions.between("datecreated", dtBegin, dtEnd)).
									createCriteria("dcpScore").list();
			trans.commit();
			
			return objs;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public DcpMediaSign GetMediaSign(int mediaId) {
		Session ss = getSession();
		Transaction trans = null;
		DcpMediaSign mediaSign = null;
		try
		{
			
			trans = ss.beginTransaction();
			
			List<?> objs = ss.createCriteria(DcpMediaSign.class)
								.createCriteria("dcpMedia")
									.add(Restrictions.eq("mediaId", mediaId)).list();
			if(objs != null && objs.size() > 0){
				mediaSign = (DcpMediaSign)objs.get(0);
			}
			trans.commit();

		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
		return mediaSign;
	}

	@Override
	public DcpTotalstat GetTotalStat(int userId) {
		Session ss = getSession();
		try
		{
			Transaction trans = ss.beginTransaction();
			
			List<DcpTotalstat> objs = ss.createCriteria(DcpTotalstat.class).
										add(Restrictions.eq("userId", userId)).list();
			trans.commit();
			
			if (objs != null && objs.size() > 0)
				return objs.get(0);
			
			return null;
		}
		finally
		{
			
			ss.close();
		}
	}

	@Override
	public List<DcpLesson> GetAllLessons()
	{
		Session ss = getSession();
		
		try
		{
			Transaction trans = ss.beginTransaction();
			
			Criteria c = ss.createCriteria(DcpLesson.class);
			c.createCriteria("dcpMedia")
				.add(Restrictions.in("contenttype", new Object[]{eContentType.EduBook.ordinal(), eContentType.EduBookURL.ordinal()}))
				.add(Restrictions.eq("parentId", 0))
				.add(Restrictions.eq("deleted", false));

			c.createCriteria("dcpLessonAssets");
			List<DcpLesson> lessons = (List<DcpLesson>)c.list();
			
			trans.commit();
			return lessons;
		}
		finally
		{
			ss.close();
		}
	}
	
	@Override
	public List<DcpLesson> GetLessons(int mediaId) {
		Session ss = getSession();
		
		try
		{
			Transaction trans = ss.beginTransaction();

			DcpMedia m = (DcpMedia)ss.createCriteria(DcpMedia.class)
					.add(Restrictions.eq("mediaId", mediaId))
					.add(Restrictions.eq("deleted", false)).uniqueResult();
			
			List<DcpLesson> lessons = null;
			if (m != null)
			{
				switch(eContentType.values()[m.getContenttype()])
				{
				case EduBook:
				case EduBookURL:
				{
					int fixedMediaId = m.getParentId() > 0 ? m.getParentId() : mediaId;

					Criteria c = ss.createCriteria(DcpLesson.class)
									.addOrder(Order.asc("category"))
									.addOrder(Order.asc("number"));
					c.createCriteria("dcpMedia")
						.add(Restrictions.eq("mediaId", fixedMediaId));
					c.createCriteria("dcpLessonAssets")
						.addOrder(Order.asc("assettype"));

					lessons = c.list();
					break;
				}
				case EduStory:
					if (m.getLessonId() > 0)
					{
						Criteria c = ss.createCriteria(DcpLesson.class);
						c.add(Restrictions.eq("lessonId", m.getLessonId()));
							//.addOrder(Order.asc("category"))
							//.addOrder(Order.asc("number"));;
						c.createCriteria("dcpMedia");
						c.createCriteria("dcpLessonAssets");
						lessons = c.list();
					}
					break;
				}
			}
			
			if (lessons != null && lessons.size() > 0)
			{
				Set<Integer> lessonIds = new HashSet<Integer>();
				List<DcpLesson> ret = new LinkedList<DcpLesson>();
				for(DcpLesson l: lessons)
				{
					if (!lessonIds.contains(l.getLessonId()))
					{
						for(Object a: l.getDcpLessonAssets())
						{
							DcpLessonAsset o = (DcpLessonAsset)a;
							o.getLessonassetId();
						}
						ret.add(l);
						lessonIds.add(l.getLessonId());
					}
				}
				return ret;
			}
			
			trans.commit();
			return lessons;
		}
		finally
		{
			ss.close();
		}
	}
	

	@Override
	public List<KeyValuePair<DcpLesson, DcpLessonAsset>> GetLessonsCovers(int mediaId) {
		Session ss = getSession();
		
		try
		{
			Transaction trans = ss.beginTransaction();

			String sql = String.format("select l.*, m.*, a.* from dcp_lesson l join dcp_media m on l.media_id = m.media_id left join dcp_lesson_asset a on (l.lesson_id = a.lesson_id) and (a.assettype = 9) where m.media_id = %d order by category, number", mediaId);
			
			List<Object[]> objList = ss.createSQLQuery(sql)
				.addEntity(DcpLesson.class)
				.addEntity(DcpMedia.class)
				.addEntity(DcpLessonAsset.class)
				.list();

			List<KeyValuePair<DcpLesson, DcpLessonAsset>> lessons = new LinkedList<KeyValuePair<DcpLesson, DcpLessonAsset>>();
			
			for(Object[] objs: objList)
			{
				DcpLesson l = (DcpLesson)objs[0];
				DcpMedia m = (DcpMedia)objs[1];
				DcpLessonAsset a = (DcpLessonAsset)objs[2];

				lessons.add(new KeyValuePair<DcpLesson, DcpLessonAsset>(l, a));
			}

			trans.commit();
			return lessons;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int SetLessons(int mediaId, List<DcpLesson> lessons) {
		Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			int nRet = -1;
			trans = ss.beginTransaction();
			
			DcpMedia media = (DcpMedia)ss.createCriteria(DcpMedia.class)
				.add(Restrictions.eq("mediaId", mediaId)).uniqueResult();
			
	
			
			if (media != null)
			{
				int index = 0;
				for(DcpLesson obj: lessons)
				{
					List<DcpLesson> dbLessons = ss.createCriteria(DcpLesson.class)
							.add(Restrictions.eq("category", obj.getCategory()))
							.add(Restrictions.eq("number", obj.getNumber()))
							.createCriteria("dcpMedia")
							.add(Restrictions.eq("mediaId", mediaId))
							.list();
					if(dbLessons != null & dbLessons.size() > 0){
						DcpLesson dl = dbLessons.get(0);
						dl.setCategoryTitle(obj.getCategoryTitle());
						dl.setTitle(obj.getTitle());
						dl.setConfig(obj.getConfig());
						dl.setSnapshoturl(obj.getSnapshoturl());
						dl.setDatemodified(obj.getDatemodified());
						obj.setLessonId(dl.getLessonId());
						
						ss.update(dl);
					}else{
						obj.setDcpMedia(media);
						ss.save(obj);
					}
					if (++index > 50)
					{
						ss.flush();
						ss.clear();
						index = 0;
					}
				}
				
				nRet = lessons.size();
			}
			trans.commit();
			return nRet;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	
	@Override
	public int DeleteLesson(int userId, int lessonId) {
		Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			int nRet = ResponseCode.ERROR_NOT_FOUND;
			DcpLesson obj = (DcpLesson)ss.createCriteria(DcpLesson.class)
				.add(Restrictions.eq("lessonId", lessonId))
				.createCriteria("dcpMedia").uniqueResult();
			
			if (obj != null)
			{
				if (userId > 0 && obj.getDcpMedia().getUserId() != userId)
				{
					nRet = ResponseCode.ERROR_NO_PERMISSION;
				}
				else
				{
					ss.delete(obj);
					nRet = ResponseCode.SUCCESS;
				}
			}
			trans.commit();
			return nRet;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int SetLesson(DcpLesson obj) {
		Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();

			obj.setDatemodified(new Date());
			ss.saveOrUpdate(obj);
			trans.commit();
			return obj.getLessonId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	
	@Override
	public int UpdateDownloadCount(DcpDownload download) {
		Session ss = getSession();
		
		Transaction trans = null;
	
		try
		{
			trans = ss.beginTransaction();

			ss.saveOrUpdate(download);
			
			trans.commit();
			return download.getId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public List<DcpDownload> GetAllDownloadCount() {
		Session ss = getSession();
		
		Transaction trans = null;
		List<DcpDownload> downloads = null;
		try
		{
			trans = ss.beginTransaction();

			downloads = ss.createCriteria(DcpDownload.class).
							createCriteria("dcpMedia").addOrder(Order.desc("mediaId")).list();
			trans.commit();
			return downloads;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpDownload GetDownloadCount(int mediaId) {
		Session ss = getSession();
		
		Transaction trans = null;
		
		try
		{
			trans = ss.beginTransaction();

			DcpDownload download = (DcpDownload)ss.createCriteria(DcpDownload.class)
													.createCriteria("dcpMedia")
													.add(Restrictions.eq("mediaId", mediaId)).uniqueResult();
			trans.commit();
			return download;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int CreateDcpLessonAsset(List<DcpLessonAsset> lessonAsset) {
	Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			int nRet = -1;
			trans = ss.beginTransaction();
			int index = 0;
			
			for(DcpLessonAsset obj: lessonAsset)
			{		
				
			/*	List<DcpLessonAsset> asset = ss.createCriteria(DcpLessonAsset.class)
											.add(Restrictions.ilike("content", obj.getContent().substring(obj.getContent().indexOf("/")),MatchMode.END))
											.createCriteria("dcpLesson")
											.add(Restrictions.eq("lessonId", obj.getDcpLesson().getLessonId()))
											.list();	
			if(asset != null && asset.size() > 0){
				DcpLessonAsset assetObj = asset.get(0);
				assetObj.setContent(obj.getContent());
				assetObj.setDatemodified(new Date());
				ss.update(assetObj);
			}else{
				ss.save(obj);	
			}*/
				ss.save(obj);	
			
				
				if (index++ > 50)
				{
					ss.flush();
					ss.clear();
					index = 0;
				}
			}
			
			nRet = lessonAsset.size();
			
			trans.commit();
			return nRet;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	@Override
	public int DeleteDcpLessonAsset(List<DcpLessonAsset> lessonAsset) {
	Session ss = getSession();
		
		Transaction trans = null;
		try
		{
			int nRet = -1;
			trans = ss.beginTransaction();
			
			HashSet<Integer> lessonIds = new HashSet<>();
			for(DcpLessonAsset obj: lessonAsset)
			{		
				lessonIds.add(obj.getDcpLesson().getLessonId());	
			}
			StringBuffer sb = new StringBuffer();
			for(Integer lessonId : lessonIds){
				sb.append(lessonId + ",");
			}
			
			String sql = String.format("delete from dcp_lesson_asset Where lesson_id in (%s)", sb.substring(0, sb.lastIndexOf(",")));
			System.out.println(sql);
			
			
			nRet =ss.createSQLQuery(sql).executeUpdate();
			
			trans.commit();
			return nRet;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public Integer IsBookInMyLibrary(int userId, int mediaId) {
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			List<?> objs = ss.createCriteria(DcpMedia.class)
					.add(Restrictions.and(Restrictions.eq("userId", userId), 
							Restrictions.and(Restrictions.eq("parentId", mediaId), Restrictions.eq("deleted", false)))).list();
			
			if (objs != null && objs.size() > 0)
			{
				return ((DcpMedia)objs.get(0)).getMediaId();
			}
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
		
		return 0;
	}
	@Override
	public int AddStoryPlayHit(int mediaId) {
		Session ss  = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			String hql = "CALL f_addStoryPlay(?)";
			Query query = ss.createSQLQuery(hql);
			query.setInteger(0, mediaId);
			
			List<Object> lstRet = query.list();
			
			if (lstRet != null && lstRet.size() > 0)
			{
				//clear the hibernate second level cache.
				getSessionFactory().getCache().evictEntityRegion(DcpStoryplay.class);
				getSessionFactory().getCache().evictEntityRegion(DcpStoryplayTotalstat.class);
				
				Object[] objs = (Object[])lstRet.get(0);
				int code = Integer.parseInt(objs[0].toString());
				String message = objs[1].toString();
				if (code < 0)
				{
					log.error(String.format("dao AddStoryPlayHit(%d) = (%d,%s)", mediaId, code, message));
				}
				return code;
			}
			trans.commit();
			
			return 0;
		}
		catch(Exception ex)
		{
			if (trans != null)
				trans.rollback();
			
			throw ex;
		}
		finally
		{	
			ss.close();
		}
	}

	@Override
	public List<DcpStoryplayTotalstat> GetStoryPlayTotalstat(int nStartPos,
			int nEndPos, boolean bDesc) {
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			List<DcpStoryplayTotalstat> objs = null;
			
			Criteria c = ss.createCriteria(DcpStoryplayTotalstat.class);
			
			if (bDesc)
				c.addOrder(Order.desc("playcount"));
			else
				c.addOrder(Order.asc("playcount"));
			
			c.createCriteria("dcpMedia");
			c.createCriteria("dcpLesson");
			objs = c.setFirstResult(nStartPos).setMaxResults(nEndPos - nStartPos + 1).list();
			
			
			return objs;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpStoryplayTotalstat GetStoryPlayStat(int mediaId) {
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			
			Criteria c = ss.createCriteria(DcpStoryplayTotalstat.class);
			
			c.createCriteria("dcpMedia")
				.add(Restrictions.eq("mediaId", mediaId));
			c.createCriteria("dcpLesson");
			DcpStoryplayTotalstat obj = (DcpStoryplayTotalstat)c.uniqueResult();
			
			
			return obj;
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	@Override
	public List<KeyValuePair<DcpMedia, DcpDivxassets>> GetAllMedias(
			List<Integer> contentType) {
		Session ss = getSession();
		try
		{
			KeyValuePair<DcpMedia,DcpDivxassets> ret= null;
			Transaction trans = ss.beginTransaction();
			String hql = "";
			if (contentType == null || contentType.size() == 0)
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.status = 6 and m.ispublic = 1 and m.deleted = 0 group by m.media_id order by m.weight desc, m.media_id desc");
			else if(contentType.get(0) == MediaBaseType.eContentType.EduStory.ordinal()){
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.status = 6 and m.ispublic = 1 and m.deleted = 0 and  m.contenttype = %d order by m.weight desc, m.media_id desc", 
						MediaBaseType.eContentType.EduStory.ordinal());
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				sb.append(" m.contenttype in (");
				for(Integer ct: contentType)
				{
					sb.append(ct);
					sb.append(",");
				}
				sb.deleteCharAt(sb.length() - 1);
				sb.append(") ");
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.status = 6 and m.parent_id = 0 and m.deleted = 0 and %s group by m.media_id order by m.weight desc, m.media_id desc", 
										sb.toString());
			}
			
			List<?> objs = ss.createSQLQuery(hql)
							.addEntity("m", DcpMedia.class)
							.addEntity("d", DcpDivxassets.class)
							.list();
			
			List<KeyValuePair<DcpMedia,DcpDivxassets>> mediaList = new LinkedList<>();
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				Object[] ary = (Object[])it.next();
				ret = new KeyValuePair<DcpMedia,DcpDivxassets>((DcpMedia)ary[0],(DcpDivxassets)ary[1]);
				mediaList.add(ret);
			}
			
			trans.commit();
			return mediaList;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int SetMediaIsPublic(boolean isPublic,
			List<String> mediaIds) {
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			StringBuilder sb = new StringBuilder();
			sb.append(" m.mediaId IN (");
			for(String mediaId: mediaIds)
			{
				sb.append(mediaId);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(") ");
			String hql = String.format("UPDATE DcpMedia m SET m.ispublic = %d WHERE %s", isPublic ? 1 : 0,sb.toString());
			return ss.createQuery(hql).executeUpdate();
			
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			trans.commit();
			ss.close();
		}
		
		
	}

}
