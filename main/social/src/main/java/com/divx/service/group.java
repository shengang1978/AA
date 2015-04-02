package com.divx.service;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/group")
@Produces("application/json")
public interface group {
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	Response CreateGroup(GroupOption option);
	
	@DELETE
	@Path("/{groupId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response DeleteGroup(@PathParam("groupId") int groupId);
	
	@PUT
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	Response UpdateGroup(Group group);
	
	@PUT
	@Path("/SetGroupPhotoUrl")
	@Consumes("application/json")
	@Produces("application/json")
	Response SetGroupPhotoUrl(Group group);
	@GET
	@Path("/MyGroups/{QueryType}")
	@Consumes("application/json")
	@Produces("application/json")
	Response MyGroups(@PathParam("QueryType") QueryOption.QueryType option, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@POST
	@Path("/AddUserToGroup/{groupId}/{userId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response AddUserToGroup(@PathParam("groupId") int groupId, @PathParam("userId") int userId);
	
	@DELETE
	@Path("/RemoveUser/{groupId}/{userId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response RemoveUser(@PathParam("groupId") int groupId, @PathParam("userId") int userId);
	
	@GET
	@Path("/GetUsers/{groupId}/{QueryType}")
	@Consumes("application/json")
	@Produces("application/json")
	Response GetUsers(@PathParam("groupId") int groupId, @PathParam("QueryType") QueryUserOption.QueryType option);
	
	@GET
	@Path("/GetGroupRoles")
	@Consumes("application/json")
	@Produces("application/json")
	Response GetGroupRoles();
	
	@GET
	@Path("/MyRole/{groupId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response GetMyRoleInGroup(@PathParam("groupId") int groupId);
	
	@PUT
	@Path("/SetRoleToUser/{groupId}/{userId}/{role}")
	@Consumes("application/json")
	@Produces("application/json")
	Response SetRoleToUser(@PathParam("groupId") int groupId, @PathParam("userId") int userId, @PathParam("role") GroupRole groupRole);
	
	@POST
	@Path("/AddUsersToGroup")
	@Consumes("application/json")
	@Produces("application/json")
	Response AddUsersToGroup(UsersOption usersOption);
}
