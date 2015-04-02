package com.divx.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.comment;
import com.divx.service.domain.manager.CommentManager;
import com.divx.service.model.Comment;
import com.divx.service.model.CommentAsset;
import com.divx.service.model.CommentFull;
import com.divx.service.model.CommentOption;
import com.divx.service.model.CommentsResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.User;

public class commentImpl implements comment {

	private CommentManager commentManager;

	@Autowired
	public void setCommentManager(CommentManager commentManager) {
		this.commentManager = commentManager;
	}

	@Override
	public Response AddComment(CommentOption option) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{	
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		
		return Util.ServiceResponseToResponse(commentManager.AddComment(helper.getUserId(), helper.getLocalAddr(), option));
	}

	@Override
	public Response GetComments(int assetType, int page, int pageSize,
			String title) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{	
			CommentsResponse res = new CommentsResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		
		return Util.ServiceResponseToResponse(commentManager.GetComments(helper.getUserId(), assetType, page, pageSize, title));
	}

}
