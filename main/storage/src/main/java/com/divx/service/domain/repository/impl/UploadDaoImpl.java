package com.divx.service.domain.repository.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.MediaServiceHelper.eUploadStatus;
import com.divx.service.domain.model.DcpOriginalasset;
import com.divx.service.domain.repository.UploadDao;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.MediaBaseType.eContentType;
@Repository
public class UploadDaoImpl extends BaseDao implements UploadDao{
	
	@Override
	public List<DcpOriginalasset> GetUploadInfo(int mediaId, MediaBaseType.eFileType fileType) {
		List<DcpOriginalasset> assets = new LinkedList<DcpOriginalasset>();
		Session ss =  this.getSessionFactory().openSession();
		try
		{
			
			Transaction trans = ss.beginTransaction();
			String hql = "";
			if (fileType == null || fileType == MediaBaseType.eFileType.Auto)
				hql = String.format("FROM DcpOriginalasset a WHERE a.mediaId = %d and a.deleted = 0 order by a.originalassetId desc", mediaId);
			else
				hql = String.format("FROM DcpOriginalasset a WHERE a.mediaId = %d and a.deleted = 0 and a.filetype = %d order by a.originalassetId desc", 
									mediaId,
									fileType.ordinal());
			
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpOriginalasset asset = (DcpOriginalasset)it.next();
				if (asset.getContenttype() != eContentType.EduStory.ordinal())
				{
					boolean needAdd = true;
					for(DcpOriginalasset a: assets)
					{
						if (a.getFiletype() == asset.getFiletype())
						{
							needAdd = false;
							break;
						}
					}
					if (needAdd)
					{
						assets.add(asset);
					}
				}
				else
				{
					assets.add(asset);
					break;
				}
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
		
		return assets;
	}
	
	@Override
	public int UpdateUploadInfo(DcpOriginalasset obj) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try
		{			
			trans = ss.beginTransaction();
			if (obj.getOriginalassetId() != null && obj.getOriginalassetId() > 0)
			{
				ss.update(obj);
			}
			else
			{
				ss.save(obj);
			}
			
			trans.commit();
			
			return obj.getOriginalassetId();
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
	public DcpOriginalasset GetOriginalasset(int originalassetId) {
		DcpOriginalasset asset = null;
		Session ss = this.getSessionFactory().openSession();
		try
		{
			
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpOriginalasset a WHERE a.originalassetId = %d", originalassetId);
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
	public List<DcpOriginalasset> GetUploads() {
		
		Session ss =  this.getSessionFactory().openSession();
		List<DcpOriginalasset> objs = null;
		try
		{
			
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpOriginalasset a WHERE a.status IN (%d, %d) and a.processed = 0 and a.attempts < 5",eUploadStatus.uploading.ordinal(),eUploadStatus.done.ordinal());
			objs = ss.createQuery(hql).list();	
			
			trans.commit();			
		}
		catch(Exception e)
		{
		}
		finally
		{
			ss.close();
		}
		return objs;
		
	}

}
