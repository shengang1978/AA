package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MyRoleInGroupResponse")
public class MyRoleInGroupResponse extends ServiceResponse{
	private GroupRole	role;

	public GroupRole getRole() {
		return role;
	}

	public void setRole(GroupRole role) {
		this.role = role;
	}
}
