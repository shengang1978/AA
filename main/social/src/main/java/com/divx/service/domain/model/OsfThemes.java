package com.divx.service.domain.model;

// Generated 2014-11-4 15:36:18 by Hibernate Tools 4.3.1

/**
 * OsfThemes generated by hbm2java
 */
public class OsfThemes implements java.io.Serializable {

	private Long id;
	private Boolean enabled;
	private String layout;
	private String layoutPopup;
	private String name;

	public OsfThemes() {
	}

	public OsfThemes(String name) {
		this.name = name;
	}

	public OsfThemes(Boolean enabled, String layout, String layoutPopup,
			String name) {
		this.enabled = enabled;
		this.layout = layout;
		this.layoutPopup = layoutPopup;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getLayoutPopup() {
		return this.layoutPopup;
	}

	public void setLayoutPopup(String layoutPopup) {
		this.layoutPopup = layoutPopup;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
