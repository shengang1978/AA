package com.divx.service.domain.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.apache.commons.lang.StringUtils;

import com.divx.service.ConfigurationManager;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;

public class IosMessagePushUtil {
	
	public static KeyValuePair<Integer,String> iosMessagePush(String message,List<String> tokens) {
		String certificatePath = ConfigurationManager.GetInstance().IosP12FilePath();
		String certificatePassword = ConfigurationManager.GetInstance().IosP12Password();
		boolean Message_Is_iOS_ProdCert = ConfigurationManager.GetInstance().GetConfigValue("Message_Is_iOS_ProdCert", true);
		KeyValuePair<Integer,String> result = new KeyValuePair<Integer,String>();
	/*	String certificatePath = "E:/证书.p12";
		String certificatePassword = "123456";// 此处注意导出的证书密码不能为空因为空密码会报错
*/		String sound = "default";// 铃音
		int badge = 1;
		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(message); // 消息内容
			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
			if (!StringUtils.isBlank(sound)) {
				payLoad.addSound(sound);// 铃音
			}
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, Message_Is_iOS_ProdCert));
			
			// 发送push消息
			
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payLoad, true);		
				result.setKey(notification.isSuccessful() ? 1 : 0);
				result.setValue(device.getToken());
			
			

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return result;
	}

	public static HashMap<Integer,KeyValuePair<List<Integer>, List<String>>> iosMessagePushToMore(String message,List<Integer> messageRecverIds,List<String> tokens) {
		String certificatePath = ConfigurationManager.GetInstance().IosP12FilePath();
		String certificatePassword = ConfigurationManager.GetInstance().IosP12Password();
		boolean Message_Is_iOS_ProdCert = ConfigurationManager.GetInstance().GetConfigValue("Message_Is_iOS_ProdCert", true);
		HashMap<Integer,KeyValuePair<List<Integer>, List<String>>> results = new HashMap<Integer,KeyValuePair<List<Integer>, List<String>>>();
		/*String certificatePath = "E:/证书.p12";
		String certificatePassword = "123456";// 此处注意导出的证书密码不能为空因为空密码会报错
*/		String sound = "default";// 铃音
		int badge = 1;
		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(message); // 消息内容
			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值

			if (!StringUtils.isBlank(sound)) {
				payLoad.addSound(sound);// 铃音
			}
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							certificatePath, certificatePassword, Message_Is_iOS_ProdCert));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			
				List<Device> device = new LinkedList<Device>();
				HashMap<String, Integer> map = new  HashMap<String, Integer>();
				for(int i=0;i< tokens.size(); i++){
					String token = tokens.get(i);
					device.add(new BasicDevice(token));
					int recverId = messageRecverIds.get(i);
					if(!map.containsKey(token)){
						map.put(token, recverId);
					}
				}
				/*for (String token : tokens) {
					device.add(new BasicDevice(token));
					
				}*/
				notifications = pushManager.sendNotifications(payLoad, device);
			
				
				
				
			for (PushedNotification notification : notifications) {
				if (notification.isSuccessful()) {
					if(!results.containsKey(0)){
						KeyValuePair<List<Integer>, List<String>> kev = new KeyValuePair<List<Integer>, List<String>>();
						kev.setKey(new LinkedList<Integer>());
						kev.setValue(new LinkedList<String>());
						results.put(0, kev);
					}
					results.get(0).getKey().add(map.get(notification.getDevice().getToken()));
					results.get(0).getValue().add(notification.getDevice().getToken());
					
					
				}else{
					if(!results.containsKey(1)){
						KeyValuePair<List<Integer>, List<String>> kev = new KeyValuePair<List<Integer>, List<String>>();
						kev.setKey(new LinkedList<Integer>());
						kev.setValue(new LinkedList<String>());
						results.put(1, kev);
					}
					results.get(1).getKey().add(map.get(notification.getDevice().getToken()));
					results.get(1).getValue().add(notification.getDevice().getToken());
					
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return results;

	}
	
	public static List<String> feedback() {
	/*	String certificatePath = "E:/证书.p12";
		String certificatePassword = "123456";*/
		String certificatePath = ConfigurationManager.GetInstance().IosP12FilePath();
		String certificatePassword = ConfigurationManager.GetInstance().IosP12Password();
		boolean Message_Is_iOS_ProdCert = ConfigurationManager.GetInstance().GetConfigValue("Message_Is_iOS_ProdCert", true);
		List<String> inactiveDeviceTokens = new LinkedList<String>(); 
		try{
			List<Device> inactiveDevices = Push.feedback(certificatePath, certificatePassword, Message_Is_iOS_ProdCert);
			if(inactiveDevices != null && inactiveDevices.size() > 0){
				for(Device devices : inactiveDevices){
					inactiveDeviceTokens.add(devices.getToken());
				}	
			}
			
		}catch(Exception ex){
			
		}
		
		
		return inactiveDeviceTokens;
	}
}
