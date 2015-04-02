package com.divx.service;

import javax.jws.WebService;
import javax.ws.rs.*;

import com.divx.service.model.*;

@SuppressWarnings("restriction")
@WebService(name = "edit", targetNamespace = "http://service.divx.com/")
@Path("/edit")
@Produces("application/json")
public interface edit {
	@POST
	@Path("/BeginEdit/{assetId}")
	@Consumes("application/json")
	ServiceResponse BeginEdit(@PathParam("assetId") int assetId, String location);
	
	@DELETE
	@Path("/CancelEdit/{assetId}")
	@Consumes("application/json")
	ServiceResponse CancelEdit(@PathParam("assetId") int assetId);		
}
