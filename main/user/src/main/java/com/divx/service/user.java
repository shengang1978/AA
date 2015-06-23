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
	Response Register(RegisterOption option);
	
	@PUT
	@Path("/Login/{name}/{pwd}")
	Response Login(@PathParam("name") String username, @PathParam("pwd") String password);
	
	@PUT
	@Path("/OAuthLogin")
	Response OAuthLogin(OAuthOption option);
	
	@PUT
	@Path("/Logout")
	Response Logout();
	
	@GET
	@Path("/FindUsers/{eFindOption}")
	Response FindUsers(@PathParam("eFindOption") FindUserOption.eFindOption option, @QueryParam("searchKey") String searchKey, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/ListUsers")
	Response ListUsers(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/")
	Response GetUserInfo();
	
	@PUT
	@Path("/UpdateUsername")
	Response UpdateUsername(UserOption option);
	
	@GET
	@Path("/ResetPassword/{OptionType}/{value}")
	Response ResetPassword(@PathParam("OptionType") UserOption.OptionType option, @PathParam("value") String value);
	
	@PUT
	@Path("/ChangePassword")
	Response ChangePassword(ChangePasswordOption option);
	
	@GET
	@Path("/Startup")
	Response Startup();
	
	@GET
	@Path("/MyProfile")
	Response GetMyProfile();
	
	@GET
	@Path("/ValidateToken")
	Response ValidateToken(); 

	@PUT
	@Path("/MyProfile")
	Response SetMyProfile(UserProfile profile);
	
	@GET
	@Path("/{userId}")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	Response GetUser(@PathParam("userId") int userId);

}
