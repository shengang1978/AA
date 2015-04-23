package com.divx.service;

import com.divx.service.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces("application/json")
@Consumes("application/json")
public interface user {
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	Response Register(RegisterOption option);
	
	@PUT
	@Path("/Login/{name}/{pwd}")
	@Consumes("application/json")
	Response Login(@PathParam("name") String username, @PathParam("pwd") String password);
	
	@PUT
	@Path("/OAuthLogin")
	@Consumes("application/json")
	Response OAuthLogin(OAuthOption option);
	
	@PUT
	@Path("/Logout")
	@Consumes("application/json")
	Response Logout();
	
	@GET
	@Path("/FindUsers/{eFindOption}")
	@Consumes("application/json")
	Response FindUsers(@PathParam("eFindOption") FindUserOption.eFindOption option, @QueryParam("searchKey") String searchKey, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
//	@PUT
//	@Path("/")
//	@Consumes("application/json")
//	Response UpdateUserInfo(UserInfo option);
	
	@GET
	@Path("/")
	@Consumes("application/json")
	Response GetUserInfo();
	
	@PUT
	@Path("/UpdateUsername")
	@Consumes("application/json")
	Response UpdateUsername(UserOption option);
	
	@GET
	@Path("/ResetPassword/{OptionType}/{value}")
	@Consumes("application/json")
	Response ResetPassword(@PathParam("OptionType") UserOption.OptionType option, @PathParam("value") String value);
	
	@PUT
	@Path("/ChangePassword")
	@Consumes("application/json")
	Response ChangePassword(ChangePasswordOption option);
	
	@GET
	@Path("/Startup")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response Startup();
	
	@GET
	@Path("/MyProfile")
	@Consumes("application/json")
	Response GetMyProfile();
	
	@PUT
	@Path("/MyProfile")
	@Consumes("application/json")
	Response SetMyProfile(UserProfile profile);

}
