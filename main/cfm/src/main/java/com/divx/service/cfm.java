package com.divx.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@WebService(name = "cfm", targetNamespace = "http://service.divx.com/")
@Path("/cfm")
@Produces("application/json")
public interface cfm {
	
	@GET
	@Path("/ConfigValue/{key}")
	@Produces("application/json")
	@Consumes("application/json")
	Response GetConfigValue(@PathParam("key") String configKey);
	
	@GET
	@Path("/Configs")
	@Produces("application/json")
	@Consumes("application/json")
	Response GetConfigs();
}
