package com.divx.service.domain.model;

// Generated 2015-6-23 12:26:37 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpImSync generated by hbm2java
 */
public class DcpImSync implements java.io.Serializable {

	private Integer id;
	private Boolean synced;
	private Date datecreated;
	private Date datemodified;
	private Integer tryCount;
	private String errorMessage;
	private Integer syncType;
	private String data;

	public DcpImSync() {
	}

	public DcpImSync(Date datecreated) {
		this.datecreated = datecreated;
	}

	public DcpImSync(Boolean synced, Date datecreated, Date datemodified,
			Integer tryCount, String errorMessage, Integer syncType, String data) {
		this.synced = synced;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.tryCount = tryCount;
		this.errorMessage = errorMessage;
		this.syncType = syncType;
		this.data = data;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getSynced() {
		return this.synced;
	}

	public void setSynced(Boolean synced) {
		this.synced = synced;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

	public Date getDatemodified() {
		return this.datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}

	public Integer getTryCount() {
		return this.tryCount;
	}

	public void setTryCount(Integer tryCount) {
		this.tryCount = tryCount;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getSyncType() {
		return this.syncType;
	}

	public void setSyncType(Integer syncType) {
		this.syncType = syncType;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
