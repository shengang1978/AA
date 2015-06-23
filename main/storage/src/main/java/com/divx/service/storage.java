package com.divx.service;


import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;
import com.divx.service.model.EndPublishOption;

@WebService
@Path("/storage")
@Produces("application/json")
@Consumes("application/json")
public interface storage {

	@GET
	@Path("/GetUploadInfo/{mediaId}")
	Response GetUploadInfo(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelUpload/{mediaId}")
	Response CancelUpload(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/EndPublish")
	Response EndPublish(EndPublishOption endPublishOption);
}
