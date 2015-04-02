package com.divx.service.domain.dao.impl;

import java.util.Iterator;
import java.util.List;



import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.Util;
import com.divx.service.domain.dao.TranscodeJobDao;
import com.divx.service.domain.model.DcpTranscodeJob;

@Repository
public class TranscodeJobDaoImpl extends BaseDao implements TranscodeJobDao {

	private static final Logger log = Logger.getLogger(TranscodeDaoImpl.class);
	
	private SessionFactory sessionFactory;

	@Override
	public List<DcpTranscodeJob> GetTranscodeJobByTranscodeId(int transcodeId) {
		
		Session ss = getSession();
		
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscodeJob m WHERE m.transcodeId = %d", transcodeId);
			List<?> objs = ss.createQuery(hql).list();
			
			trans.commit();
			return (List<DcpTranscodeJob>)objs;
		}
		catch(Exception e)
		{
			//log.error("Fail to get undone transcode");
			Util.LogError(log, String.format("GetTranscodeJobByTranscodeId(%d)", transcodeId), e);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	
	@Override
	public int CreateTranscodeJob(DcpTranscodeJob obj) {
		//log.info("create transcode job for transcode id " + obj.getTranscodeId());
		Session ss = getSession();

		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.save(obj);
			
			trans.commit();
			
			return obj.getJobId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			
			//log.error("Fail to create transcode job for transcode id " + obj.getTranscodeId());
			Util.LogError(log, String.format("CreateTranscodeJob(%s)", Util.ObjectToJson(obj)), e);
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	
	
	@Override
	public int UpdateTranscodeJob(DcpTranscodeJob obj) {
		//log.info("update transcode job for job Id " + obj.getJobId());
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.update(obj);
			
			trans.commit();
		
			return obj.getJobId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			
			log.error("Fail to transcode job for job id " + obj.getJobId());
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	
	@Override
	public DcpTranscodeJob GetTranscodeJobByJobName(String jobname) {
		Session ss = getSession();
		
		DcpTranscodeJob obj = null;
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscodeJob m WHERE m.jobname = '%s'", jobname);
			List<?> objs = ss.createQuery(hql).list();
			
			int maxJobId = -1;
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpTranscodeJob objTemp = (DcpTranscodeJob)it.next();
				
				// Get the last transcode record for asset id
				if(maxJobId < objTemp.getJobId())
					obj = objTemp;
			}
			
			trans.commit();
			return obj;
		}
		catch(Exception e)
		{
			log.error("Fail to get transcode job for jobname " + jobname);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	
	private Session getSession() {
		if (sessionFactory == null) {
			sessionFactory = getSessionFactory();
		}

		return sessionFactory.openSession();
	}
}
