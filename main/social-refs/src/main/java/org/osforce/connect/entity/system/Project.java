package org.osforce.connect.entity.system;

import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.validator.constraints.NotBlank;
import org.osforce.connect.entity.profile.Profile;
import org.osforce.spring4me.commons.collection.CollectionUtil;
import org.osforce.spring4me.entity.IdEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 *
 * @author gavin
 * @since 1.0.0
 * @create Nov 4, 2010 - 11:47:28 AM <a
 *         href="http://www.opensourceforce.org">开源力量</a>
 */
@Entity
@Table(name="projects")
@Cacheable
public class Project extends IdEntity {
	private static final long serialVersionUID = -8971421207588403404L;
	public static final String NAME = Project.class.getSimpleName();
	@NotBlank
	@Column(name="_unique_id")
	private String uniqueId;
	@NotBlank
	@Column(name="_title")
	private String title;
	@DateTimeFormat(iso=ISO.DATE_TIME)
	@Column(name="_entered")
	private Date entered;
	@Column(name="_modified")
	private Date modified;
	@Column(name="_publish")
	private Boolean publish = true;
	@Column(name="_enabled")
	private Boolean enabled = false;
	// helper
	@Column(name="_entered_by_id")
	private Long enteredbyId;
	@Column(name="_modified_by_id")
	private Long modifiedbyId;
	@Column(name="_category_id")
	private Long categoryId;
	@Column(name="_sub_category1_id")
	private Long subCategory1Id;
	@Column(name="_sub_category2_id")
	private Long subCategory2Id;
	@Column(name="_sub_category3_id")
	private Long subCategory3Id;
//	private Long profileId;
	// refer
	//private User enteredBy;
	//private User modifiedBy;
//	private Profile profile;
//	private ProjectCategory category;
//	private ProjectCategory subCategory1;
//	private ProjectCategory subCategory2;
//	private ProjectCategory subCategory3;
//	private List<ProjectFeature> features = CollectionUtil.newArrayList();

	public Project() {
	}

	@Column(unique=true, nullable=false)
	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Column(nullable=false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getPublish() {
		return publish;
	}

	public void setPublish(Boolean publish) {
		this.publish = publish;
	}

	//@Transient
	public Long getEnteredId() {
//		if(enteredId==null && enteredBy!=null) {
//			enteredId = enteredBy.getId();
//		}
		return enteredbyId;
	}

	public void setEnteredId(Long enteredId) {
		this.enteredbyId = enteredId;
	}

	//@Transient
	public Long getModifiedId() {
//		if(modifiedId==null && modifiedBy!=null) {
//			modifiedId = modifiedBy.getId();
//		}
		return modifiedbyId;
	}

	public void setModifiedId(Long modifiedId) {
		this.modifiedbyId = modifiedId;
	}

	@Transient
	public Long getCategoryId() {
//		if(categoryId==null && category!=null) {
//			categoryId = category.getId();
//		}
		return categoryId;
	}

	//@Transient
	public Long getSubCategoryId1() {
//		if(subCategoryId1==null && subCategory1!=null)  {
//			subCategoryId1 = subCategory1.getId();
//		}
		return subCategory1Id;
	}

	public void setSubCategoryId1(Long subCategoryId1) {
		this.subCategory1Id = subCategoryId1;
	}

	//@Transient
	public Long getSubCategoryId2() {
//		if(subCategoryId2==null && subCategory2!=null)  {
//			subCategoryId2 = subCategory2.getId();
//		}
		return subCategory2Id;
	}

	public void setSubCategoryId2(Long subCategoryId2) {
		this.subCategory2Id = subCategoryId2;
	}

	//@Transient
	public Long getSubCategoryId3() {
//		if(subCategoryId3==null && subCategory3!=null)  {
//			subCategoryId3 = subCategory3.getId();
//		}
		return subCategory3Id;
	}

	public void setSubCategoryId3(Long subCategoryId3) {
		this.subCategory3Id = subCategoryId3;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

//	@Transient
//	public Long getProfileId() {
//		if(profileId==null && profile!=null) {
//			profileId = profile.getId();
//		}
//		return profileId;
//	}
//
//	public void setProfileId(Long profileId) {
//		this.profileId = profileId;
//	}

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="entered_by_id")
//	public User getEnteredBy() {
//		return enteredBy;
//	}
//
//	public void setEnteredBy(User enteredBy) {
//		this.enteredBy = enteredBy;
//	}
//
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="modified_by_id")
//	public User getModifiedBy() {
//		return modifiedBy;
//	}
//
//	public void setModifiedBy(User modifiedBy) {
//		this.modifiedBy = modifiedBy;
//	}

//	@OneToOne(mappedBy="project")
//	public Profile getProfile() {
//		return profile;
//	}
//
//	public void setProfile(Profile profile) {
//		this.profile = profile;
//	}

//	@IndexedEmbedded
//	@ManyToOne(fetch=FetchType.EAGER, optional=false)
//	@JoinColumn(name="category_id")
//	public ProjectCategory getCategory() {
//		return category;
//	}
//
//	public void setCategory(ProjectCategory category) {
//		this.category = category;
//	}

//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="sub_category1_id")
//	public ProjectCategory getSubCategory1() {
//		return subCategory1;
//	}
//
//	public void setSubCategory1(ProjectCategory subCategory1) {
//		this.subCategory1 = subCategory1;
//	}
//
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="sub_category2_id")
//	public ProjectCategory getSubCategory2() {
//		return subCategory2;
//	}
//
//	public void setSubCategory2(ProjectCategory subCategory2) {
//		this.subCategory2 = subCategory2;
//	}
//
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="sub_category3_id")
//	public ProjectCategory getSubCategory3() {
//		return subCategory3;
//	}
//
//	public void setSubCategory3(ProjectCategory subCategory3) {
//		this.subCategory3 = subCategory3;
//	}
//
//	@OneToMany(fetch=FetchType.LAZY, mappedBy="project")
//	@OrderBy("level")
//	public List<ProjectFeature> getFeatures() {
//		return features;
//	}
//
//	public void setFeatures(List<ProjectFeature> features) {
//		this.features = features;
//	}

}
