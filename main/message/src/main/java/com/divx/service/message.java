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
@Consumes("application/json")
public interface message {
	@POST
	@Path("/UserMessage")
	Response SendUserMessage(Message msg);
	
	@POST
	@Path("/SysMessage")
	Response SendSystemMessage(SysMessage msg);
	
	@GET
	@Path("/MyMessages")
	Response MyMessages();
	
	@POST
	@Path("/ResponseMessages")
	Response ResponseMessages(ResponseOption option);
	
	@GET
	@Path("/HasMessage")
	String HasMessage();
	
	@POST
	@Path("/RegisterDevice")
	Response RegisterDevice(RegisterDeviceOption option);
	
	@GET
	@Path("/UnregisterDevice/{deviceguid}")
	Response UnregisterDevice(@PathParam("deviceguid") String deviceGuid);
}
