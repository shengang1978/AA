package com.divx.service.auth;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.auth.model.DivXAuthUser;

public class CustomTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	protected CustomTokenAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		// TODO Auto-generated constructor stub
		super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(new NoOpAuthenticationManager());
		setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
	}

	public final String HEADER_SECURITY_TOKEN = "Token"; 
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {
		String token = request.getHeader(HEADER_SECURITY_TOKEN);

		if (token == null || token.isEmpty() || DivXAuthToken.FromAuthToken(token) == null)
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null)
			{
				List<GrantedAuthority> roles=new ArrayList<GrantedAuthority>();
				roles.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
				auth = new AnonymousAuthenticationToken("bypass_auth","bypass_auth",roles);
				auth.setAuthenticated(false);
				this.setContinueChainBeforeSuccessfulAuthentication(true);
			}
			return auth;
		}
		
		this.setContinueChainBeforeSuccessfulAuthentication(false);
    	AbstractAuthenticationToken userAuthenticationToken = authUserByToken(token);
        if(userAuthenticationToken == null) 
        	throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
        return userAuthenticationToken;
	}

	 /**
     * authenticate the user based on token
     * @return
     */
    private AbstractAuthenticationToken authUserByToken(String token) {
    	if(token==null) {
    		return null;
    	}
        AbstractAuthenticationToken authToken = null;
        try {
        	authToken = new DivXAuthenticationToken(token);
        	if (authToken.getPrincipal() == null)	//Invalid token
        		return null;
        } catch (Exception e) {
            logger.error("Authenticate user by token error: ", e);
            authToken = null;
        }
        return authToken;
    }


	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		super.doFilter(req, res, chain);
	}
}

class NoOpAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		return authentication;
	}
}

//@Component
//class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
//	 protected final Log logger = LogFactory.getLog(this.getClass());
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException ) throws IOException, ServletException {
//		String contentType = request.getContentType();
//		logger.info(contentType);
//		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
//	}
//}

class TokenSimpleUrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	protected String determineTargetUrl(HttpServletRequest request,
			HttpServletResponse response) {
		String context = request.getContextPath();
		String fullURL = request.getRequestURI();
		String url = fullURL.substring(fullURL.indexOf(context)+context.length());
		return url;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (authentication.isAuthenticated())
		{
			String url = determineTargetUrl(request,response);
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

}

//class JWTAuthenticationToken extends AbstractAuthenticationToken{
// 	private static final long serialVersionUID = 1L;
//	private final Object principal;
//    private Object details;
//
//    Collection<GrantedAuthority>  authorities;
//	public JWTAuthenticationToken( String jwtToken) {
//		super(null);
//        super.setAuthenticated(true); // must use super, as we override
//        //JWTParser parser = new JWTParser(jwtToken);
//        
//        TokenParser parser = new TokenParser(jwtToken); 
//		this.principal= parser.GetUser();
//		
//		this.setDetailsAuthorities();
//	}
//
//	@Override
//	public Object getCredentials() {
//		return "";
//	}
//
//	@Override
//	public Object getPrincipal() {
//		return principal;
//	}
//	private void setDetailsAuthorities() {
//		DivXAuthUser ti = (DivXAuthUser)principal;
//		String username = ti.getUsername();
//		//SpringUserDetailsAdapter adapter = new SpringUserDetailsAdapter(username);
//		details=ti; //adapter;
//		authorities=(Collection) ti.getAuthorities(); //adapter.getAuthorities();
//		
//	}
//
//	@Override
//	public Collection getAuthorities() {
//		return authorities;
//	}
//}