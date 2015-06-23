package com.divx.service.impl;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.Util;
import com.divx.service.stat;
import com.divx.service.domain.manager.RankStatManager;

public class statImpl implements stat {

	private RankStatManager rankStatManager;
	
	@Autowired
	public void setRankStatManager(RankStatManager rankStatManager)
	{
		this.rankStatManager = rankStatManager;
	}
	
	@Override
	public Response GetStoryPlayStat(int startPos, int endPos) {
		return Util.ServiceResponseToResponse(rankStatManager.GetStoryPlayStat(startPos, endPos));
	}

}
