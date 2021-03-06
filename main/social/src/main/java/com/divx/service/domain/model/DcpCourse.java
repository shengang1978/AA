package com.divx.service.domain.model;

// Generated 2015-4-7 16:03:38 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpCourse generated by hbm2java
 */
public class DcpCourse implements java.io.Serializable {

	private Integer courseId;
	private String courseTitle;
	private String courseDescription;
	private Date createDate;
	private Date modifyDate;
	private int mediaId;

	public DcpCourse() {
	}

	public DcpCourse(int mediaId) {
		this.mediaId = mediaId;
	}

	public DcpCourse(String courseTitle, String courseDescription,
			Date createDate, Date modifyDate, int mediaId) {
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.mediaId = mediaId;
	}

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public String getCourseTitle() {
		return this.courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return this.courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
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

	public int getMediaId() {
		return this.mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}

}
