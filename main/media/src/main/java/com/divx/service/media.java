package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;

@Path("/media")
@Produces("application/json")
public interface media {
	@POST
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	Response CreateMedia(MediaBase media);
	
	@PUT
	@Path("/")
	@Produces("application/json")
	@Consumes("application/json")
	Response UpdateMedia(MediaBase media);
	
	@PUT
	@Path("/UpdateUploadInfo")
	@Produces("application/json")
	@Consumes("application/json")
	Response UpdateUploadInfo(Upload uploadinfo);
	
	@GET
	@Path("/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response GetMedia(@PathParam("mediaId") int mediaId);
	
	@GET
	@Path("/RecommendMedias")
	@Produces("application/json")
	@Consumes("application/json")
	Response GetRecommendMedias(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/MyMedias")
	@Produces("application/json")
	@Consumes("application/json")
	Response MyMedias(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/MyScores")
	@Produces("application/json")
	@Consumes("application/json")
	Response MyScores();
	
	@GET
	@Path("/MyStories")
	@Produces("application/json")
	@Consumes("application/json")
	Response MyStories(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/UploadInfo/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response GetUploadInfo(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelUpload/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response CancelUpload(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CompleteUpload/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response CompleteUpload(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/EditAndPublish/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response EditAndPublish(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelEdit/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response CancelEdit(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/Publish/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response Publish(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelPublish/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response CancelPublish(@PathParam("mediaId") int mediaId);
	
	@GET
	@Path("/GetMediaStatus/{mediaId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response GetMediaStatus(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/EndPublish")
	@Consumes("application/json")
	Response EndPublish(EndPublishOption option);
	
	@PUT
	@Path("/EndEdit/{assetId}")
	@Produces("application/json")
	@Consumes("application/json")
	Response EndEdit(@PathParam("assetId") int assetId, String assetLocation);
	
	@DELETE
	@Path("/{mediaId}")
	@Consumes("application/json")
	Response DeleteMedia(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/TransferMedia")
	@Consumes("application/json")
	@Produces("application/json")
	Response TransferMedia(TransferOption option);
}
