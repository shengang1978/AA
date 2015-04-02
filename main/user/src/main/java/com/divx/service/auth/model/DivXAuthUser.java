package com.divx.service.auth.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.divx.service.auth.DivXAuthenticationToken;
import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.OsfUsers;


public class DivXAuthUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -11162606166149172L;
	
	private OsfUsers user;

	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialExpired;
	private DcpToken token;
	private Collection<DivxAuthPermission> authorities;
	public DivXAuthUser()
	{	
	}
	public DivXAuthUser(OsfUsers user)
	{
		this.user= user; 
		accountExpired = false;
		accountLocked = false;
		credentialExpired = false;
		
		authorities = new LinkedList<DivxAuthPermission>();
	}
	public DivXAuthUser(OsfUsers user, DcpToken token)
	{
		this.user= user; 
		this.setToken(token);
	}
	
	public static DivXAuthUser FromSecurityContent()
	{
		Authentication obj = SecurityContextHolder.getContext().getAuthentication();
		if (obj instanceof DivXAuthenticationToken)
		{
			DivXAuthenticationToken token = (DivXAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
			if (token != null)
				return (DivXAuthUser)token.getPrincipal();
		}

		return null;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	
	public void setAuthorities(Collection<DivxAuthPermission> authorities)
	{
		this.authorities = authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return !accountExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return !accountLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return !credentialExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}

	public int getUserId() {
		return user.getId().intValue();
	}
	public DcpToken getToken() {
		return token;
	}
	public void setToken(DcpToken token) {
		this.token = token;
	}
	public OsfUsers getUser() {
		return user;
	}
	public void setUser(OsfUsers user) {
		this.user = user;
	}
}
