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
 * Home object for domain model class DcpRadio.
 * @see com.divx.service.domain.model.DcpRadio
 * @author Hibernate Tools
 */
public class DcpRadioHome {

	private static final Log log = LogFactory.getLog(DcpRadioHome.class);

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

	public void persist(DcpRadio transientInstance) {
		log.debug("persisting DcpRadio instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DcpRadio instance) {
		log.debug("attaching dirty DcpRadio instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DcpRadio instance) {
		log.debug("attaching clean DcpRadio instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DcpRadio persistentInstance) {
		log.debug("deleting DcpRadio instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DcpRadio merge(DcpRadio detachedInstance) {
		log.debug("merging DcpRadio instance");
		try {
			DcpRadio result = (DcpRadio) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DcpRadio findById(int id) {
		log.debug("getting DcpRadio instance with id: " + id);
		try {
			DcpRadio instance = (DcpRadio) sessionFactory.getCurrentSession()
					.get("com.divx.service.domain.model.DcpRadio", id);
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

	public List findByExample(DcpRadio instance) {
		log.debug("finding DcpRadio instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.divx.service.domain.model.DcpRadio")
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
