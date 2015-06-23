package com.divx.service.model.im;

public class HxUser {
	public HxUser(){}
	public HxUser(String username, String password, String nickname)
	{
		this.username = username;
		this.password = password;
		this.nickname = nickname;
	}
	private String username;
	private String password;
	private String nickname;
	
	private String uuid;
	private String type;
	private Long created;
	private Long modified;
	private boolean activated;
	private String device_token;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public Long getCreated() {
		return created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public Long getModified() {
		return modified;
	}
	public void setModified(Long modified) {
		this.modified = modified;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDevice_token() {
		return device_token;
	}
	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}
}