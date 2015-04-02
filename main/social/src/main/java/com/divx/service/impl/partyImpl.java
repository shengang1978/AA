package com.divx.service.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.party;
import com.divx.service.domain.manager.PartyManager;
import com.divx.service.model.PartiesResponse;
import com.divx.service.model.Party;
import com.divx.service.model.PartyBaseType.ePartyQueryType;
import com.divx.service.model.GroupResponse;
import com.divx.service.model.PartyResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

public class partyImpl implements party {

	private PartyManager partyManager;

	@Autowired
	public void setPartyManager(PartyManager partyManager) {
		this.partyManager = partyManager;
	}
	@Override
	public Response CreateParty(Party party) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(partyManager.CreateParty(helper.getUserId(), party));
	}

	@Override
	public Response GetParty(int partyId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new PartyResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(partyManager.GetParty(helper.getUserId(), partyId));
	}

	@Override
	public Response UpdateParty(Party party) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(partyManager.UpdateParty(helper.getUserId(), party));
	}

	@Override
	public Response DeleteParty(int partyId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(partyManager.DeleteParty(helper.getUserId(), partyId));
	}

	@Override
	public Response MyParties(ePartyQueryType queryType, int startPos,
			int endPos) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new PartiesResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(partyManager.GetParties(helper.getUserId(), queryType, startPos, endPos));
	}

	
}
