package com.divx.service.im.comm;

import com.divx.service.ConfigurationManager;

/**
 * Constants
 * 
 * @author Lynch 2014-09-15
 *
 */
public class Constants {

	// API_HTTP_SCHEMA
	public static String API_HTTP_SCHEMA()
	{
		return ConfigurationManager.GetInstance().GetConfigValue("HX_API_HTTP_SCHEMA", "https");
	}
	// API_SERVER_HOST
	public static String API_SERVER_HOST()
	{
		return ConfigurationManager.GetInstance().GetConfigValue("HX_API_SERVER_HOST", "");
	}
	// APPKEY
	public static String APPKEY()
	{
		return ConfigurationManager.GetInstance().GetConfigValue("HX_APPKEY", "");
	}
	// APP_CLIENT_ID
	public static String APP_CLIENT_ID()
	{
		return ConfigurationManager.GetInstance().GetConfigValue("HX_APP_CLIENT_ID", "");
	}
	// APP_CLIENT_SECRET
	public static String APP_CLIENT_SECRET()
	{
		return ConfigurationManager.GetInstance().GetConfigValue("HX_APP_CLIENT_SECRET", "");
	}
}
