package com.divx.service.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.divx.service.model.PartyBaseType.ePartyType;

@XmlRootElement(name = "Party")
public class Party {	
	private int id;
	private ePartyType partyType;
	private String title;
	private String description;
	private Date date;
	private String address;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ePartyType getPartyType() {
		return partyType;
	}
	public void setPartyType(ePartyType partyType) {
		this.partyType = partyType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
