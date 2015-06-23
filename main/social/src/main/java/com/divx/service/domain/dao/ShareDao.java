package com.divx.service.domain.dao;

import java.util.List;

import javax.ws.rs.QueryParam;

import com.divx.service.domain.model.*;
import com.divx.service.model.KeyValueFourPair;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;
import com.divx.service.model.QueryOption;
import com.divx.service.model.ShareOption.ShareType;


public interface ShareDao {
	
	// create a share
	int CreateShare(DcpShare obj,int groupId);
	
	int SetShareByTitle(DcpShare obj);
	
	// Get all shares the person created.
	List<ShareDbInfo>  GetShares(int userId,int option);
	
	List<ShareDbInfo> GetPublicShares(List<Integer> contentTypes,QueryOption.SearchType searchOption);
	
	// Get all the shares in the group.
	// 
	//List<KeyValuePair<DcpShare,DcpShareUser>> GetSharesOfGroup(int groupId);
	
	// Get all the shares the friend share to him/her.
	//List<DcpShare> GetSharesOfFriend(int userId);
	
	List<ShareDbInfo> GetSharesOfGroups(int userId, int groupId);
	
	int	DeleteShare(int userId, int shareId);
	
	int	UpdateDcpShareStatus(int mediaId, int status);
	
	int CreateShareUser(ShareType ShareType,DcpShareUser shareUser);
	DcpShareUser GetDcpShareUser(int shareId);
	
}
