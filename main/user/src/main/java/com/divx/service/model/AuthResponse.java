package com.divx.service.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "AuthResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthResponse")
public class AuthResponse extends ServiceResponse {
	private String token;
	private String deviceGuid;
	
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
}
