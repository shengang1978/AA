package com.divx.service.model.party;

public class PartyBaseType {
	public enum ePartyType
	{
		dinner,
		film,
		music
	}
	
	public enum ePartyQueryType
	{
		all,
		owner,
		member
	}
	
	public enum ePartyUserType{
		username,
		mobile,
		email,
		qq,
		weixin
	}
}
