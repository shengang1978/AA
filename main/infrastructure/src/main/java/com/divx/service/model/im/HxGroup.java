package com.divx.service.model.im;

public class HxGroup{
	public HxGroup(){}
	public HxGroup(String owner, String groupName, String desc, int maxUsers)
	{
		this.owner = owner;
		this.groupname = groupName;
		this.desc = desc;
		this.maxusers = maxUsers;
		this.approval = true;
		this.isPublic = false;
	}
	
	private String groupid;
	private String groupname;
	private String desc;
	private boolean approval;
	private boolean isPublic;
	private int maxusers;
	private String owner;
	
	private boolean result;
	private String user;
	
	private boolean success;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public int getMaxusers() {
		return maxusers;
	}
	public void setMaxusers(int maxusers) {
		this.maxusers = maxusers;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	
}
