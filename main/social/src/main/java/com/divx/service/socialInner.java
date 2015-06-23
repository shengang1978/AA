package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.divx.service.model.ShareOption;

@Path("/socialInner")
@Produces("application/json")
@Consumes("application/json")
public interface socialInner {
	@GET
	@Path("/UserFriends/{userId}")
	Response UserFriends(@PathParam("userId") int userId);
	
	@GET
	@Path("/UsersInGroup/{groupId}")
	Response UsersInGroup(@PathParam("groupId") int groupId);
	
	@POST
	@Path("/InnerShareMedia")
	Response InnerShareMedia(ShareOption option);
}
