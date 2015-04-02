package com.divx.service.domain.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.model.DcpRecver;
import com.divx.service.domain.model.DcpUserdevice;
import com.divx.service.domain.repository.DeviceDao;

@Repository
public class DeviceDaoImpl extends BaseDao implements DeviceDao {


	@Override
	public DcpRecver GetRecver(int userId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpRecver recver = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpRecver r WHERE r.userId = %d", userId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				recver = (DcpRecver)objs.get(0);
			}
			trans.commit();
		
			return recver;
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
	public int AddRecver(DcpRecver recver) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(recver);
			trans.commit();
		
			return recver.getUserId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
	}

	@Override
	public int UpdateRecver(DcpRecver recver) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.update(recver);
			trans.commit();
		
			return recver.getUserId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}	
	}

	@Override
	public int AddDevice(DcpUserdevice dev) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(dev);
			trans.commit();
		
			return dev.getDeviceId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public int UpdateDevice(DcpUserdevice dev) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.update(dev);
			trans.commit();
		
			return dev.getDeviceId();
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
			throw e;
		}
		finally
		{
			ss.close();
		}
	}

	@Override
	public DcpUserdevice GetDevice(String deviceGuid) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpUserdevice dev = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpUserdevice ud WHERE ud.deviceGuid = '%s'", deviceGuid);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				dev = (DcpUserdevice)objs.get(0);
			}
			trans.commit();
		
			return dev;
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

}
