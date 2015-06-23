package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.divx.service.model.edu.Score;


@Path("/score")
@Produces("application/json")
@Consumes("application/json")
public interface score {
	@PUT
	@Path("/")
	Response SetScore(Score score);
	
	@GET
	@Path("/{scoreId}")
	Response GetScore(@PathParam("scoreId") int scoreId);
	
	@GET
	@Path("/Scores/{mediaId}")
	Response GetScoresOfMedia(@PathParam("mediaId") int mediaId);
	
	@GET
	@Path("/Score/{userId}/{lessonId}")
	Response GetScoreOfLesson(@PathParam("userId") int userId, @PathParam("lessonId") int lessonId);
	
	@GET
	@Path("/ScoreStat/{userId}")
	Response ScoreStat(@PathParam("userId") int userId);
	
	@GET
	@Path("/MyScoreStat")
	Response MyScoreStat();

}
