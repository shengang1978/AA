package com.divx.service.domain.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.divx.service.SessionFactoryHelper;
import com.divx.service.domain.dao.PresetDao;
import com.divx.service.domain.model.DcpPresetgroup;
import com.divx.service.domain.model.DcpRadio;
import com.divx.service.domain.model.DcpRadioHome;

@Repository
public class PresetDaoImpl extends DcpRadioHome implements PresetDao 
{
	protected SessionFactory getSessionFactory() {
		return SessionFactoryHelper.getSessionFactory();
	}
	@Override
	public List<DcpRadio> GetRadios() {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try {
			trans = ss.beginTransaction();
			List<?> results = ss
					.createCriteria("com.divx.service.domain.model.DcpRadio")
					.createCriteria("dcpPresetgroups")
					.list();
			return (List<DcpRadio>) results;
		}
		finally
		{
			if (trans != null)
				trans.commit();
			
			ss.close();
		}
	}

	@Override
	public List<DcpPresetgroup> GetPresetgroups(int radioId, int format) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try {
			trans = ss.beginTransaction();
			List objs = ss.createCriteria("com.divx.service.domain.model.DcpPresetgroup")
					.add(Restrictions.eq("format", format))
					.createCriteria("dcpRadio")
						.add(Restrictions.eq("radioId", radioId))
						
					.list();

			return objs;
		}
		finally
		{
			if (trans != null)
				trans.commit();
			
			ss.close();
		}
	}
}
