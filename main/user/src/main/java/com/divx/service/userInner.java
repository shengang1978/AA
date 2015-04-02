package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.divx.service.model.OrgSite;
import com.divx.service.model.UserPhotoOption;
import com.divx.service.model.UserProfile;

@Path("/userInner")
@Produces("application/json")
@Consumes("application/json")
public interface userInner {
	@GET
	@Path("/UserProfile/{organizationId}/{username}")
	@Consumes("application/json")
	Response GetUserProfile(@PathParam("organizationId") int organizationId, @PathParam("username") String username);
	
	@PUT
	@Path("/UserProfile/{organizationId}/{username}")
	@Consumes("application/json")
	Response SetUserProfile(@PathParam("organizationId") int organizationId, @PathParam("username") String username, UserProfile profile);

	@GET
	@Path("/CheckUser/{token}")
	@Consumes("application/json")
	Response CheckUser(@PathParam("token") String token);
	
	@GET
	@Path("/{userId}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	Response GetUser(@PathParam("userId") int userId);
	
	@PUT
	@Path("/UpdateUserPhoto")
	@Consumes("application/json")
	Response UpdateUserPhoto(UserPhotoOption option);
	
	@GET
	@Path("/sites")
	@Consumes("application/json")
	Response GetOrgSites(@QueryParam("searchKey") String searchKey, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@PUT
	@Path("/site")
	@Consumes("application/json")
	Response SetOrgSite(OrgSite site);
}
