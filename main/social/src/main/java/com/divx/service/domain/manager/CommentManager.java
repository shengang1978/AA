package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.domain.dao.ShareDao;
import com.divx.service.domain.dao.ShareHistoryDao;
import com.divx.service.domain.model.DcpCommentExt;
import com.divx.service.domain.model.DcpShare;
import com.divx.service.domain.model.OsfComments;
import com.divx.service.model.Activity;
import com.divx.service.model.Comment;
import com.divx.service.model.CommentAsset;
import com.divx.service.model.CommentFull;
import com.divx.service.model.CommentOption;
import com.divx.service.model.CommentsResponse;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;
import com.divx.service.model.User;

@Service
public class CommentManager {
	private ShareHistoryDao	shareHistoryDao;
	private ShareDao	shareDao;
	@Autowired
	public void setShareHistoryDao(ShareHistoryDao shareHistoryDao) {
		this.shareHistoryDao = shareHistoryDao;
	}
	
	@Autowired
	public void setShareDao(ShareDao shareDao) {
		this.shareDao = shareDao;
	}

	public ServiceResponse AddComment(int userId, String location, CommentOption option)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpShare share = new DcpShare();
			CommentAsset asset = option.getAsset();
			share.setAssettype(asset.getAssetTypeId());
			share.setDatecreated(new Date());
			share.setDatemodified(new Date());
			share.setFriendId(0);
			share.setGroupId(0);
			share.setMediadescription(asset.getDescription());
			share.setMediatitle(asset.getTitle());
			share.setMediaId(asset.getId());
			share.setSharetype(ShareOption.ShareType.all.ordinal());
			share.setSnapshoturl(asset.getSnapshotUrl());
			share.setUserId(userId);
			
			int shareId = shareDao.SetShareByTitle(share);
			
			OsfComments base = new OsfComments();
			Comment comment = option.getComment();
			base.setActivitytype(Activity.ActionType.comment.ordinal());
			base.setContent(comment.getContent());
			base.setEnabled(true);
			base.setEntered(new Date());
			base.setEnteredById(userId);
			base.setEntity("");
			base.setLinkedId(shareId);
			base.setModified(new Date());
			base.setModifiedById(userId);
			
			DcpCommentExt ext = new DcpCommentExt();
			ext.setLikecount(0);
			ext.setDislikecount(0);
			ext.setReplyCommentId(comment.getReplyId());
			ext.setLocation(location);
			
			shareHistoryDao.AddComment(base, ext);
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		return res;
	}
	
	public CommentsResponse GetComments(int userId, int assetType, int page, int pageSize, String title)
	{
		CommentsResponse res = new CommentsResponse();
		try
		{
			List<KeyValuePair<OsfComments, DcpCommentExt>> objs = shareHistoryDao.GetComments(assetType, page, pageSize, title);
			
			List<CommentFull> comments = new LinkedList<CommentFull>();
			if(objs.size() == 0){
				User user = userHelper.GetUser(new Long(userId).intValue());
				CommentFull cf = new CommentFull();
				cf.setUser(user);
				
				comments.add(cf);
			}
			for(KeyValuePair<OsfComments, DcpCommentExt> obj: objs)
			{
				CommentFull cf = new CommentFull();
				
				OsfComments oc = obj.getKey();
				DcpCommentExt dce = obj.getValue();
				
				Comment com = new Comment();
				com.setId(oc.getId().intValue());
				com.setDate(oc.getEntered());
				com.setContent(oc.getContent());
				com.setReplyId(dce.getReplyCommentId());
				
				cf.setComment(com);
				User user = userHelper.GetUser(new Long(oc.getEnteredById()).intValue());
				
				cf.setUser(user);
				
				comments.add(cf);
			}
			res.setComments(comments);
			
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
		}
		
		return res;
	}
	
	private UserHelper userHelper = new UserHelper();
}
