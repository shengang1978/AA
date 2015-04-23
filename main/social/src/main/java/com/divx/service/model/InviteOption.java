package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "InviteOption")
public class InviteOption {
	public enum InviteType
	{
		email,
		mobile
	}
	private InviteType inviteType;
	private String identify; 
	private String message;
	private int groupId;
	public InviteType getInviteType() {
		return inviteType;
	}
	public void setInviteType(InviteType inviteType) {
		this.inviteType = inviteType;
	}
	public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	

}