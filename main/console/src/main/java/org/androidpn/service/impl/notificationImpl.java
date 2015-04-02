package org.androidpn.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import org.androidpn.server.util.Config;
import org.androidpn.server.xmpp.push.NotificationManager;
import org.androidpn.service.notification;
import org.androidpn.service.model.Notification;
import org.androidpn.service.model.NotificationResponse;
import org.directwebremoting.util.Logger;

import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueStringPair;
import com.divx.service.model.ResponseCode;
import com.divx.service.*;

@WebService(targetNamespace = "http://impl.service.androidpn.org/", endpointInterface = "org.androidpn.service.notification", portName = "notificationImplPort", serviceName = "notificationImplService")
public class notificationImpl implements notification {
	private static final Logger log = Logger.getLogger(notificationImpl.class);
	
	private NotificationManager notificationManager;

    public notificationImpl() {
        notificationManager = new NotificationManager();
    }
	@Override
	public Response sendNotification(Notification notify) {
		NotificationResponse res = new NotificationResponse();
		try
		{
	        String apiKey = Config.getString("apiKey", "");
			if (notify.getIsBroadcast())
			{
				notificationManager.sendBroadcast(apiKey, "System Message", notify.getData(), "");
			}
			else
			{
				List<KeyValuePair<String, Integer>> result = new LinkedList<KeyValuePair<String, Integer>>();
				List<String> tokens = notify.getDeviceTokens();
				
				for(String token: tokens)
				{
					try
					{
						NotificationManager.eNotifyResult ret = notificationManager.sendNotifcationToUser(apiKey, token, "DivX Ohana",
			                    notify.getData(), "");
						
						result.add(new KeyValuePair<String, Integer>(token, ret.ordinal()));
					}
					catch(Exception e)
					{
						log.warn(String.format("sendNotifcationToUser Error. (%s,%s,%s)", apiKey, token, notify.getData()), e);
						result.add(new KeyValuePair<String, Integer>(token, NotificationManager.eNotifyResult.eUnknowError.ordinal()));
					}
				}
		            
				res.setResult(result);
			}
	        
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception ex)
		{
			log.warn(String.format("messageId(%d)", notify.getMessageId()), ex);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Unknown Error");
		}
		
		return Util.ServiceResponseToResponse(res);
	}

}
