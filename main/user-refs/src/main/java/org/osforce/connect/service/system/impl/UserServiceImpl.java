package org.osforce.connect.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.osforce.connect.dao.profile.ProfileDao;
import org.osforce.connect.dao.system.ProjectDao;
import org.osforce.connect.dao.system.ProjectFeatureDao;
import org.osforce.connect.dao.system.UserDao;
import org.osforce.connect.entity.profile.Profile;
import org.osforce.connect.entity.system.Project;
import org.osforce.connect.entity.system.ProjectFeature;
import org.osforce.connect.entity.system.User;
import org.osforce.connect.service.system.UserService;
import org.osforce.spring4me.dao.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Jan 29, 2011 - 11:15:39 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private ProfileDao profileDao;
	private ProjectDao projectDao;
	private ProjectFeatureDao projectFeatureDao;
	
	public UserServiceImpl() {
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Autowired
	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}
	
	@Autowired
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
	@Autowired
	public void setProjectFeatureDao(ProjectFeatureDao projectFeatureDao) {
		this.projectFeatureDao = projectFeatureDao;
	}
	
	public User getUserByToken(String token)
	{
		return userDao.findUserByToken(token);
	}
	
	public User getUser(Long userId) {
		//return userDao.get(userId);
		return userDao.getUserById(userId);
	}
	
	public User loginUser(String username, String password) {
		User user = userDao.getUserByUsername(username);
		if (user == null)
		{
			if (username.indexOf("@") > 0)
			{
				user = userDao.getUserByEmail(username);
			}
			else
			{
				user = userDao.getUserByMobile(username);
			}
		}
		
		if(user!=null && StringUtils.equals(password, user.getPassword())) {
			//user.setLastLogin(new Date());
			//userDao.update(user);
			UUID uid = UUID.randomUUID();
			user.setToken(uid.toString());
			user.setLastLogin(new Date());
			userDao.update(user);
			return user;
		}
			
		return  null;
	}
	
	public int createUser(User user) {
		return updateUser(user);
	}

	public int updateUser(User user) {
		if(user.getId()==null) {
			switch(user.getUsernametype())
			{
			case 0:	// username
				if (userDao.getUserByUsername(user.getUsername()) != null)
					return -2001;	//ResponseCode.ERROR_REGISTER_USER_USERNAME_EXIST
				break;
			case 1:	// email
				if (userDao.getUserByEmail(user.getUsername()) != null)
					return -2002;	//ResponseCode.ERROR_REGISTER_USER_EMAIL_EXIST
				break;
			case 2: // mobile
				if (userDao.getUserByMobile(user.getUsername()) != null)
					return -2003;   //ResponseCode.ERROR_REGISTER_USER_MOBILE_EXIST
				break;
			}
			UUID uid = UUID.randomUUID();
			user.setToken(uid.toString());
			user.setLastLogin(new Date());
			userDao.save(user);
		} else {
			userDao.update(user);
		}
		
		return 0; //ResponseCode。SUCCESS
	}

	public void deleteUser(Long userId) {
		userDao.delete(userId);
	}
	
	public User getUser(String username) {
		return userDao.getUserByUsername(username);
	}
	
	public Page<User> getUserPage(Page<User> page) {
		return userDao.findUserPage(page, (Long)null);
	}
	
	public Page<User> getUserPage(Page<User> page, Long siteId) {
		return userDao.findUserPage(page, siteId);
	}
	
	public Page<User> getUserPage(Page<User> page, String startWith) {
		return userDao.findUserPage(page, startWith);
	}
	
	public void registerUser(User user, Project project) {
		Assert.notNull(user, "Parameter user can not be null!");
		//Assert.notNull(project, "Parameter project can not be null!");
		// create user
		Date now = new Date();
		user.setEmail(user.getUsername());
		user.setEntered(now);
		user.setLastLogin(now);
		user.setEnabled(true);
		user.setToken(UUID.randomUUID().toString());
		userDao.save(user);
		// create project
		String uniqueId = cleanEmail(user.getEmail());
		project.setTitle(user.getNickname());
		project.setUniqueId(uniqueId);
		project.setModified(now);
		project.setModifiedBy(user);
		project.setEntered(now);
		project.setEnteredBy(user);
		project.setEnabled(true);
		projectDao.save(project);
		// create project feature
		List<ProjectFeature> features = project.getFeatures();
		for(ProjectFeature feature : features) {
			feature.setProject(project);
			projectFeatureDao.save(feature);
		}
		// create project profile
		Profile profile = new Profile();
		profile.setTitle(project.getTitle());
		profile.setProject(project);
		profile.setEnteredBy(user);
		profile.setModifiedBy(user);
		profile.setEntered(now);
		profile.setModified(now);
		profileDao.save(profile);
		// update user project
		user.setProject(project);
		userDao.update(user);
	}
	
	protected String cleanEmail(String email) {
		email = StringUtils.lowerCase(email);
		String uniqueId = StringUtils.substringBefore(email, "@");
		StringBuffer buffer = new StringBuffer();
		for(char c : uniqueId.toCharArray()) {
			if((c>='0' && c<='9') || (c>='a' && c<='z') || c=='_' || c=='-') {
				buffer.append(c);
			} else {
				buffer.append('-');
			}
		}
		return buffer.toString();
	}

	@Override
	public List<User> findUser(String username) {
		return userDao.findUserByUsername(username);
	}

	@Override
	public List<User> findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public List<User> findUserByMobile(String mobile) {
		return userDao.findUserByMobile(mobile);
	}

	@Override
	public List<User> findUserByNickname(String nickname) {
		// TODO Auto-generated method stub
		return userDao.findUserByNickname(nickname);
	}
}
