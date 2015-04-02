package com.divx.service.domain.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.model.*;
import com.divx.service.domain.repository.*;
import com.divx.service.model.*;

@Repository
public class MediaDaoImpl extends BaseDao  implements MediaDao {

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
	public List<KeyValuePair<DcpMedia,DcpDivxassets>> GetMyMedias(int userId, int startPos, int endPos) {
		Session ss = getSession();
		try
		{
			DcpMedia obj = null;
			DcpDivxassets asset = null;
			KeyValuePair<DcpMedia,DcpDivxassets> ret= null;
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpMedia m, DcpDivxassets d WHERE m.mediaId = d.mediaId and m.userId = %d and m.deleted = 0 order by m.mediaId desc", userId);
			int maxRestult = endPos - startPos;
			List<?> objs = ss.createQuery(hql).setFirstResult(startPos).setMaxResults(maxRestult).list();
			
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
	public List<DcpMedia> GetPublicMedias(int startPos, int endPos)
	{
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			DcpMedia obj = null;
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpMedia m WHERE m.deleted = 0 and m.status = 6");
			int maxRestult = endPos - startPos;
			return ss.createQuery(hql).setFirstResult(startPos).setMaxResults(maxRestult).list();
			
//			ArrayList<DcpMedia> mediaList = new ArrayList<>();
//			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
//			{
//				obj = (DcpMedia)it.next();
//				mediaList.add(obj);
//			}
//			
//			trans.commit();
//			return mediaList;
		}
		finally
		{
			if (trans != null)
				trans.commit();
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
	public DcpDivxassets GetDivxAsset(int mediaId, int videoformat) {
		Session ss = getSession();
		
		try
		{
			DcpDivxassets asset = null;
			Transaction trans = ss.beginTransaction();
			//String hql = String.format("FROM DcpDivxassets m WHERE m.mediaId = %d and m.videoformat = %d and m.status = 0 order by m.assetsId desc", mediaId, videoformat);
			String hql = String.format("FROM DcpDivxassets m WHERE m.mediaId = %d and m.status = 0 order by m.assetsId desc", mediaId);
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpDivxassets obj = (DcpDivxassets)it.next();
				if (obj.getVideoformat() == videoformat)
				{//Prior to get the request video format
					asset = obj;
					break;
				}
				else
				{
					// If not find the same format, get the latest.
					if (asset == null)
						asset = obj;
				}
			}
			
			trans.commit();
			return asset;
		}
		finally
		{
			ss.close();
		}
	}

}
