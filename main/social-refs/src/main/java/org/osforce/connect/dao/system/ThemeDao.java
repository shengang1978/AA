package org.osforce.connect.dao.system;

import java.util.List;

import org.osforce.connect.entity.system.Theme;
import org.osforce.spring4me.dao.BaseDao;
import org.osforce.spring4me.dao.Page;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Jan 28, 2011 - 3:57:06 PM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
public interface ThemeDao extends BaseDao<Theme> {

	/**
	 * Find theme list
	 * @return
	 */
	List<Theme> findThemeList();

	/**
	 * Find theme page
	 * @param page
	 * @return
	 */
	Page<Theme> findThemePage(Page<Theme> page);

}
