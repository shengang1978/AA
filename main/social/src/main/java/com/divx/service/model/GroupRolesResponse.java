package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GroupRolesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupRolesResponse")
public class GroupRolesResponse extends ServiceResponse{
	private List<String>	roles;

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
