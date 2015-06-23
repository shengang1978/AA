package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/share")
@Produces("application/json")
@Consumes("application/json")
public interface share {
	@POST
	@Path("/ShareMedia")
	Response ShareMedia(ShareOption option);
	
	@GET
	@Path("/History/{shareId}")
	Response History(@PathParam("shareId") int shareId,
			@QueryParam("startPos") int startPos,
			@QueryParam("endPos") int endPos);

	@POST
	@Path("/Comment/{shareId}")
	Response Comment(@PathParam("shareId") int shareId, String comment);
	
	@POST
	@Path("/Like")
	Response Like(com.divx.service.model.Like like);

	@GET
	@Path("/MyShares/{QueryType}")
	Response MyShares(@PathParam("QueryType") QueryOption.QueryType option, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/PublicShares/{MediaType}/{SearchType}")
	Response GetPublicShares(@PathParam("MediaType") QueryOption.MediaType option,@PathParam("SearchType") QueryOption.SearchType searchOption,@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/SharesInGroup/{groupId}")
	Response SharesInGroup(@PathParam("groupId") int groupId,@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@DELETE
	@Path("/Remove/{shareId}")
	Response Remove(@PathParam("shareId") int shareId);
	
	@GET
	@Path("/UserShares/{userId}")
	Response UserShares(@PathParam("userId") int userId, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
}
