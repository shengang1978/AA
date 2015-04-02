package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FindUserOption")
public class FindUserOption {
	public enum eFindOption
	{
		username,
		email,
		mobile,
		nickname
	}
	
	private eFindOption	findOption;
	private String searchKey;
	public eFindOption getFindOption() {
		return findOption;
	}
	public void setFindOption(eFindOption findOption) {
		this.findOption = findOption;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
