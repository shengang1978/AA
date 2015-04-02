package com.divx.service.domain.model;

// Generated 2015-4-1 17:38:20 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * OsfComments generated by hbm2java
 */
public class OsfComments implements java.io.Serializable {

	private Long id;
	private String content;
	private Boolean enabled;
	private Date entered;
	private String entity;
	private long linkedId;
	private Date modified;
	private long enteredById;
	private long modifiedById;
	private Integer activitytype;

	public OsfComments() {
	}

	public OsfComments(String content, Date entered, long linkedId,
			Date modified, long enteredById, long modifiedById) {
		this.content = content;
		this.entered = entered;
		this.linkedId = linkedId;
		this.modified = modified;
		this.enteredById = enteredById;
		this.modifiedById = modifiedById;
	}

	public OsfComments(String content, Boolean enabled, Date entered,
			String entity, long linkedId, Date modified, long enteredById,
			long modifiedById, Integer activitytype) {
		this.content = content;
		this.enabled = enabled;
		this.entered = entered;
		this.entity = entity;
		this.linkedId = linkedId;
		this.modified = modified;
		this.enteredById = enteredById;
		this.modifiedById = modifiedById;
		this.activitytype = activitytype;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getEntered() {
		return this.entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public long getLinkedId() {
		return this.linkedId;
	}

	public void setLinkedId(long linkedId) {
		this.linkedId = linkedId;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public long getEnteredById() {
		return this.enteredById;
	}

	public void setEnteredById(long enteredById) {
		this.enteredById = enteredById;
	}

	public long getModifiedById() {
		return this.modifiedById;
	}

	public void setModifiedById(long modifiedById) {
		this.modifiedById = modifiedById;
	}

	public Integer getActivitytype() {
		return this.activitytype;
	}

	public void setActivitytype(Integer activitytype) {
		this.activitytype = activitytype;
	}

}
