package com.divx.service.domain.manager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.Notification;
import com.divx.service.model.NotificationResponse;
import com.divx.service.model.ResponseCode;

public class AndroidMessagePushUtil {
	private static final Logger log = Logger.getLogger(AndroidMessagePushUtil.class);
	
	public static List<KeyValuePair<String, Integer>> androidMessagePushToMore(String message, List<String> tokens)
	{
		try
		{
			String reqUrl = ConfigurationManager.GetInstance().Message_Androidpn_URL() + "/notification/";
			
			Notification msg = new Notification();
			msg.setData(message);
			msg.setDeviceTokens(tokens);
			msg.setIsBroadcast(false);

			String retJson = Util.HttpPost(reqUrl, Util.ObjectToJson(msg));
			
			NotificationResponse res = Util.JsonToObject(retJson, NotificationResponse.class);
			if (res.getResponseCode() == ResponseCode.SUCCESS)
			{
				return res.getResult();
			}
			else
			{
				log.error(String.format("Fail to push android notification. %d %s", res.getResponseCode(), res.getResponseMessage()));
			}
		}
		catch(Exception ex)
		{
			//log.error("androidMessagePushToMore Exception", ex);
			Util.LogError(log, "AndroidMessagePushUtil Exception", ex);
		}
		return new LinkedList<KeyValuePair<String, Integer>>();
	}
}
