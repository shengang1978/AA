package com.divx.service.domain.model;

// Generated 2015-4-20 23:30:30 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpPartyUser generated by hbm2java
 */
public class DcpPartyUser implements java.io.Serializable {

	private Integer id;
	private DcpParty dcpParty;
	private Date datecreated;
	private Date datemodified;
	private String username;
	private String email;
	private String mobile;
	private String qq;
	private String weixin;
	private String nickname;
	private String snapshoturl;
	private int usertype;

	public DcpPartyUser() {
	}

	public DcpPartyUser(DcpParty dcpParty, Date datecreated, Date datemodified,
			int usertype) {
		this.dcpParty = dcpParty;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.usertype = usertype;
	}

	public DcpPartyUser(DcpParty dcpParty, Date datecreated, Date datemodified,
			String username, String email, String mobile, String qq,
			String weixin, String nickname, String snapshoturl, int usertype) {
		this.dcpParty = dcpParty;
		this.datecreated = datecreated;
		this.datemodified = datemodified;
		this.username = username;
		this.email = email;
		this.mobile = mobile;
		this.qq = qq;
		this.weixin = weixin;
		this.nickname = nickname;
		this.snapshoturl = snapshoturl;
		this.usertype = usertype;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DcpParty getDcpParty() {
		return this.dcpParty;
	}

	public void setDcpParty(DcpParty dcpParty) {
		this.dcpParty = dcpParty;
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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixin() {
		return this.weixin;
	}

	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSnapshoturl() {
		return this.snapshoturl;
	}

	public void setSnapshoturl(String snapshoturl) {
		this.snapshoturl = snapshoturl;
	}

	public int getUsertype() {
		return this.usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

}