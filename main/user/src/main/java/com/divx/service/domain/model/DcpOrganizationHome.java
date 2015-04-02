package com.divx.service.domain.model;

// Generated 2015-2-6 15:30:05 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class DcpOrganization.
 * @see com.divx.service.domain.model.DcpOrganization
 * @author Hibernate Tools
 */
public class DcpOrganizationHome {

	private static final Log log = LogFactory.getLog(DcpOrganizationHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(DcpOrganization transientInstance) {
		log.debug("persisting DcpOrganization instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DcpOrganization instance) {
		log.debug("attaching dirty DcpOrganization instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DcpOrganization instance) {
		log.debug("attaching clean DcpOrganization instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DcpOrganization persistentInstance) {
		log.debug("deleting DcpOrganization instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DcpOrganization merge(DcpOrganization detachedInstance) {
		log.debug("merging DcpOrganization instance");
		try {
			DcpOrganization result = (DcpOrganization) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DcpOrganization findById(java.lang.Integer id) {
		log.debug("getting DcpOrganization instance with id: " + id);
		try {
			DcpOrganization instance = (DcpOrganization) sessionFactory
					.getCurrentSession()
					.get("com.divx.service.domain.model.DcpOrganization", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DcpOrganization instance) {
		log.debug("finding DcpOrganization instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.divx.service.domain.model.DcpOrganization")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
