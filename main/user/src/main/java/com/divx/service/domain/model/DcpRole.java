package com.divx.service.domain.model;

// Generated 2015-6-11 16:01:06 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DcpRole generated by hbm2java
 */
public class DcpRole implements java.io.Serializable {

	private int roleId;
	private String roleName;
	private Date createDate;
	private Date modifyDate;
	private Set dcpUserRoles = new HashSet(0);

	public DcpRole() {
	}

	public DcpRole(int roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public DcpRole(int roleId, String roleName, Date createDate,
			Date modifyDate, Set dcpUserRoles) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.dcpUserRoles = dcpUserRoles;
	}

	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Set getDcpUserRoles() {
		return this.dcpUserRoles;
	}

	public void setDcpUserRoles(Set dcpUserRoles) {
		this.dcpUserRoles = dcpUserRoles;
	}

}
