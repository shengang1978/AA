package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/friend")
@Produces("application/json")
public interface friendRest {
	@POST
	@Path("/RequestFriend")
	@Consumes("application/json")
	@Produces("application/json")
	Response RequestFriend(FriendRequest request);
	
	@GET
	@Path("/MyFriendRequests")
	@Consumes("application/json")
	Response MyFriendRequests();
	
	
	@POST
	@Path("/RespondRequestFriend")
	@Consumes("application/json")
	Response RespondRequestFriend(RespondOperate oper);
	
	
	@POST
	@Path("/InviteUser")
	@Consumes("application/json")
	Response InviteUser(InviteOption option);
	
	@GET
	@Path("/MyFriends")
	@Consumes("application/json")
	Response MyFriends();
	
	@DELETE
	@Path("/UnbindFriend/{userId}")
	@Consumes("application/json")
	Response UnbindFriend(@PathParam("userId") int userId);
	
	@POST
	@Path("/RespondInviteUser/{username}/{password}")
	@Consumes("application/json")
	Response RespondInviteUser(@PathParam("username") String username,@PathParam("password") String password);
}
