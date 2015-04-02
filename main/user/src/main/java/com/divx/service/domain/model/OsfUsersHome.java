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
 * Home object for domain model class OsfUsers.
 * @see com.divx.service.domain.model.OsfUsers
 * @author Hibernate Tools
 */
public class OsfUsersHome {

	private static final Log log = LogFactory.getLog(OsfUsersHome.class);

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

	public void persist(OsfUsers transientInstance) {
		log.debug("persisting OsfUsers instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(OsfUsers instance) {
		log.debug("attaching dirty OsfUsers instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(OsfUsers instance) {
		log.debug("attaching clean OsfUsers instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(OsfUsers persistentInstance) {
		log.debug("deleting OsfUsers instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public OsfUsers merge(OsfUsers detachedInstance) {
		log.debug("merging OsfUsers instance");
		try {
			OsfUsers result = (OsfUsers) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public OsfUsers findById(java.lang.Long id) {
		log.debug("getting OsfUsers instance with id: " + id);
		try {
			OsfUsers instance = (OsfUsers) sessionFactory.getCurrentSession()
					.get("com.divx.service.domain.model.OsfUsers", id);
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

	public List findByExample(OsfUsers instance) {
		log.debug("finding OsfUsers instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.divx.service.domain.model.OsfUsers")
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
