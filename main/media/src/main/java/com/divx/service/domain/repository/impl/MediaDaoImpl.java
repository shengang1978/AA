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
import com.divx.service.model.MediaBaseType.eFileType;

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
	public List<KeyValuePair<DcpMedia,DcpDivxassets>> GetMyMedias(List<Integer> contentType, int userId, int startPos, int endPos) {
		Session ss = getSession();
		try
		{
			DcpMedia obj = null;
			DcpDivxassets asset = null;
			KeyValuePair<DcpMedia,DcpDivxassets> ret= null;
			Transaction trans = ss.beginTransaction();
			//String hql = String.format("FROM DcpMedia m, DcpDivxassets d WHERE m.mediaId = d.mediaId and m.userId = %d and m.deleted = 0 order by m.mediaId desc group by m.mediaId", userId);
			String hql = "";
			if (contentType == null || contentType.size() == 0)
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.user_id = %d and m.deleted = 0 and m.parent_id = 0 group by m.media_id order by m.media_id desc", userId);
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
				hql = String.format("select m.*, d.* FROM dcp_media m left join dcp_divxassets d on m.media_id = d.media_id where m.user_id = %d and m.deleted = 0 and m.parent_id = 0 and %s group by m.media_id order by m.media_id desc", 
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

}
