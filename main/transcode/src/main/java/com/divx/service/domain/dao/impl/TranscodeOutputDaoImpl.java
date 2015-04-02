package com.divx.service.domain.dao.impl;

import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.TranscodeOutputDao;
import com.divx.service.domain.model.DcpTranscode;
import com.divx.service.domain.model.DcpTranscodeOutput;

@Repository
public class TranscodeOutputDaoImpl extends BaseDao implements TranscodeOutputDao {

	private static final Logger log = Logger.getLogger(TranscodeDaoImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Override
	public DcpTranscodeOutput GetByTranscodeOutputId(int transcodeOutputId) {

		Session ss = getSession();
		
		DcpTranscodeOutput obj = null;
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscodeOutput m WHERE m.transcodeOutputId = %d", transcodeOutputId);
			List<?> objs = ss.createQuery(hql).list();
			
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				obj = (DcpTranscodeOutput)it.next();
				break;
			}
			
			trans.commit();
			return obj;
		}
		catch(Exception e)
		{
			log.error("Fail to get transcodeOutput for transcodeOutputId " + transcodeOutputId);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
	
	@Override
	public DcpTranscodeOutput GetByTranscodeId(int transcodeId) {

		Session ss = getSession();
		
		DcpTranscodeOutput obj = null;
		try
		{	
			Transaction trans = ss.beginTransaction();
			String hql = String.format("FROM DcpTranscodeOutput m WHERE m.transcodeId = %d", transcodeId);
			List<?> objs = ss.createQuery(hql).list();
			
			int maxTranscodeOutputId = -1;
			for (Iterator<?> it = objs.iterator(); it.hasNext(); )
			{
				DcpTranscodeOutput objTemp = (DcpTranscodeOutput)it.next();
				
				// Get the last DcpTranscodeOutput record for asset id
				if(maxTranscodeOutputId < objTemp.getTranscodeOutputId())
					obj = objTemp;
			}
			
			trans.commit();
			return obj;
		}
		catch(Exception e)
		{
			log.error("Fail to get DcpTranscodeOutput for transcodeId " + transcodeId);
		}
		finally
		{
			ss.close();
		}
		
		return null;
	}
		

	@Override
	public int CreateTranscodeOutput(DcpTranscodeOutput obj) {
		//log.info("create transcodeOutput for transcodeId " + obj.getTranscodeId());
		Session ss = getSession();

		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.save(obj);
			
			trans.commit();
			
			return obj.getTranscodeOutputId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			
			log.error("Fail to transcodeOutput for transcodeId " + obj.getTranscodeId());
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int UpdateTranscodeOutput(DcpTranscodeOutput obj) {
		//log.info("update transcode for transcodeOutputId " + obj.getTranscodeOutputId());
		Session ss = getSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			ss.update(obj);
			
			trans.commit();
		
			return obj.getTranscodeOutputId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			
			log.error("Fail to update transcode for transcodeOutputId " + obj.getTranscodeOutputId());
			throw e;
		}
		finally
		{
			ss.close();
		}
	}
	
	private Session getSession() {
		if (sessionFactory == null) {
			sessionFactory = getSessionFactory();
		}

		return sessionFactory.openSession();
	}
}
