package com.divx.service.model.party;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PartyUsersOption")
public class PartyUsersOption {
	private List<PartyUser> users;

	public List<PartyUser> getUsers() {
		return users;
	}

	public void setUsers(List<PartyUser> users) {
		this.users = users;
	}
}
