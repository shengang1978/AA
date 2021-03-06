package com.divx.service.domain.model;

// Generated 2015-6-1 16:15:49 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpMediaKeywords generated by hbm2java
 */
public class DcpMediaKeywords implements java.io.Serializable {

	private Integer keywordsId;
	private int mediaId;
	private String keyword;
	private Date datecreated;

	public DcpMediaKeywords() {
	}

	public DcpMediaKeywords(int mediaId, String keyword, Date datecreated) {
		this.mediaId = mediaId;
		this.keyword = keyword;
		this.datecreated = datecreated;
	}

	public Integer getKeywordsId() {
		return this.keywordsId;
	}

	public void setKeywordsId(Integer keywordsId) {
		this.keywordsId = keywordsId;
	}

	public int getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getDatecreated() {
		return this.datecreated;
	}

	public void setDatecreated(Date datecreated) {
		this.datecreated = datecreated;
	}

}
