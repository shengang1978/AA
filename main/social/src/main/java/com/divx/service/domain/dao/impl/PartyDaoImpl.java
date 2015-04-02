package com.divx.service.domain.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.divx.service.BaseDao;
import com.divx.service.domain.dao.PartyDao;
import com.divx.service.domain.model.DcpParty;

@Repository
public class PartyDaoImpl extends BaseDao implements PartyDao {

	@Override
	public int CreateParty(DcpParty obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int UpdateParty(DcpParty obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int DeleteParty(int partyId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DcpParty GetParty(int partyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DcpParty> GetParties(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
