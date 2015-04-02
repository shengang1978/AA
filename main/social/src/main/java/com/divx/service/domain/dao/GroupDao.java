package com.divx.service.domain.dao;

import java.util.List;

import com.divx.service.domain.model.OsfProjects;
import com.divx.service.model.KeyValueTriplePair;

public interface GroupDao {
	OsfProjects GetMyFriendGroup(int userId);
	
	int CreateGroup(OsfProjects group);
	
	List<KeyValueTriplePair<Integer, Integer, OsfProjects>>  GetMyGroups(int userId,int queryType);
	
	OsfProjects GetGroup(int groupId);
	
	int DeleteGroup(int groupId);
	
	int UpdateGroup(OsfProjects group);
}
