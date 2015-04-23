package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OAuthOption")
public class OAuthOption {
	public enum eAuthProvider
	{
		QQ,
		Weixin
	}
	
	private eAuthProvider authProvider;
	private String accessToken;
	private String openId;		//unique id of the oAuth account
	
	
	public eAuthProvider getAuthProvider() {
		return authProvider;
	}
	public void setAuthProvider(eAuthProvider authProvider) {
		this.authProvider = authProvider;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}

	
	
}
