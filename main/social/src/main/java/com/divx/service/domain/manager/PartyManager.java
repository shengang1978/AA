package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.domain.dao.PartyDao;
import com.divx.service.domain.model.DcpParty;
import com.divx.service.model.CreatePartyResponse;
import com.divx.service.model.party.PartiesResponse;
import com.divx.service.model.party.Party;
import com.divx.service.model.party.PartyBaseType;
import com.divx.service.model.party.PartyResponse;
import com.divx.service.model.party.PartyBaseType.ePartyType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

@Service
public class PartyManager {
	private PartyDao partyDao;

	@Autowired
	public void setPartyDao(PartyDao partyDao) {
		this.partyDao = partyDao;
	}
	
	public CreatePartyResponse CreateParty(int userId, Party party)
	{
		CreatePartyResponse res = new CreatePartyResponse();
		
		try
		{
			DcpParty obj = new DcpParty();
			obj.setAddress(party.getAddress());
			obj.setDatecreated(new Date());
			obj.setDatemodified(new Date());
			obj.setDescription(party.getDescription());
			obj.setPartydate(party.getDate());
			obj.setPartytype(party.getPartyType().ordinal());
			obj.setTitle(party.getTitle());
			obj.setUserId(userId);
			int id = partyDao.CreateParty(obj);
			if (id > 0)
			{
				res.setPartyId(id);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("CreateParty Exception " + ex.getMessage());
		}
		
		return res;
	}
	
	public ServiceResponse UpdateParty(int userId, Party party)
	{
		ServiceResponse res = new ServiceResponse();
		
		try
		{
			DcpParty obj = partyDao.GetParty(party.getId());
			if (obj != null)
			{
				obj.setAddress(party.getAddress());
				//obj.setDatecreated(new Date());
				obj.setDatemodified(new Date());
				obj.setDescription(party.getDescription());
				obj.setPartydate(party.getDate());
				obj.setPartytype(party.getPartyType().ordinal());
				obj.setTitle(party.getTitle());
				obj.setUserId(userId);
				int id = partyDao.UpdateParty(obj);
				if (id > 0)
				{
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
				}
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format("Invalid party id"));
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("UpdateParty Exception " + ex.getMessage());
		}
		return res;
	}
	
	public ServiceResponse DeleteParty(int userId, int partyId)
	{
		PartyResponse res = new PartyResponse();
		try
		{
			int id = partyDao.DeleteParty(partyId);
			if (id > 0)
			{
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format("Invalid party id"));
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("DeleteParty Exception " + ex.getMessage());
		}
		return res;
	}
	
	public PartyResponse GetParty(int userId, int partyId)
	{
		PartyResponse res = new PartyResponse();
		try
		{
			DcpParty obj = partyDao.GetParty(partyId);
			if (obj != null)
			{
				res.setParty(ToParty(obj));
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage(String.format("Invalid party id"));
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("CreateParty Exception " + ex.getMessage());
		}
		return res;
	}
	
	public PartiesResponse GetParties(int userId, PartyBaseType.ePartyQueryType queryType, int startPos, int endPos)
	{
		PartiesResponse res = new PartiesResponse();
	
		try
		{
			List<DcpParty> parties = partyDao.GetParties(userId);
			if (parties != null)
			{
				int fromIndex = startPos;
				int toIndex = endPos;
				if (fromIndex >= parties.size())
				{
					fromIndex = parties.size() - 1;
					//toIndex = fromIndex;
				}
				if (endPos >= parties.size())
				{
					endPos = parties.size() - 1;
				}
				if (fromIndex > endPos)
				{
					fromIndex = endPos;
				}
				
				List<DcpParty> subParties = parties.subList(fromIndex, toIndex);
				List<Party> pts = new LinkedList<Party>();
				for (DcpParty obj: subParties)
				{
					pts.add(ToParty(obj));
				}
				res.setStartPos(fromIndex);
				res.setCount(subParties.size());
				res.setParties(pts);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("CreateParty Exception " + ex.getMessage());
		}
		
		return res;
	}
	
	private Party ToParty(DcpParty obj)
	{
		Party p = new Party();
		p.setId(obj.getPartyId());
		p.setAddress(obj.getAddress());
		p.setDate(obj.getPartydate());
		p.setDescription(obj.getDescription());
		p.setPartyType(ePartyType.values()[obj.getPartytype()]);
		p.setTitle(obj.getTitle());
		
		return p;
	}
}
