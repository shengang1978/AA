package com.divx.service;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import com.divx.service.model.*;
import com.divx.service.model.edu.BookOption;
import com.divx.service.model.ugc.Book;

@Path("/media")
@Produces("application/json")
@Consumes("application/json")
public interface media {
	@POST
	@Path("/")
	Response CreateMedia(MediaBase media);
	
	@PUT
	@Path("/")
	Response UpdateMedia(MediaBase media);
	
	@PUT
	@Path("/UpdateUploadInfo")
	Response UpdateUploadInfo(Upload uploadinfo);
	
	@GET
	@Path("/{mediaId}")
	Response GetMedia(@PathParam("mediaId") int mediaId);
	
	@GET
	@Path("/RecommendMedias")
	Response GetRecommendMedias(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/PublicMedias")
	Response GetPublicMedias(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/MyMedias")
	Response MyMedias(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/MyStories")
	Response MyStories(@QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
	
	@GET
	@Path("/UploadInfo/{mediaId}")
	Response GetUploadInfo(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelUpload/{mediaId}")
	Response CancelUpload(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CompleteUpload/{mediaId}")
	Response CompleteUpload(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/EditAndPublish/{mediaId}")
	Response EditAndPublish(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelEdit/{mediaId}")
	Response CancelEdit(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/Publish/{mediaId}")
	Response Publish(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/CancelPublish/{mediaId}")
	Response CancelPublish(@PathParam("mediaId") int mediaId);
	
	@GET
	@Path("/GetMediaStatus/{mediaId}")
	Response GetMediaStatus(@PathParam("mediaId") int mediaId);
	
	@PUT
	@Path("/EndPublish")
	Response EndPublish(EndPublishOption option);
	
	@PUT
	@Path("/EndEdit/{assetId}")
	Response EndEdit(@PathParam("assetId") int assetId, String assetLocation);
	
	@DELETE
	@Path("/{mediaId}")
	Response DeleteMedia(@PathParam("mediaId") int mediaId);
	
	@POST
	@Path("/TransferMedia")
	Response TransferMedia(TransferOption option);
	
	@PUT
	@Path("/DownloadCount")
	Response DownloadCount(DownloadCountOption option);
	
	@POST
	@Path("UpdateBookInfo")
	@Consumes("application/json")
	Response SetBookInfo(Book book);
	
	@GET
	@Path("/GetStory/{mediaId}")
	Response GetStory(@PathParam("mediaId") int mediaId);
	
}
