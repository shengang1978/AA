package com.divx.service.model.party;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "PartyResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyResponse")
public class PartyResponse extends ServiceResponse {
	private Party party;

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}
}
