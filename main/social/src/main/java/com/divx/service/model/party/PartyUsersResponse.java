package com.divx.service.model.party;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "PartyUsersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyUsersResponse")
public class PartyUsersResponse extends ServiceResponse {
	private List<PartyUser> users;

	public List<PartyUser> getUsers() {
		return users;
	}

	public void setUsers(List<PartyUser> users) {
		this.users = users;
	}
}
