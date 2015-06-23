package com.divx.service.domain.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.Util;
import com.divx.service.domain.dao.TranscodeDao;
import com.divx.service.domain.model.DcpFilter;
import com.divx.service.domain.model.DcpTranscode;
import com.divx.common.main.Constants.eJobType;
import com.divx.common.main.Constants.eProcessStatus;

@Repository
public class TranscodeDaoImpl extends BaseDao implements TranscodeDao {

	private static final Logger log = Logger.getLogger(TranscodeDaoImpl.class);
	
	//private SessionFactory sessionFactory;
	
	@Override
	public DcpTranscode GetTranscodeByTranscodeId(int transcodeId) {

		Session ss = getSession();
		
		DcpTranscode obj = null;
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscode m WHERE m.transcodeId = %d", transcodeId);
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				obj = (DcpTranscode)it.next();
				break;
			}
			
			trans.commit();
			return obj;
		}
		catch(Exception e)
		{
			log.error("Fail to get transcode for transcodeId " + transcodeId);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	
	@Override
	public DcpTranscode GetTranscodeByAssetId(int assetId) {

		Session ss = getSession();
		
		DcpTranscode obj = null;
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscode m WHERE m.assetId = %d", assetId);
			List<?> objs = ss.createQuery(hql).list();
			
			int maxTranscodeId = -1;
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpTranscode objTemp = (DcpTranscode)it.next();
				
				// Get the last transcode record for asset id
				if(maxTranscodeId < objTemp.getTranscodeId())
					obj = objTemp;
			}
			
			trans.commit();
			return obj;
		}
		catch(Exception e)
		{
			log.error("Fail to get transcode for transcodeId " + assetId);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	
	@Override
	public DcpTranscode GetTranscodeByJobName(String jobname) {
		Session ss = getSession();
		
		DcpTranscode obj = null;
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscode m WHERE m.jobname = '%s'", jobname);
			List<?> objs = ss.createQuery(hql).list();
			
			int maxTranscodeId = -1;
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpTranscode objTemp = (DcpTranscode)it.next();
				
				// Get the last transcode record for asset id
				if(maxTranscodeId < objTemp.getTranscodeId())
					obj = objTemp;
			}
			
			trans.commit();
			return obj;
		}
		catch(Exception e)
		{
			log.error("Fail to get transcode for jobname " + jobname);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
		

	@Override
	public int CreateTranscode(DcpTranscode obj) {
		//log.info("create transcode for assetId " + obj.getAssetId());
		Session ss = getSession();

		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.save(obj);
			
			trans.commit();
			
			return obj.getTranscodeId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			
			log.error("Fail to create transcode for assetId " + obj.getAssetId());
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int UpdateTranscode(DcpTranscode obj) {
		//log.info("update transcode for transcodeId " + obj.getTranscodeId());
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.update(obj);
			
			trans.commit();
		
			return obj.getTranscodeId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			
			log.error("Fail to update transcode for transcodeId " + obj.getTranscodeId());
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	
	private Session getSession() {
		return getSessionFactory().openSession();
	}

	@Override
	public List<DcpTranscode> GetUndoneTranscode(int status) {
		
		Session ss = getSession();
		
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscode m WHERE m.status = %d", status);
			List<?> objs = ss.createQuery(hql).list();
			
			trans.commit();
			return (List<DcpTranscode>)objs;
		}
		catch(Exception e)
		{
			log.error("Fail to get undone transcode", e);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	
	@Override
	public List<DcpTranscode> GetUndoneTranscode(int jobType, List<Integer> status) {
		Session ss = getSession();
		
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscode m WHERE m.jobtype = :jobType and m.status in (:status)");
			Query query = ss.createQuery(hql);
			query.setParameter("jobType", jobType);
			query.setParameterList("status", status);
			//List<?> objs = ss.createQuery(hql).list();
			List<?> objs = query.list();
			trans.commit();
			return (List<DcpTranscode>)objs;
		}
		catch(Exception e)
		{
			log.error("Fail to get undone transcode", e);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	

	@Override
	public List<DcpTranscode> GetUndoneTranscode(List<Integer> status) {
		Session ss = getSession();
		
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscode m WHERE m.status in (:status)");
			Query query = ss.createQuery(hql);
			query.setParameterList("status", status);
			//List<?> objs = ss.createQuery(hql).list();
			List<?> objs = query.list();
			trans.commit();
			return (List<DcpTranscode>)objs;
		}
		catch(Exception e)
		{
			log.error("Fail to get undone transcode", e);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}

	@Override
	public int CreateFilter(DcpFilter obj) {
		Session ss = getSession();
		Transaction trans = null;
		try
		{	trans = ss.beginTransaction();
		String hql = String.format("FROM DcpFilter f WHERE f.width = %d and f.height = %d and f.rotation = %d and f.format = %d order by f.filterId desc", obj.getWidth(),obj.getHeight(),obj.getRotation(),obj.getFormat());
		List<?> objs = ss.createQuery(hql).list();
		if(objs != null && objs.size() > 0){
			DcpFilter filter = (DcpFilter)objs.get(0);
			filter.setDateModify(new Date());
			filter.setFilterContent(obj.getFilterContent());
			ss.update(filter);
		}else{
			ss.save(obj);
		}
			trans.commit();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			log.error("Fail to create Filter" + Util.getStackTrace(e));
		}
		finally
		{
			ss.close();
		}
		return obj.getFilterId();
	}

	@Override
	public DcpFilter getDcpFilter(int width, int height, int rotation,eJobType type) {
		Session ss = getSession();
		Transaction trans = null;
		DcpFilter obj = null;
		try
		{	trans = ss.beginTransaction();
			String hql = String.format("FROM DcpFilter f WHERE f.width = %d and f.height = %d and f.rotation = %d and f.format = %d and f.enabled = 1 order by f.filterId desc", width,height,rotation,type.ordinal());
			List<?> objs = ss.createQuery(hql).list();
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				obj = (DcpFilter)it.next();
				break;
			}
			trans.commit();
			
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			log.error("Fail to get dcpFilter" + Util.getStackTrace(e));
		}
		finally
		{
			ss.close();
		}
		return obj;
	}



}
