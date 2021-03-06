package com.divx.service.domain.model;

// Generated 2015-6-11 16:01:06 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpToken generated by hbm2java
 */
public class DcpToken implements java.io.Serializable {

	private Integer tokenId;
	private int userId;
	private String token;
	private Integer devicetype;
	private String deviceguid;
	private Date datecreated;
	private Date datemodified;
	private boolean isactive;
	private Date expiredate;
	private String deviceuniqueid;

	public DcpToken() {
	}

	public DcpToken(int userId, String token, Date datecreated,
			Date datemodified, boolean isactive, String deviceuniqueid) {
		this.userId = userId;
		this.token = token;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.isactive = isactive;
		this.deviceuniqueid = deviceuniqueid;
	}

	public DcpToken(int userId, String token, Integer devicetype,
			String deviceguid, Date datecreated, Date datemodified,
			boolean isactive, Date expiredate, String deviceuniqueid) {
		this.userId = userId;
		this.token = token;
		this.devicetype = devicetype;
		this.deviceguid = deviceguid;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.isactive = isactive;
		this.expiredate = expiredate;
		this.deviceuniqueid = deviceuniqueid;
	}

	public Integer getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getDevicetype() {
		return this.devicetype;
	}

	public void setDevicetype(Integer devicetype) {
		this.devicetype = devicetype;
	}

	public String getDeviceguid() {
		return this.deviceguid;
	}

	public void setDeviceguid(String deviceguid) {
		this.deviceguid = deviceguid;
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

	public boolean isIsactive() {
		return this.isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public Date getExpiredate() {
		return this.expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public String getDeviceuniqueid() {
		return this.deviceuniqueid;
	}

	public void setDeviceuniqueid(String deviceuniqueid) {
		this.deviceuniqueid = deviceuniqueid;
	}

}
