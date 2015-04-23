package com.divx.service.domain.dao;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.divx.service.domain.model.*;
import com.divx.service.model.KeyValueTriplePair;


public interface ShareDao {
	
	// create a share
	int CreateShare(DcpShare obj);
	
	int SetShareByTitle(DcpShare obj);
	
	// Get all shares the person created.
	List<DcpShare> GetShares(int userId,int option);
	
	List<DcpShare> GetPublicShares(List<Integer> contentTypes);
	
	// Get all the shares in the group.
	// 
	List<DcpShare> GetSharesOfGroup(int groupId);
	
	// Get all the shares the friend share to him/her.
	//List<DcpShare> GetSharesOfFriend(int userId);
	
	List<KeyValueTriplePair<Integer, Integer, DcpShare>> GetSharesOfGroups(int userId, int groupId);
	
	int	DeleteShare(int userId, int shareId);
	
	int	UpdateDcpShareStatus(int mediaId, int status);
}
