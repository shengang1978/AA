package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/transcode")
@Produces("application/json")
public interface transcode {
	
	@POST
	@Path("/transcode/{assetId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response BeginTranscode(@PathParam("assetId") int assetId, String location);
	
	@POST
	@Path("/transcodeExt")
	@Consumes("application/json")
	@Produces("application/json")
	Response TranscodeExt(TransOption option);
	
	@GET
	@Path("/process/{assetId}")
	@Consumes("application/json")
	Response GetPrecess(@PathParam("assetId") int assetId);
	
	@POST
	@Path("/thumbnail")
	@Consumes("application/json")
	Response GenerateThumbnails(ThumbnailOptions option);
}
