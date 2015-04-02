package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/message")
@Produces("application/json")
public interface message {
	@POST
	@Path("/UserMessage")
	@Produces("application/json")
	@Consumes("application/json")
	Response SendUserMessage(Message msg);
	
	@POST
	@Path("/SysMessage")
	@Produces("application/json")
	@Consumes("application/json")
	Response SendSystemMessage(SysMessage msg);
	
	@GET
	@Path("/MyMessages")
	@Produces("application/json")
	@Consumes("application/json")
	Response MyMessages();
	
	@POST
	@Path("/ResponseMessages")
	@Produces("application/json")
	@Consumes("application/json")
	Response ResponseMessages(ResponseOption option);
	
	@GET
	@Path("/HasMessage")
	@Produces("application/json")
	@Consumes("application/json")
	String HasMessage();
	
	@POST
	@Path("/RegisterDevice")
	@Produces("application/json")
	@Consumes("application/json")
	Response RegisterDevice(RegisterDeviceOption option);
	
	@GET
	@Path("/UnregisterDevice/{deviceguid}")
	@Produces("application/json")
	@Consumes("application/json")
	Response UnregisterDevice(@PathParam("deviceguid") String deviceGuid);
}
