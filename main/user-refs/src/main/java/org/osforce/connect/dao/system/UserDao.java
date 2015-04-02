package org.osforce.connect.dao.system;

import java.util.List;

import org.osforce.connect.entity.system.User;
import org.osforce.spring4me.dao.BaseDao;
import org.osforce.spring4me.dao.Page;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Jan 29, 2011 - 10:07:53 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * Find user by username
	 * @param username
	 * @return
	 */
	User getUserByUsername(String username);

	User getUserById(Long userId);
	User getUserByEmail(String email);
	User getUserByMobile(String mobile);
	/**
	 * Find user by email, username, mobile
	 * @param username
	 * @return
	 */
	List<User> findUserByEmail(String email);
	
	List<User> findUserByUsername(String username);
	
	List<User> findUserByMobile(String mobile);
	
	List<User> findUserByNickname(String nickname);
	/**
	 * Find user by token
	 * @param token
	 * @return
	 */
	User findUserByToken(String token);
	
	/**
	 * Find user page by ...
	 * @param page
	 * @param siteId Note: site id may be null
	 * @return
	 */
	Page<User> findUserPage(Page<User> page, Long siteId);

	/**
	 * Find user page start with prefix
	 * @param page
	 * @param startWith
	 * @return
	 */
	Page<User> findUserPage(Page<User> page, String startWith);

}
