package com.divx.service.domain.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.domain.dao.PartyDao;
import com.divx.service.model.PartiesResponse;
import com.divx.service.model.Party;
import com.divx.service.model.PartyBaseType;
import com.divx.service.model.PartyResponse;
import com.divx.service.model.ServiceResponse;

@Service
public class PartyManager {
	private PartyDao partyDao;

	@Autowired
	public void setPartyDao(PartyDao partyDao) {
		this.partyDao = partyDao;
	}
	
	public ServiceResponse CreateParty(int userId, Party party)
	{
		ServiceResponse res = new ServiceResponse();
		return res;
	}
	
	public ServiceResponse UpdateParty(int userId, Party party)
	{
		ServiceResponse res = new ServiceResponse();
		return res;
	}
	
	public ServiceResponse DeleteParty(int userId, int partyId)
	{
		ServiceResponse res = new ServiceResponse();
		return res;
	}
	
	public PartyResponse GetParty(int userId, int partyId)
	{
		PartyResponse res = new PartyResponse();
		return res;
	}
	
	public PartiesResponse GetParties(int userId, PartyBaseType.ePartyQueryType queryType, int startPos, int endPos)
	{
		PartiesResponse res = new PartiesResponse();
		
		return res;
	}
}
