package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.Util;
import com.divx.service.domain.model.DcpLesson;
import com.divx.service.domain.model.DcpScore;
import com.divx.service.domain.model.DcpScorestat;
import com.divx.service.domain.model.DcpTotalstat;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.edu.EduStatResponse;
import com.divx.service.model.edu.Score;
import com.divx.service.model.edu.ScoreStat;
import com.divx.service.model.edu.SetResponse;
import com.divx.service.model.edu.ScoresResponse;

@Service
public class ScoreManager {
	private static final Logger log = Logger.getLogger(ScoreManager.class);
	
	private MediaDao mediaDao;
	
	@Autowired
	public void setMediaDao(MediaDao mediaDao)
	{
		this.mediaDao = mediaDao;
	}
	
	public EduStatResponse GetScoreStat(int userId)
	{
		EduStatResponse res = CacheManager.GetInstance().GetCacheEduStat(userId);
		
		if (res != null)
			return res;
		try
		{
			res = new EduStatResponse();
			//List<DcpScorestat> objs = mediaDao.GetStats(userId, ScoreStat.eStatDuration.Today);
			DcpTotalstat ts = mediaDao.GetTotalStat(userId);
			
			//if (objs != null && objs.size() > 0)
			{
				List<ScoreStat> stat = new LinkedList<ScoreStat>();
			//	stat.add(ScoreHelper.ToScoreStat(objs, new ScoreStat()));
				stat.add(ScoreHelper.ToScoreStat(ts, new ScoreStat()));
				
				res.setScoreStat(stat);
			}

			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			CacheManager.GetInstance().SetCacheEduStat(userId, res);
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(String.format("Internal Error. %s", Util.getStackTrace(ex)));
			Util.LogError(log, String.format("ScoreStat(%d) Exception: ", userId), ex);
		}
		
		return res;
	}
	
	public ScoresResponse GetScore(int scoreId)
	{
		ScoresResponse res = new ScoresResponse();
		
		try
		{
			DcpScore score = mediaDao.GetScore(scoreId);
			if (score != null)
			{
				List<Score> objs = new LinkedList<Score>();
				objs.add(ScoreHelper.ToScore(score));
				
				res.setScores(objs);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid score Id");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(String.format("Internal Error. %s", Util.getStackTrace(ex)));
			Util.LogError(log, String.format("GetScore(%d) Exception: ", scoreId), ex);
		}
		
		return res;
	}
	
	public ScoresResponse GetScoreOfLesson(int userId, int lessonId)
	{
		ScoresResponse res = CacheManager.GetInstance().GetCacheScoresOfLesson(userId, lessonId);
		
		if (res != null)
			return res;
		
		res = new ScoresResponse();
		
		try
		{
			DcpScore score = mediaDao.GetScore(userId, lessonId);
			if (score != null)
			{
				List<Score> objs = new LinkedList<Score>();
				objs.add(ScoreHelper.ToScore(score));
				
				res.setScores(objs);
			}
			else
			{
				List<Score> objs = new LinkedList<Score>();
				Score o = new Score();
				o.setDate(new Date());
				objs.add(o);
				
				res.setScores(objs);
			}
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
			CacheManager.GetInstance().SetCacheScoresOfLesson(userId, lessonId, res);
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(String.format("Internal Error. %s", Util.getStackTrace(ex)));
			Util.LogError(log, String.format("GetScoreOfLesson(%d, %d) Exception: ", userId, lessonId), ex);
		}
		
		return res;
	}
	
	public ScoresResponse GetScoresOfMedia(int userId, int mediaId)
	{
		ScoresResponse res = CacheManager.GetInstance().GetCacheScoresOfMedia(userId, mediaId);
		
		if (res != null)
			return res;
		
		res = new ScoresResponse();
		
		try
		{
			List<DcpScore> scores = mediaDao.GetScores(userId, mediaId);
			if (scores != null)
			{
				List<Score> objs = new LinkedList<Score>();
				
				for(DcpScore score: scores)
					objs.add(ScoreHelper.ToScore(score));
				
				res.setScores(objs);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
				CacheManager.GetInstance().SetCacheScoresOfMedia(userId, mediaId, res);
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid userId or lessonId");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(String.format("Internal Error. %s", Util.getStackTrace(ex)));
			Util.LogError(log, String.format("GetScoresOfMedia(%d, %d) Exception: ", userId, mediaId), ex);
		}
		
		return res;
	}
	
	public SetResponse SetScore(int userId, Score score)
	{
		SetResponse res = new SetResponse();
		
		try
		{
			if (score.getLessonId() <= 0)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid lessonId");
				return res;
			}
			
			DcpLesson lesObj = mediaDao.GetLesson(score.getLessonId());
			if (lesObj == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid lessonId");
				return res;
			}
			
			DcpScore obj = null;
			
			if (score.getScoreId() > 0)
			{
				DcpScore ds = mediaDao.GetScore(score.getScoreId());
				if (ds == null)
				{
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid ScoreId");
					return res;
				}
				obj = ScoreHelper.ToScore(score, ds);
			}
			else
			{
				obj = ScoreHelper.ToScore(score, new DcpScore());
				obj.setDatecreated(new Date());
				obj.setUserId(userId);
			}
			obj.setDatemodified(new Date());
			obj.setDcpLesson(lesObj);
			int nScoreId = mediaDao.SetScore(obj);
			if (nScoreId > 0)
			{
				CacheManager.GetInstance().ScoreCacheInvalidate(userId);
				res.setId(nScoreId);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid userId or lessonId");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(String.format("Internal Error. %s", Util.getStackTrace(ex)));
			Util.LogError(log, String.format("SetScore(%d, %s) Exception: ", userId, Util.ObjectToJson(score)), ex);
		}
		
		return res;
	}
}
