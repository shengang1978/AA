package com.divx.service.auth.model;

import org.springframework.security.core.GrantedAuthority;

public class DivxAuthPermission implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}
	
	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	private String authority;
}
