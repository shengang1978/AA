package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.LessonsOption;

@Path("/lesson")
@Produces("application/json")
@Consumes("application/json")
public interface lesson {
	@POST
	@Path("/")
	Response CreateLesson(Lesson lesson);
	
	@PUT
	@Path("/")
	Response UpdateLesson(Lesson lesson);
	
	@GET
	@Path("/{lessonId}")
	Response GetLesson(@PathParam("lessonId") int lessonId);
	
	@DELETE
	@Path("/{lessonId}")
	Response DeleteLesson(@PathParam("lessonId") int lessonId);
	
	@GET
	@Path("/Lessons/{mediaId}")
	Response GetLessons(@PathParam("mediaId")int mediaId);
}
