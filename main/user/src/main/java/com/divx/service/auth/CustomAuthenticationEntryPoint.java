package com.divx.service.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.divx.service.Util;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final static String errorMessage;
	static {
		ServiceResponse sr = new ServiceResponse();
    	sr.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_IS_EXPIRED);
    	sr.setResponseMessage("Unauthorized: Authentication token was either missing or invalid.");
    	errorMessage = Util.ObjectToJson(sr);
	}
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
    	
    	
        response.sendError( HttpServletResponse.SC_UNAUTHORIZED, errorMessage );
    }
}