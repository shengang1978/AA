package com.divx.service.domain.dao;

import java.util.List;

import com.divx.service.domain.model.DcpParty;
import com.divx.service.domain.model.DcpPartyUser;
import com.divx.service.model.party.PartyBaseType;

public interface PartyDao {
	int CreateParty(DcpParty obj);
	
	int UpdateParty(DcpParty obj);
	
	int DeleteParty(int partyId);
	
	DcpParty GetParty(int partyId);
	
	List<DcpParty> GetParties(int userId);
	
	List<DcpPartyUser> GetUsersInParty(int partyId);
	
	int RemoveUser(int partyId, PartyBaseType.ePartyUserType userType, String value);
	
	int AddUsers(int partyId, List<DcpPartyUser> users);
}
