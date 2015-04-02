package org.osforce.connect.dao.system.impl;

import java.util.Date;
import java.util.UUID;

import org.osforce.connect.dao.system.ProjectDao;
import org.osforce.connect.entity.system.Project;
import org.osforce.spring4me.dao.AbstractDao;
import org.osforce.spring4me.dao.Page;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Jan 29, 2011 - 11:03:21 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Repository
public class ProjectDaoImpl extends AbstractDao<Project> 
	implements ProjectDao {

	public ProjectDaoImpl() {
		super(Project.class);
	}
	
	static final String JPQL0 = "FROM Project AS p WHERE p.uniqueId = ?1";
	public Project findProject(String uniqueId) {
		return findOne(JPQL0, uniqueId);
	}
	
	static final String JPQL1 = "FROM Project AS p %s ORDER BY p.entered DESC";
	public Page<Project> findProjectPage(Page<Project> page, Long categoryId) {
		if(categoryId!=null) {
			return findPage(page, String.format(JPQL1, "WHERE p.category.id = ?1"), categoryId);
		}
		return findPage(page, String.format(JPQL1, ""));
	}
	
	/*static final String FIND_CONCERED_PAGE_JPQL = "FROM Project p WHERE p.category.id = ?1 AND p.id IN (SELECT l.toId FROM Link l WHERE l.from.id = ?2 AND l.entity = ?3)";
	public Page<Project> findConcernedPage(Page<Project> page,
			ProjectCategory category, User user) {
		return findPage(page, FIND_CONCERED_PAGE_JPQL, 
				category.getId(), user.getProjectId(), Project.class.getSimpleName());
	}*/
	
	static final String JPQL2 = "SELECT COUNT(*) FROM Project";
	public Long countProjects() {
		return count(JPQL2);
	}
	
	static final String JPQL3 = "FROM Project AS p WHERE p.enteredbyId = ?1 and p.categoryId = 1";
	public Project getMyFriendProject(int userId) {

		Project p = null;
		
		try
		{
			p = findOne(JPQL3, userId);
		}
		catch(Exception e)
		{
			p = null;
			e.printStackTrace();
		}
		if (p == null)
		{
			p = new Project();
			p.setCategoryId(new Long(1));
			p.setEnabled(true);
			p.setEnteredId(new Long(userId));
			p.setEntered(new Date());
			p.setModified(new Date());
			p.setModifiedId(new Long(userId));
			p.setTitle("MyFriends");
			String uniqueId = String.format("MyFriend-%d-%s", userId, UUID.randomUUID().toString());
			p.setUniqueId(uniqueId);
			p.setPublish(true);
			
			this.save(p);
		}

		return p;
	}
}
