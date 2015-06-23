package com.divx.service;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.divx.service.model.CommentOption;

@Path("/comment")
@Produces("application/json")
@Consumes("application/json")
public interface comment {
	@POST
	@Path("/")
	Response AddComment(CommentOption option);	
	
	@GET
	@Path("/{assetType}")
	Response GetComments(@PathParam("assetType") int assetType, @QueryParam("page") int page, @QueryParam("pageSize") int pageSize, @QueryParam("title") String title);
}
