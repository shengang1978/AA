package com.divx.service.domain.model;

// Generated 2015-4-2 23:00:14 by Hibernate Tools 4.3.1

/**
 * OsfRoles generated by hbm2java
 */
public class OsfRoles implements java.io.Serializable {

	private Long id;
	private String code;
	private String description;
	private Boolean enabled;
	private Integer level;
	private String name;
	private Long categoryId;

	public OsfRoles() {
	}

	public OsfRoles(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public OsfRoles(String code, String description, Boolean enabled,
			Integer level, String name, Long categoryId) {
		this.code = code;
		this.description = description;
		this.enabled = enabled;
		this.level = level;
		this.name = name;
		this.categoryId = categoryId;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

}
