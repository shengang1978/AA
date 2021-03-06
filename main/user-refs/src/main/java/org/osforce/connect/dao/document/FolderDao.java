package org.osforce.connect.dao.document;

import java.util.List;

import org.osforce.connect.entity.document.Folder;
import org.osforce.spring4me.dao.BaseDao;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Feb 12, 2011 - 8:19:44 AM
 *  <a href="http://www.opensourceforce.org">开源力量</a>
 */
public interface FolderDao extends BaseDao<Folder> {

	/**
	 * Find root folder list
	 * @param projectId
	 * @return
	 */
	List<Folder> findFolderList(Long projectId, Long parendId);

}
