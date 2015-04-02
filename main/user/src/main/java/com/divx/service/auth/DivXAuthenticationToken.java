package com.divx.service.auth;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.auth.model.DivXAuthUser;

public class DivXAuthenticationToken extends AbstractAuthenticationToken{
	private static final long serialVersionUID = 1L;
	private final Object principal;
    private Object details;
    private static final UserDetailsService userService = new DivxAuthUserService();

    private Collection<GrantedAuthority> authorities;
    
	public DivXAuthenticationToken(String authToken){
		super(null);
        super.setAuthenticated(true); // must use super, as we override
        //JWTParser parser = new JWTParser(jwtToken);
        DivXAuthToken token = DivXAuthToken.FromAuthToken(authToken);
        UserDetails user = null;
        if (token != null)
        {
        	user = userService.loadUserByUsername(token.GetAuthUsername());
        	if (user == null || !DivXAuthToken.IsValidAuthToken(token, user))
        	{//Invalid token.
        		user = null;
        	}
        }
		this.principal= user;
		
		this.setDetailsAuthorities();
	}
	
	@Override
	public Object getCredentials() {
		return "";
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
	private void setDetailsAuthorities() {
		DivXAuthUser ti = (DivXAuthUser)principal;
		String username = ti.getUsername();
		//SpringUserDetailsAdapter adapter = new SpringUserDetailsAdapter(username);
		details=ti; //adapter;
		authorities=(Collection) ti.getAuthorities(); //adapter.getAuthorities();
		
	}

	@Override
	public Collection getAuthorities() {
		return authorities;
	}
}
