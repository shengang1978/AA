package com.divx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.divx.service.model.Party;
import com.divx.service.model.PartyBaseType;

@Path("/party")
@Produces("application/json")
public interface party {
	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	Response CreateParty(Party party);
	
	@GET
	@Path("/{partyId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response GetParty(@PathParam("partyId")int partyId);
	
	@PUT
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	Response UpdateParty(Party party);
	
	@DELETE
	@Path("/{partyId}")
	@Consumes("application/json")
	@Produces("application/json")
	Response DeleteParty(@PathParam("partyId")int partyId);
	
	@GET
	@Path("/MyParties/{queryType}")
	@Consumes("application/json")
	@Produces("application/json")
	Response MyParties(@PathParam("queryType") PartyBaseType.ePartyQueryType queryType, @QueryParam("startPos") int startPos, @QueryParam("endPos") int endPos);
}
