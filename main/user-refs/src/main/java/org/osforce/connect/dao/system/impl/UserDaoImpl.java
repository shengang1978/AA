package org.osforce.connect.dao.system.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.osforce.connect.dao.system.UserDao;
import org.osforce.connect.entity.system.User;
import org.osforce.spring4me.dao.AbstractDao;
import org.osforce.spring4me.dao.Page;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Jan 29, 2011 - 10:09:37 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

	protected UserDaoImpl() {
		super(User.class);
	}
	
	public User getUserByUsername(String username) {
		return findOne("FROM User AS u WHERE u.username = ?1", username);
	}

	static final String JPQL1 = "FROM User AS u %s ORDER BY u.entered DESC";
	public Page<User> findUserPage(Page<User> page, Long siteId) {
		if(siteId!=null) {
			return findPage(page, String.format(JPQL1, "WHERE u.project.category.site.id = ?1"), siteId);
		}
		return findPage(page, String.format(JPQL1, ""));
	}
	
	public Page<User> findUserPage(Page<User> page, String startWith) {
		return findPage(page, "FROM User AS u WHERE u.username LIKE ?1", startWith + "%");
	}
	
	public User findUserByToken(String token) {
		return findOne("FROM User AS u WHERE u.token = ?1", token);
	}
	
	//static final String JPQL4 = "FROM User AS u WHERE u.email = ?1";
	public List<User> findUserByEmail(String email) {
		//return findOne(JPQL4, email);
		TypedQuery<User> query = entityManager.createQuery("select u FROM User as u WHERE u.email LIKE:email", User.class);
		query.setParameter("email", email + '%');
		return query.getResultList();
	}
	
	public List<User> findUserByMobile(String mobile) {
		//return findOne(JPQL5, mobile);
		TypedQuery<User> query = entityManager.createQuery("select u FROM User as u WHERE u.mobile LIKE:mobile", User.class);
		query.setParameter("mobile", mobile + '%');
		return query.getResultList();
	}
	
	@Override
	public User getUserById(Long userId) {
		return findOne("FROM User AS u WHERE u.id = ?1", userId);
	}
	
	@Override
	public List<User> findUserByUsername(String username) {
		TypedQuery<User> query = entityManager.createQuery("select u FROM User as u WHERE u.username LIKE:username ORDER BY u.username", User.class);
		query.setParameter("username", username + '%');
		return query.getResultList();
	}

	@Override
	public User getUserByEmail(String email) {
		return findOne("FROM User u WHERE u.email = ?1", email);
	}

	@Override
	public User getUserByMobile(String mobile) {
		return findOne("FROM User u WHERE u.mobile = ?1", mobile);
	}

	@Override
	public List<User> findUserByNickname(String nickname) {
		TypedQuery<User> query = entityManager.createQuery("select u FROM User as u WHERE u.nickname LIKE:nickname ORDER BY u.username", User.class);
		query.setParameter("nickname", nickname + '%');
		return query.getResultList();
	}
}
