package com.divx.service.domain.model;

// Generated 2015-6-23 12:26:37 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * DcpEmailTemplate generated by hbm2java
 */
public class DcpEmailTemplate implements java.io.Serializable {

	private Integer emailId;
	private Date createdate;
	private Date modifydate;
	private String emailType;
	private String emailContent;

	public DcpEmailTemplate() {
	}

	public DcpEmailTemplate(Date createdate, Date modifydate) {
		this.createdate = createdate;
		this.modifydate = modifydate;
	}

	public DcpEmailTemplate(Date createdate, Date modifydate, String emailType,
			String emailContent) {
		this.createdate = createdate;
		this.modifydate = modifydate;
		this.emailType = emailType;
		this.emailContent = emailContent;
	}

	public Integer getEmailId() {
		return this.emailId;
	}

	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getModifydate() {
		return this.modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getEmailType() {
		return this.emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getEmailContent() {
		return this.emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

}
