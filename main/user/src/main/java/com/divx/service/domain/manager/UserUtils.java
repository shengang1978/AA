package com.divx.service.domain.manager;

import com.divx.service.Util;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.model.User;

public class UserUtils {
	public static User ToUser(OsfUsers src, User dst)
	{
		dst.setUserId(src.getId().intValue());
		dst.setUsername(src.getUsername());
		dst.setNickname(src.getNickname());
		if (src.getPhotourl() != null && !src.getPhotourl().isEmpty())
			dst.setPhotourl(Util.UrlWithHttp(src.getPhotourl()));
		else
			dst.setPhotourl("");
		
		return dst;
	}
}
