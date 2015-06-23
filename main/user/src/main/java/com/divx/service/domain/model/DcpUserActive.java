package com.divx.service.domain.model;

// Generated 2015-6-11 16:01:06 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpUserActive generated by hbm2java
 */
public class DcpUserActive implements java.io.Serializable {

	private long userId;
	private OsfUsers osfUsers;
	private Date lastAccess;

	public DcpUserActive() {
	}

	public DcpUserActive(OsfUsers osfUsers, Date lastAccess) {
		this.osfUsers = osfUsers;
		this.lastAccess = lastAccess;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public OsfUsers getOsfUsers() {
		return this.osfUsers;
	}

	public void setOsfUsers(OsfUsers osfUsers) {
		this.osfUsers = osfUsers;
	}

	public Date getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

}