package org.androidpn.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.androidpn.service.model.Notification;

@WebService(name = "notification", targetNamespace = "http://service.androidpn.org/")
@Path("/notification")
@Produces("application/json")
public interface notification {
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	Response sendNotification(Notification notify);
	
}
