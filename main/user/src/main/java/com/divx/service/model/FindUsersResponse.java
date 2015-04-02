package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "FindUsersResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindUsersResponse")
public class FindUsersResponse extends ServiceResponse{
	private List<User>	users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
