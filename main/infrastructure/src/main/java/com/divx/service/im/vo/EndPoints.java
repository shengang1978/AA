package com.divx.service.im.vo;

import java.net.URL;

import com.divx.service.im.comm.Constants;
import com.divx.service.im.utils.HTTPClientUtils;

/**
 * HTTPClient EndPoints
 * 
 * @author Lynch 2014-09-15
 *
 */
public class EndPoints {

	public static final URL TOKEN_APP_URL()
	{
		return HTTPClientUtils.getURL(Constants.APPKEY().replace("#", "/") + "/token");
	}

	public static final URL USERS_URL()
	{
		return HTTPClientUtils.getURL(Constants.APPKEY().replace("#", "/") + "/users");
	}

	public static final URL MESSAGES_URL()
	{
		return HTTPClientUtils.getURL(Constants.APPKEY().replace("#", "/") + "/messages");
	}

	public static final URL CHATGROUPS_URL()
	{
		return HTTPClientUtils.getURL(Constants.APPKEY().replace("#", "/") + "/chatgroups");
	}

	public static final URL CHATFILES_URL()
	{
		return HTTPClientUtils.getURL(Constants.APPKEY().replace("#", "/") + "/chatfiles");
	}

}
