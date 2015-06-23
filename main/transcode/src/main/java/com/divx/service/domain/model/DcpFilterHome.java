package com.divx.service.domain.model;

// Generated 2015-4-3 16:12:38 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class DcpFilter.
 * @see com.divx.service.domain.model.DcpFilter
 * @author Hibernate Tools
 */
public class DcpFilterHome {

	private static final Log log = LogFactory.getLog(DcpFilterHome.class);

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

	public void persist(DcpFilter transientInstance) {
		log.debug("persisting DcpFilter instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DcpFilter instance) {
		log.debug("attaching dirty DcpFilter instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DcpFilter instance) {
		log.debug("attaching clean DcpFilter instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DcpFilter persistentInstance) {
		log.debug("deleting DcpFilter instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DcpFilter merge(DcpFilter detachedInstance) {
		log.debug("merging DcpFilter instance");
		try {
			DcpFilter result = (DcpFilter) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DcpFilter findById(java.lang.Integer id) {
		log.debug("getting DcpFilter instance with id: " + id);
		try {
			DcpFilter instance = (DcpFilter) sessionFactory.getCurrentSession()
					.get("com.divx.service.domain.model.DcpFilter", id);
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

	public List findByExample(DcpFilter instance) {
		log.debug("finding DcpFilter instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.divx.service.domain.model.DcpFilter")
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
