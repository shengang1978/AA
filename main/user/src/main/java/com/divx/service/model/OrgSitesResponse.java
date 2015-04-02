package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OrgSitesResponse")
public class OrgSitesResponse extends ServiceResponse{
	private List<OrgSite>	sites;
	private int startPos;
	private int count;

	public int getStartPos() {
		return startPos;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<OrgSite> getSites() {
		return sites;
	}

	public void setSites(List<OrgSite> sites) {
		this.sites = sites;
	}
}
