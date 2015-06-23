package com.divx.service.model;

import java.util.Date;

import com.divx.service.model.BaseSocialType.ActionType;

public class Activity{
	
	private int id;
	private User user;
	private ActionType activityType;
	private String content;
	private Date   date;
	public int getActivityId() {
		return id;
	}
	public void setActivityId(int id) {
		this.id = id;
	}

	public ActionType getActivityType() {
		return activityType;
	}
	public void setActivityType(ActionType activityType) {
		this.activityType = activityType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
