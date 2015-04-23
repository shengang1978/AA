package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.domain.model.OsfComments;
import com.divx.service.model.*;

@Path("/share")
@Produces("application/json")
@Consumes("application/json")
public interface share {
	@POST
	@Path("/ShareMedia")
	@Consumes("application/json")
	@Produces("application/json")
	Response ShareMedia(ShareOption option);
	
	@GET
	@Path("/History/{shareId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response History(@PathParam("shareId") int shareId,
			@QueryParam("startPos") int startPos,
			@QueryParam("endPos") int endPos);

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
	Response MyShares(@PathParam("QueryType") QueryOption.QueryType option, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/PublicShares")
	@Consumes("application/json")
	@Produces("application/json")
	Response GetPublicShares(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/SharesInGroup/{groupId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response SharesInGroup(@PathParam("groupId") int groupId,@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@DELETE
	@Path("/Remove/{shareId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response Remove(@PathParam("shareId") int shareId);
}
