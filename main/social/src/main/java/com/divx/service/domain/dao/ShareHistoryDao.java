package com.divx.service.domain.dao;

import java.util.List;

import com.divx.service.domain.model.ActivityDbInfo;
import com.divx.service.domain.model.DcpCommentExt;
import com.divx.service.domain.model.OsfComments;
import com.divx.service.model.BaseSocialType;
import com.divx.service.model.KeyValuePair;

public interface ShareHistoryDao {
	int AddShareActivity(OsfComments act);
	
	List<OsfComments> GetShareActivities(int shareId, int activityType);
	
	//KeyValuePair<OsfComments, DcpCommentExt>
	int AddComment(OsfComments base, DcpCommentExt ext);
	
	//List<KeyValuePair<OsfComments, DcpCommentExt>> GetComments(int shareId);
	
	List<KeyValuePair<OsfComments, DcpCommentExt>> GetComments(int assetType, int page, int pageSize, String title);
	
	int UpdateSharaActivities(OsfComments osf);

	OsfComments GetLike(int shareId, int userId);
	
	ActivityDbInfo AddComment(int userId, int shareId, BaseSocialType.ActionType at, BaseSocialType.eLikeOption lo, String content, int point);
	
}
