package com.divx.service.domain.repository;

import com.divx.service.domain.model.DcpToken;


public interface TokenDao {
	int CreateToken(DcpToken token);
	
	int UpdateToken(DcpToken token);
	
	DcpToken GetToken(String token);
	
	DcpToken GetToken(String deviceUniqueId, int userId);

}
