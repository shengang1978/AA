package com.divx.service.domain.model;

// Generated 2015-6-11 16:01:06 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpUserRole generated by hbm2java
 */
public class DcpUserRole implements java.io.Serializable {

	private Integer id;
	private DcpRole dcpRole;
	private OsfUsers osfUsers;
	private Date createDate;
	private Date modifyDate;

	public DcpUserRole() {
	}

	public DcpUserRole(DcpRole dcpRole, OsfUsers osfUsers) {
		this.dcpRole = dcpRole;
		this.osfUsers = osfUsers;
	}

	public DcpUserRole(DcpRole dcpRole, OsfUsers osfUsers, Date createDate,
			Date modifyDate) {
		this.dcpRole = dcpRole;
		this.osfUsers = osfUsers;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DcpRole getDcpRole() {
		return this.dcpRole;
	}

	public void setDcpRole(DcpRole dcpRole) {
		this.dcpRole = dcpRole;
	}

	public OsfUsers getOsfUsers() {
		return this.osfUsers;
	}

	public void setOsfUsers(OsfUsers osfUsers) {
		this.osfUsers = osfUsers;
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

}
