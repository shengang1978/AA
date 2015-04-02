package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GroupsOrFriends")
public class GroupsOrFriends {
	private int groupId;
	private int[] groupIds;
	private int[] userIds;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(int[] groupIds) {
		this.groupIds = groupIds;
	}
	public int[] getUserIds() {
		return userIds;
	}
	public void setUserIds(int[] userIds) {
		this.userIds = userIds;
	}
	
	
}
