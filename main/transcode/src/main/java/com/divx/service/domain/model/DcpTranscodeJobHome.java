package com.divx.service.domain.model;

// Generated 2015-2-5 15:50:16 by Hibernate Tools 4.3.1

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class DcpTranscodeJob.
 * @see com.divx.service.domain.model.DcpTranscodeJob
 * @author Hibernate Tools
 */
public class DcpTranscodeJobHome {

	private static final Log log = LogFactory.getLog(DcpTranscodeJobHome.class);

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

	public void persist(DcpTranscodeJob transientInstance) {
		log.debug("persisting DcpTranscodeJob instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DcpTranscodeJob instance) {
		log.debug("attaching dirty DcpTranscodeJob instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DcpTranscodeJob instance) {
		log.debug("attaching clean DcpTranscodeJob instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DcpTranscodeJob persistentInstance) {
		log.debug("deleting DcpTranscodeJob instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DcpTranscodeJob merge(DcpTranscodeJob detachedInstance) {
		log.debug("merging DcpTranscodeJob instance");
		try {
			DcpTranscodeJob result = (DcpTranscodeJob) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DcpTranscodeJob findById(java.lang.Integer id) {
		log.debug("getting DcpTranscodeJob instance with id: " + id);
		try {
			DcpTranscodeJob instance = (DcpTranscodeJob) sessionFactory
					.getCurrentSession()
					.get("com.divx.service.domain.model.DcpTranscodeJob", id);
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

	public List findByExample(DcpTranscodeJob instance) {
		log.debug("finding DcpTranscodeJob instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.divx.service.domain.model.DcpTranscodeJob")
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
