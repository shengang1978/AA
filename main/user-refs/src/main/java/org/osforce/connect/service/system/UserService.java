package org.osforce.connect.service.system;

import java.util.List;

import org.osforce.connect.entity.system.Project;
import org.osforce.connect.entity.system.User;
import org.osforce.spring4me.dao.Page;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Jan 29, 2011 - 11:07:44 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
public interface UserService {

	User getUser(Long userId);
	
	User getUser(String username);
	
	User getUserByToken(String token);
	
	User loginUser(String username, String password);
	
	int createUser(User user);
	
	int updateUser(User user);
	
	void deleteUser(Long userId);
	
	void registerUser(User user, Project project);

	Page<User> getUserPage(Page<User> page);

	Page<User> getUserPage(Page<User> page, Long siteId);
	
	Page<User> getUserPage(Page<User> page, String startWith);

	List<User> findUser(String username);
	List<User> findUserByEmail(String email);
	List<User> findUserByMobile(String mobile);
	List<User> findUserByNickname(String nickname);
}
