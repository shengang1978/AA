package com.divx.service.domain.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import com.divx.service.domain.model.DcpConfig;
import com.divx.service.domain.model.DcpEmailTemplate;
import com.divx.service.domain.model.OsfMailSettings;
import com.divx.service.domain.repository.ConfigDao;

@Repository
public class ConfigDaoImpl implements ConfigDao {

	private static SessionFactory factory;
	
	static {
		factory = new Configuration().configure().buildSessionFactory();
	};
	
	@Override
	public List<DcpConfig> GetConfigs() {
		Session ss = ConfigDaoImpl.factory.openSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();

			String hql = String.format("FROM DcpConfig");

			List<DcpConfig> objs = ss.createQuery(hql).list();

			trans.commit();
			return objs;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpEmailTemplate getEmailTemplate(int id) {
		Session ss = ConfigDaoImpl.factory.openSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();

			String hql = String.format("FROM DcpEmailTemplate d where d.emailId = %d",id);

			List<DcpEmailTemplate> objs = ss.createQuery(hql).list();
			if(objs != null && objs.size() > 0){
				trans.commit();
				return objs.get(0);	
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
		return null;
	}

	@Override
	public OsfMailSettings getEmailSetting(int id) {
		Session ss = ConfigDaoImpl.factory.openSession();
		
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();

			String hql = String.format("FROM OsfMailSettings d where d.id = %d",id);

			List<OsfMailSettings> objs = ss.createQuery(hql).list();
			if(objs != null && objs.size() > 0){
				trans.commit();
				return objs.get(0);	
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
		return null;
	}
	
}
