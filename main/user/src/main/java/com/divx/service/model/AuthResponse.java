package com.divx.service.model;

import javax.xml.bind.annotation.*;

import com.divx.service.model.RegisterOption.eRegisterType;

@XmlRootElement(name = "AuthResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthResponse")
public class AuthResponse extends ServiceResponse {
	private String token;
	private String deviceGuid;
	private String roleName;
	private eRegisterType registerType;
	private User user;

	public void setToken(String token)
	{
		this.token = token;
	}
	
	public String getToken()
	{
		return token;
	}

	public String getDeviceGuid() {
		return deviceGuid;
	}

	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public eRegisterType getRegisterType() {
		return registerType;
	}

	public void setRegisterType(eRegisterType registerType) {
		this.registerType = registerType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
