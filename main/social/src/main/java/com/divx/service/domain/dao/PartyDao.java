package com.divx.service.domain.dao;

import java.util.List;

import com.divx.service.domain.model.DcpParty;

public interface PartyDao {
	int CreateParty(DcpParty obj);
	
	int UpdateParty(DcpParty obj);
	
	int DeleteParty(int partyId);
	
	DcpParty GetParty(int partyId);
	
	List<DcpParty> GetParties(int userId);
}
