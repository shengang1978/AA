package com.divx.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class ServiceHeaderHelper {
	public ServiceHeaderHelper()
	{
		Init();
	}
	
	public String getHeader(String key)
	{
		return httpRequest.getHeader(key);
	}
	
	public String getDeviceUniqueId()
	{
		return httpRequest.getHeader("DeviceUniqueId");
	}
	
	public String getDeviceType()
	{
		return httpRequest.getHeader("DeviceType");
	}
	
	public String getLocalAddr()
	{
		return httpRequest.getLocalAddr();
	}
	
	public int getOrganizationId()
	{
		int orgId = 1;
		String strOrgId = httpRequest.getHeader("OrganizationId");
		if (strOrgId != null && !strOrgId.isEmpty())
		{
			try
			{
				orgId = Integer.parseInt(strOrgId);
			}
			catch(Exception e)
			{
				
			}
		}
		return orgId;
	}
	
	private void Init()
	
	{
		Message message = PhaseInterceptorChain.getCurrentMessage();
		httpRequest = (HttpServletRequest)message.get(AbstractHTTPDestination.HTTP_REQUEST);
		
	}
	
	private HttpServletRequest httpRequest;
}
