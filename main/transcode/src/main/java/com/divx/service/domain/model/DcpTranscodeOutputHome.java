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
 * Home object for domain model class DcpTranscodeOutput.
 * @see com.divx.service.domain.model.DcpTranscodeOutput
 * @author Hibernate Tools
 */
public class DcpTranscodeOutputHome {

	private static final Log log = LogFactory
			.getLog(DcpTranscodeOutputHome.class);

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

	public void persist(DcpTranscodeOutput transientInstance) {
		log.debug("persisting DcpTranscodeOutput instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DcpTranscodeOutput instance) {
		log.debug("attaching dirty DcpTranscodeOutput instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DcpTranscodeOutput instance) {
		log.debug("attaching clean DcpTranscodeOutput instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DcpTranscodeOutput persistentInstance) {
		log.debug("deleting DcpTranscodeOutput instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DcpTranscodeOutput merge(DcpTranscodeOutput detachedInstance) {
		log.debug("merging DcpTranscodeOutput instance");
		try {
			DcpTranscodeOutput result = (DcpTranscodeOutput) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DcpTranscodeOutput findById(java.lang.Integer id) {
		log.debug("getting DcpTranscodeOutput instance with id: " + id);
		try {
			DcpTranscodeOutput instance = (DcpTranscodeOutput) sessionFactory
					.getCurrentSession().get(
							"com.divx.service.domain.model.DcpTranscodeOutput",
							id);
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

	public List findByExample(DcpTranscodeOutput instance) {
		log.debug("finding DcpTranscodeOutput instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.divx.service.domain.model.DcpTranscodeOutput")
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
