package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/stat")
@Produces("application/json")
@Consumes("application/json")
public interface stat {
	
	@GET
	@Path("/StoryPlayStat")
	Response GetStoryPlayStat(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
}
