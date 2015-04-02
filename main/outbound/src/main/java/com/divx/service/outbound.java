package com.divx.service;

import javax.jws.WebService;

@WebService(name = "outbound", targetNamespace = "http://service.divx.com/")
public interface outbound {
	String StartSession(String assetId);
	int EndSession(String sessionId);
}
