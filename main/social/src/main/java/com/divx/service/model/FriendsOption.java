package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FriendsOption")
public class FriendsOption {
	private int groupId;
	private int[] groupIds;
	private int[] userIds;
	private String[] emails;
	private String[] mobiles;
	
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
	public String[] getEmails() {
		return emails;
	}
	public void setEmails(String[] emails) {
		this.emails = emails;
	}
	public String[] getMobiles() {
		return mobiles;
	}
	public void setMobiles(String[] mobiles) {
		this.mobiles = mobiles;
	}
	
	public String arrToStrng(int[] arr){
		StringBuffer temp = new StringBuffer("");
		if(arr != null && arr.length > 0){
			for(int i=0;i< arr.length;i++){
				if(i == arr.length-1){
					temp = temp.append(arr[i]);
				}else{
					temp = temp.append(arr[i] + ",");
				}
			}
		}
		return temp.toString();
	}
	
	public String arrToStrng(String[] arr){
		StringBuffer temp = new StringBuffer("");
		if(arr != null && arr.length > 0){
			for(int i=0;i< arr.length;i++){
				if (arr[i].isEmpty())
					continue;
				
				if(i == arr.length-1){
					temp = temp.append(arr[i]);
				}else{
					temp = temp.append(arr[i] + ",");
				}
			}
		}
		
		return temp.toString();
	}
}
