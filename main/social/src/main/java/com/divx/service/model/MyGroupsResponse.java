package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "MyGroupsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MyGroupsResponse")
public class MyGroupsResponse extends ServiceResponse {
	private List<Group> groups;
	private int startPos;
	private int count;

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
}
