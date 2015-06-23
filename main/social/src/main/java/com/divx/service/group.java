package com.divx.service;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/group")
@Consumes("application/json")
@Produces("application/json")
public interface group {
	@POST
	@Path("/")
	Response CreateGroup(GroupOption option);
	
	@DELETE
	@Path("/{groupId}")
	Response DeleteGroup(@PathParam("groupId") int groupId);
	
	@PUT
	@Path("/")
	Response UpdateGroup(Group group);
	
	@PUT
	@Path("/SetGroupPhotoUrl")
	Response SetGroupPhotoUrl(Group group);
	@GET
	@Path("/MyGroups/{QueryType}")
	Response MyGroups(@PathParam("QueryType") QueryOption.QueryType option, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@POST
	@Path("/AddUserToGroup/{groupId}/{userId}")
	Response AddUserToGroup(@PathParam("groupId") int groupId, @PathParam("userId") int userId);
	
	@DELETE
	@Path("/RemoveUser/{groupId}/{userId}")
	Response RemoveUser(@PathParam("groupId") int groupId, @PathParam("userId") int userId);
	
	@GET
	@Path("/GetUsers/{groupId}/{QueryType}")
	Response GetUsers(@PathParam("groupId") int groupId, @PathParam("QueryType") QueryUserOption.QueryType option);
	
	@GET
	@Path("/GetGroupRoles")
	Response GetGroupRoles();
	
	@GET
	@Path("/MyRole/{groupId}")
	Response GetMyRoleInGroup(@PathParam("groupId") int groupId);
	
	@PUT
	@Path("/SetRoleToUser/{groupId}/{userId}/{role}")
	Response SetRoleToUser(@PathParam("groupId") int groupId, @PathParam("userId") int userId, @PathParam("role") GroupRole groupRole);
	
	@POST
	@Path("/AddUsersToGroup")
	Response AddUsersToGroup(UsersOption usersOption);
}
