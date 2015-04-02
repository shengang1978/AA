package com.divx.service.domain.model;

// Generated 2015-2-5 15:50:16 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

/**
 * DcpRadio generated by hbm2java
 */
public class DcpRadio implements java.io.Serializable {

	private int radioId;
	private String description;
	private Float rangestart;
	private Float rangeend;
	private Set dcpPresetgroups = new HashSet(0);

	public DcpRadio() {
	}

	public DcpRadio(int radioId) {
		this.radioId = radioId;
	}

	public DcpRadio(int radioId, String description, Float rangestart,
			Float rangeend, Set dcpPresetgroups) {
		this.radioId = radioId;
		this.description = description;
		this.rangestart = rangestart;
		this.rangeend = rangeend;
		this.dcpPresetgroups = dcpPresetgroups;
	}

	public int getRadioId() {
		return this.radioId;
	}

	public void setRadioId(int radioId) {
		this.radioId = radioId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getRangestart() {
		return this.rangestart;
	}

	public void setRangestart(Float rangestart) {
		this.rangestart = rangestart;
	}

	public Float getRangeend() {
		return this.rangeend;
	}

	public void setRangeend(Float rangeend) {
		this.rangeend = rangeend;
	}

	public Set getDcpPresetgroups() {
		return this.dcpPresetgroups;
	}

	public void setDcpPresetgroups(Set dcpPresetgroups) {
		this.dcpPresetgroups = dcpPresetgroups;
	}

}
