package com.divx.service.impl;

import javax.jws.WebService;

import com.divx.service.outbound;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.outbound", portName = "outboundimplPort", serviceName = "outboundimplService")
public class outboundimpl implements outbound {

	@Override
	public String StartSession(String assetId) {
		// TODO Auto-generated method stub
		return "session id";
	}

	@Override
	public int EndSession(String sessionId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
