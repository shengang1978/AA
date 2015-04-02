package com.divx.service.domain.model;

// Generated 2014-11-3 18:28:48 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpUserdevice generated by hbm2java
 */
public class DcpUserdevice implements java.io.Serializable {

	private Integer deviceId;
	private String deviceuniqueid;
	private String devicetype;
	private Boolean isactive;
	private Date datecreated;
	private Date datemodified;
	private int userId;
	private String deviceGuid;

	public DcpUserdevice() {
	}

	public DcpUserdevice(String deviceuniqueid, String devicetype,
			Date datecreated, Date datemodified, int userId, String deviceGuid) {
		this.deviceuniqueid = deviceuniqueid;
		this.devicetype = devicetype;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.userId = userId;
		this.deviceGuid = deviceGuid;
	}

	public DcpUserdevice(String deviceuniqueid, String devicetype,
			Boolean isactive, Date datecreated, Date datemodified, int userId,
			String deviceGuid) {
		this.deviceuniqueid = deviceuniqueid;
		this.devicetype = devicetype;
		this.isactive = isactive;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.userId = userId;
		this.deviceGuid = deviceGuid;
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceuniqueid() {
		return this.deviceuniqueid;
	}

	public void setDeviceuniqueid(String deviceuniqueid) {
		this.deviceuniqueid = deviceuniqueid;
	}

	public String getDevicetype() {
		return this.devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public Boolean getIsactive() {
		return this.isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
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

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeviceGuid() {
		return this.deviceGuid;
	}

	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

}
