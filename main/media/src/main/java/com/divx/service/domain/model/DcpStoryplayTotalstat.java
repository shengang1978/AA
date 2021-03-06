package com.divx.service.domain.model;

// Generated 2015-6-1 16:15:49 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpStoryplayTotalstat generated by hbm2java
 */
public class DcpStoryplayTotalstat implements java.io.Serializable {

	private int id;
	private DcpLesson dcpLesson;
	private DcpMedia dcpMedia;
	private Integer playcount;
	private Date datemodified;

	public DcpStoryplayTotalstat() {
	}

	public DcpStoryplayTotalstat(int id, DcpMedia dcpMedia) {
		this.id = id;
		this.dcpMedia = dcpMedia;
	}

	public DcpStoryplayTotalstat(int id, DcpLesson dcpLesson,
			DcpMedia dcpMedia, Integer playcount, Date datemodified) {
		this.id = id;
		this.dcpLesson = dcpLesson;
		this.dcpMedia = dcpMedia;
		this.playcount = playcount;
		this.datemodified = datemodified;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DcpLesson getDcpLesson() {
		return this.dcpLesson;
	}

	public void setDcpLesson(DcpLesson dcpLesson) {
		this.dcpLesson = dcpLesson;
	}

	public DcpMedia getDcpMedia() {
		return this.dcpMedia;
	}

	public void setDcpMedia(DcpMedia dcpMedia) {
		this.dcpMedia = dcpMedia;
	}

	public Integer getPlaycount() {
		return this.playcount;
	}

	public void setPlaycount(Integer playcount) {
		this.playcount = playcount;
	}

	public Date getDatemodified() {
		return this.datemodified;
	}

	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}

}
