package com.divx.service.domain.manager;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.Util;
import com.divx.service.domain.model.DcpStoryplayTotalstat;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.model.RankStatResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.StoryPlayStat;
import com.divx.service.model.StoryPlayStatResponse;

@Service
public class RankStatManager {
	private static final Logger log = Logger.getLogger(RankStatManager.class);
	
	private MediaDao mediaDao;
	
	@Autowired
	public void setMediaDao(MediaDao mediaDao)
	{
		this.mediaDao = mediaDao;
	}
	
	public int AddStoryPlayHit(int mediaId)
	{
		try
		{
			return mediaDao.AddStoryPlayHit(mediaId);
		}
		catch(Exception ex)
		{
			Util.LogError(log, String.format("Exception to AddStoryPlayHit(%d)", mediaId), ex);
		}
		
		return 0;
	}
	
	public int GetStoryPlayHit(int mediaId)
	{
		try
		{
			DcpStoryplayTotalstat obj = mediaDao.GetStoryPlayStat(mediaId);
			if (obj != null && obj.getPlaycount() != null)
			{
				return obj.getPlaycount();
			}
		}
		catch(Exception ex)
		{
			Util.LogError(log, String.format("Exception to AddStoryPlayHit(%d)", mediaId), ex);
		}
		
		return 0;
	}
	
	public StoryPlayStatResponse GetStoryPlayStat(int nStartPos, int nEndPos)
	{
		StoryPlayStatResponse res = new StoryPlayStatResponse();
		
		try
		{
			res.setSort(StoryPlayStatResponse.eSort.D);
			res.setStatType(StoryPlayStatResponse.eStatType.StoryPlayRank);
			
			List<StoryPlayStat> items = new LinkedList<StoryPlayStat>();
			
			List<DcpStoryplayTotalstat> objs = mediaDao.GetStoryPlayTotalstat(nStartPos, nEndPos, true);
			
			if (objs != null && objs.size() > 0)
			{
				for(DcpStoryplayTotalstat obj: objs)
				{
					items.add(MediaHelper.ToStoryPlayStat(obj, new StoryPlayStat()));
				}
			}
			
			res.setItems(items);
			res.setCount(items.size());
			res.setStartPos(nStartPos);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Exception. " + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception to GetStoryPlayStat(%d, %d", nStartPos, nEndPos), ex);
		}
		
		return res;
	}

	public StoryPlayStat GetStoryPlayStat(int mediaId)
	{
		StoryPlayStat stat = new StoryPlayStat();
		
		DcpStoryplayTotalstat obj = mediaDao.GetStoryPlayStat(mediaId);
		
		if (obj != null)
		{
			MediaHelper.ToStoryPlayStat(obj, stat);
		}
		else
		{
			stat.setMediaId(mediaId);
			stat.setPlayCount(0);
		}
		
		return stat;
	}
}
