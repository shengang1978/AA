package com.divx.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.divx.service.auth.model.DivXAuthToken;
import com.divx.service.auth.model.DivXAuthUser;
import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.domain.repository.TokenDao;
import com.divx.service.domain.repository.impl.DivxUserDaoImpl;
import com.divx.service.domain.repository.impl.TokenDaoImpl;

public class DivxAuthUserService implements UserDetailsService {

	private final static DivxUserDao	divxUserDao = new DivxUserDaoImpl();
	private final static TokenDao	tokenDao = new TokenDaoImpl();
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		DivXAuthToken token = DivXAuthToken.ParseAuthUsername(username);
		
		if (token != null)
		{
			DcpToken dt = tokenDao.GetToken(token.getDcpToken());
			if (dt != null && dt.getUserId() == token.getUserId())
			{
				OsfUsers u = divxUserDao.GetUser(token.getUserId());
				
				if (u != null)
				{
					return new DivXAuthUser(u, dt);
				}
			}
		}
		return null;
	}

}
