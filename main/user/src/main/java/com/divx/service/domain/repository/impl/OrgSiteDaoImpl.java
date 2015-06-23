package com.divx.service.domain.repository.impl;

import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.divx.service.SessionFactoryHelper;
import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpOrgsite;
import com.divx.service.domain.repository.OrgSiteDao;

@Repository
public class OrgSiteDaoImpl 
	implements OrgSiteDao {

	protected SessionFactory getSessionFactory() {
		try {
			return SessionFactoryHelper.getSessionFactory();
		} catch (Exception e) {
			//log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}
	
	@Override
	public List<DcpOrgsite> findOrgSite(String siteName) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try {
			trans = ss.beginTransaction();
			if (!siteName.isEmpty())
				return ss.createCriteria("com.divx.service.domain.model.DcpOrgsite")
					.add(Restrictions.like("sitename", "%" + siteName + "%"))
					.createCriteria("dcpOrganization").list();
			else
				return ss.createCriteria("com.divx.service.domain.model.DcpOrgsite")
						.createCriteria("dcpOrganization").list();
				
		} catch (RuntimeException re) {
			throw re;
		}
		finally
		{
			if (trans != null)
				trans.commit();
			ss.close();
		}
	}

	@Override
	public int setOrgSite(DcpOrgsite site) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try
		{
			trans = ss.beginTransaction();
			if (site.getId() != null)
			{
				site.setDatemodified(new Date());
				//this.attachDirty(site);
				ss.update(site);
				return site.getId();
			}
			else
			{
				Criteria crSite = ss
						.createCriteria("com.divx.service.domain.model.DcpOrgsite")
						.add(Restrictions.eq("siteId", site.getSiteId()));
				
				crSite = crSite.createCriteria("dcpOrganization")
						.add(Restrictions.eq("id", site.getDcpOrganization().getId()));
	
				List results = crSite.list();
				
				if (results.isEmpty())
				{
					site.setDatecreated(new Date());
					site.setDatemodified(new Date());
					//attachDirty(site);
					ss.save(site);
					return site.getId();
				}
				else
				{
					DcpOrgsite obj = (DcpOrgsite)results.get(0);
					obj.setDatemodified(new Date());
					obj.setSitename(site.getSitename());
					obj.setSiteurl(site.getSiteurl());
					//this.attachDirty(obj);
					ss.update(obj);
					return obj.getId();
				}
			}
		}
		catch(Exception e)
		{
			if (trans != null)
				trans.rollback();
			throw e;
		}
		finally
		{
			if (trans != null)
				trans.commit();
			ss.close();
		}
	}

	@Override
	public DcpOrganization findOrgByTitle(String orgTitle) {
		Session ss = this.getSessionFactory().openSession();
		Transaction trans = null;
		try {
			trans = ss.beginTransaction();
			List results = ss.createCriteria("com.divx.service.domain.model.DcpOrganization")
					.add(Restrictions.eq("title", orgTitle)).list();
			
			if (!results.isEmpty())
				return (DcpOrganization)results.get(0);
		} catch (RuntimeException re) {
			throw re;
		}
		finally
		{
			if (trans != null)
				trans.commit();
			ss.close();
		}
		return null;
	}
	
}
