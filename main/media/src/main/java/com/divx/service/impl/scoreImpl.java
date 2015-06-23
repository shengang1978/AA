package com.divx.service.impl;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.score;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.manager.ScoreManager;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.edu.Score;

public class scoreImpl implements score {

	private ScoreManager scoreManager;
	
	@Autowired
	public void setScoreManager(ScoreManager scoreManager)
	{
		this.scoreManager = scoreManager;
	}
	
	@Override
	public Response SetScore(Score score) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(scoreManager.SetScore(helper.getUserId(), score));
	}

	@Override
	public Response GetScore(int scoreId) {

		return Util.ServiceResponseToResponse(scoreManager.GetScore(scoreId));
	}

	@Override
	public Response ScoreStat(int userId) {

		return Util.ServiceResponseToResponse(scoreManager.GetScoreStat(userId));
	}

	@Override
	public Response GetScoreOfLesson(int userId, int lessonId) {
		
		return Util.ServiceResponseToResponse(scoreManager.GetScoreOfLesson(userId, lessonId));
	}
	@Override
	public Response MyScoreStat() {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(scoreManager.GetScoreStat(helper.getUserId()));
	}

	@Override
	public Response GetScoresOfMedia(int mediaId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(scoreManager.GetScoresOfMedia(helper.getUserId(), mediaId));
	}

}
