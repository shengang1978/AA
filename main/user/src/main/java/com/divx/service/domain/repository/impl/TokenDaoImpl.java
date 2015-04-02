package com.divx.service.domain.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.TokenDao;
@Repository
public class TokenDaoImpl extends BaseDao implements TokenDao {

	@Override
	public int CreateToken(DcpToken token) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.save(token);
			trans.commit();
		
			return token.getTokenId();
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
	public DcpToken GetToken(String token) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpToken obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpToken t WHERE t.token = '%s'", token);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (DcpToken)objs.get(0);
			}
			trans.commit();
		
			return obj;
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
	public DcpToken GetToken(String deviceUniqueId, int userId) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		DcpToken obj = null;
		try
		{
			trans = ss.beginTransaction();
			String hql = String.format("FROM DcpToken t WHERE t.deviceuniqueid = '%s' and t.userId = %d", deviceUniqueId, userId);
			List<?> objs = ss.createQuery(hql).list();
			if (objs != null && objs.size() > 0)
			{
				obj = (DcpToken)objs.get(0);
			}
			trans.commit();
		
			return obj;
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
	public int UpdateToken(DcpToken token) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;

		try
		{
			trans = ss.beginTransaction();
			ss.update(token);
			trans.commit();
		
			return token.getTokenId();
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
}
