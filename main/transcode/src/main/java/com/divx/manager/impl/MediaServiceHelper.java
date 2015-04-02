package com.divx.manager.impl;


import org.apache.log4j.Logger;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.EndPublishOptionShell;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

public class MediaServiceHelper {
	private static final Logger log = Logger.getLogger(MediaServiceHelper.class);
	
	public static boolean EndPublish(EndPublishOptionShell endOpt)
	{
		try
		{
			String baseUrl = ConfigurationManager.GetInstance().MediaServiceBaseUrl();
			String reqUrl = baseUrl + "/media/EndPublish";
			
			String reqRet = Util.HttpPutJson(reqUrl, endOpt, null);
			ServiceResponse sr = Util.JsonToObject(reqRet, ServiceResponse.class);
			if (sr.getResponseCode() == ResponseCode.SUCCESS)
				return true;
			else
			{
				log.error(String.format("Error To call EndPublish. %s, %s, %s", reqUrl, Util.ObjectToJson(endOpt), reqRet));
			}
		}
		catch(Exception ex)
		{
			log.error(String.format("Fail to call EndPublish(%s)", Util.ObjectToJson(endOpt)), ex);
		}
		return false;
	}
}
