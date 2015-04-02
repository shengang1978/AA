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
 * Home object for domain model class DcpOrgsite.
 * @see com.divx.service.domain.model.DcpOrgsite
 * @author Hibernate Tools
 */
public class DcpOrgsiteHome {

	private static final Log log = LogFactory.getLog(DcpOrgsiteHome.class);

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

	public void persist(DcpOrgsite transientInstance) {
		log.debug("persisting DcpOrgsite instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DcpOrgsite instance) {
		log.debug("attaching dirty DcpOrgsite instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DcpOrgsite instance) {
		log.debug("attaching clean DcpOrgsite instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DcpOrgsite persistentInstance) {
		log.debug("deleting DcpOrgsite instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DcpOrgsite merge(DcpOrgsite detachedInstance) {
		log.debug("merging DcpOrgsite instance");
		try {
			DcpOrgsite result = (DcpOrgsite) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DcpOrgsite findById(java.lang.Integer id) {
		log.debug("getting DcpOrgsite instance with id: " + id);
		try {
			DcpOrgsite instance = (DcpOrgsite) sessionFactory
					.getCurrentSession().get(
							"com.divx.service.domain.model.DcpOrgsite", id);
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

	public List findByExample(DcpOrgsite instance) {
		log.debug("finding DcpOrgsite instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.divx.service.domain.model.DcpOrgsite")
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
