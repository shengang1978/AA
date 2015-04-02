/*package com.ls.ss.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        @SuppressWarnings("unchecked")
        Map<String, String[]> parms = request.getParameterMap();

        if (parms.containsKey("token")) {
            String strToken = parms.get("token")[0]; // grab the first "token" parameter
            System.out.println("Token: " + strToken);

            if (strToken.equals("test")) {
                System.out.println("valid token found");
                
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "test");
                token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
                Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test", authorities); //this.authenticationProvider.authenticate(token);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                System.out.println("invalid token");
            }
        } else {
            System.out.println("no token found");
        }
        // continue thru the filter chain
        chain.doFilter(request, response);
    }
}*/

package com.divx.service.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.divx.service.auth.model.DivXAuthToken;


public class AuthenticationTokenProcessingFilter extends GenericFilterBean
{
	private final UserDetailsService userService;

	public AuthenticationTokenProcessingFilter(UserDetailsService userService)
	{
		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);

		String authToken = this.extractAuthTokenFromRequest(httpRequest);
		
		DivXAuthToken token = DivXAuthToken.FromAuthToken(authToken);

		if (token != null && token.GetAuthUsername() != null) {

			UserDetails userDetails = this.userService.loadUserByUsername(token.GetAuthUsername());

			if (userDetails != null && DivXAuthToken.IsValidAuthToken(authToken, userDetails)) {

				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request)
	{
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest)
	{
		return httpRequest.getHeader("Token");
	}
}