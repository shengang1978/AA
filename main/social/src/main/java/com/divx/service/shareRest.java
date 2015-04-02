package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/share")
@Produces("application/json")
@Consumes("application/json")
public interface shareRest {
	@POST
	@Path("/ShareMedia")
	@Consumes("application/json")
	@Produces("application/json")
	Response ShareMedia(ShareOption option);
	
	@GET
	@Path("/History/{shareId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response History(@PathParam("shareId")  int shareId);
	
	@POST
	@Path("/Comment/{shareId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response Comment(@PathParam("shareId") int shareId, String comment);
	
	@POST
	@Path("/Like/{shareId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response Like(@PathParam("shareId") int shareId, String like);

	@GET
	@Path("/MyShares/{QueryType}")
	@Consumes("application/json")
	@Produces("application/json")
	Response MyShares(@PathParam("QueryType") QueryOption.QueryType option);
	
	@GET
	@Path("/PublicShares")
	@Consumes("application/json")
	@Produces("application/json")
	Response GetPublicShares(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/SharesInGroup/{groupId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response SharesInGroup(@PathParam("groupId") int groupId);
	
	@DELETE
	@Path("/Remove/{groupId}/{shareId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response Remove(@PathParam("groupId") int groupId, @PathParam("shareId") int shareId);
}
