package org.osforce.connect.entity.commons;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.osforce.connect.entity.system.User;
import org.osforce.spring4me.entity.IdEntity;

/**
 * 
 * @author gavin
 * @since 1.0.0
 * @create Nov 11, 2010 - 2:40:42 PM
 * <a href="http://www.opensourceforce.org">开源力量</a>
 */
@Entity
@Table(name="comments")
@Cacheable
public class Comment extends IdEntity {
	private static final long serialVersionUID = -364081016771966534L;

	@NotBlank
	private String content;
	private Date entered;
	private Date modified;
	private Boolean enabled = true;
	private Long linkedId;
	private String entity; // entity name like : Activity BlogPost
	private int activitytype;
	// helper
	private Long enteredId;
	private Long modifiedId;
	// refer
	private User enteredBy;
	private User modifiedBy;

	public Comment() {
	}
	
	@Column(nullable=false, length=500)
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	@Column(nullable=false)
	public Date getEntered() {
		return entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}
	
	@Column(nullable=false)
	public Date getModified() {
		return modified;
	}
	
	public void setModified(Date modified) {
		this.modified = modified;
	}

	@Column(nullable=false)
	public Long getLinkedId() {
		return linkedId;
	}
	
	public void setLinkedId(Long linkedId) {
		this.linkedId = linkedId;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	@Transient
	public Long getEnteredId() {
		if(enteredId==null && enteredBy!=null) {
			enteredId = enteredBy.getId();
		}
		return enteredId;
	}
	
	public void setEnteredId(Long enteredId) {
		this.enteredId = enteredId;
	}
	
	@Transient
	public Long getModifiedId() {
		if(modifiedId==null && modifiedBy!=null) {
			modifiedId = modifiedBy.getId();
		}
		return modifiedId;
	}
	
	public void setModifiedId(Long modifiedId) {
		this.modifiedId = modifiedId;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="entered_by_id")
	public User getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(User enteredBy) {
		this.enteredBy = enteredBy;
	}

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="modified_by_id")
	public User getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public int getActivitytype() {
		return activitytype;
	}

	public void setActivitytype(int activitytype) {
		this.activitytype = activitytype;
	}
}
