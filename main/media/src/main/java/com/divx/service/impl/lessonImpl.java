package com.divx.service.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.lesson;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.manager.ScoreManager;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.LessonsOption;

public class lessonImpl implements lesson {

	private MediaManager mediaManager;
	private ScoreManager scoreManager;
		
	@Autowired
	public void setMediaManager(MediaManager mediaManager)
	{
		this.mediaManager = mediaManager;
	}
	
	@Autowired
	public void setScoreManager(ScoreManager scoreManager)
	{
		this.scoreManager = scoreManager;
	}
	
	@Override
	public Response GetLessons(int mediaId) {
		return Util.ServiceResponseToResponse(mediaManager.GetLessons(mediaId));
	}

	@Override
	public Response GetLesson(int lessonId) {
		return Util.ServiceResponseToResponse(mediaManager.GetLesson(lessonId));
	}

	@Override
	public Response DeleteLesson(int lessonId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		return Util.ServiceResponseToResponse(mediaManager.DeleteLesson(helper.getUserId(), lessonId));
	}

	@Override
	public Response CreateLesson(Lesson lesson) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		return Util.ServiceResponseToResponse(mediaManager.SetLesson(helper.getUserId(), lesson));
	}

	@Override
	public Response UpdateLesson(Lesson lesson) {
	
		return Util.ServiceResponseToResponse(mediaManager.UpdateLessonInfo(lesson));
	}
}
