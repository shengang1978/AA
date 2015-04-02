package com.divx.service.domain.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.ShareHistoryDao;
import com.divx.service.domain.model.DcpCommentExt;
import com.divx.service.domain.model.OsfComments;
import com.divx.service.model.KeyValuePair;

@Repository
public class ShareHistoryDaoImpl extends BaseDao implements ShareHistoryDao {

	@Override
	public int AddShareActivity(OsfComments obj) {

		Session session = getSessionFactory().openSession();

		int commId = -1;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			commId = obj.getId().intValue();
		} catch (Throwable ex) {
			// Log exception
			if (tx != null) {
				tx.rollback();
			}

			throw new ExceptionInInitializerError(ex);
		} finally {
			session.close();
		}

		return commId;
	}

	@Override
	public List<OsfComments> GetShareActivities(int shareId, int activityType) {
		Session session = getSessionFactory().openSession();

		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			String hql;
			if (activityType < 0) {
				hql = String
						.format("FROM OsfComments s WHERE s.linkedId = %d and s.enabled = 1 order by s.activitytype, s.id desc",
								shareId);
			} else {
				hql = String
						.format("FROM OsfComments s WHERE s.linkedId = %d and s.activitytype = %d and s.enabled = 1 order by s.id desc",
								activityType, shareId);
			}
			
			return session.createQuery(hql).list();
		} catch (Throwable ex) {
			// Log exception
			// if (tx != null)
			// {
			// tx.rollback();
			// }
			tx = null;
			throw new ExceptionInInitializerError(ex);
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}
	}

	@Override
	public int AddComment(OsfComments base, DcpCommentExt ext) {
		Session session = getSessionFactory().openSession();

		int commId = -1;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(base);
			ext.setCommentId(base.getId().intValue());
			session.save(ext);
			tx.commit();
			commId = base.getId().intValue();
		} catch (Throwable ex) {
			// Log exception
			if (tx != null) {
				tx.rollback();
			}

			throw new ExceptionInInitializerError(ex);
		} finally {
			session.close();
		}
		return commId;
	}

	@Override
	public List<KeyValuePair<OsfComments, DcpCommentExt>> GetComments(
			int assetType, int page, int pageSize, String title) {
		Session session = getSessionFactory().openSession();

		Transaction tx = null;

		try {
			List<KeyValuePair<OsfComments, DcpCommentExt>> ret = new LinkedList<KeyValuePair<OsfComments, DcpCommentExt>>();
			tx = session.beginTransaction();

			String hql = String
					.format("select c, e FROM OsfComments c, DcpCommentExt e, DcpShare s WHERE c.id = e.commentId and c.linkedId = s.shareId and s.assettype = %d and s.mediatitle = '%s' order by c.activitytype, c.id desc",
							assetType, title);

			List<?> objs = session.createQuery(hql)
					.setFirstResult(page * pageSize).setMaxResults(pageSize)
					.list();

			if (objs != null && objs.size() > 0) {
				Iterator<?> it = objs.iterator();
				while (it.hasNext()) {
					Object[] ary = (Object[]) it.next();

					ret.add(new KeyValuePair<OsfComments, DcpCommentExt>(
							(OsfComments) ary[0], (DcpCommentExt) ary[1]));
				}
			}
			return ret;
		} catch (Throwable ex) {
			tx = null;
			throw new ExceptionInInitializerError(ex);
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}
	}

	/**
	 * ¸ü¸ÄµãÔÞ×´Ì¬
	 */
	@Override
	public int UpdateSharaActivities(OsfComments comments) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			comments.setModified(new Date());
			session.update(comments);
			return comments.getId().intValue();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}
	}

	/**
	 *  
	 */
	@Override
	public OsfComments GetLike(int shareId, int userId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		List<?> group = null;
		try {
			tx = session.beginTransaction();
			String hql = String
					.format("FROM OsfComments c WHERE c.linkedId = %d and c.enteredById = %d and c.activitytype = 1",
							shareId, userId);
			group = session.createQuery(hql).list();
			if (group.size() > 0) {
				for (Iterator<?> it = group.iterator(); 
					it.hasNext();) {
					return ((OsfComments) it.next());
				}
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (tx != null)
				tx.commit();
			session.close();
		}
		return null;
	}

}
