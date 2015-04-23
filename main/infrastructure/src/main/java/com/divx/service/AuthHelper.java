package com.divx.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.divx.service.model.AuthHelperModel;
import com.divx.service.model.DcpBaseType;
import com.divx.service.model.ResponseCode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class AuthHelper {
	public AuthHelper(String strToken)
	{
		this.token = "";
		this.userId = -1;
		isServiceToken = false;
		
		if (strToken != null && !strToken.isEmpty())
		{
			try {			
					AuthHelperModel.CheckUserResponse obj = CheckUserResponseCallable(strToken);
					
					if (obj.getResponseCode() == ResponseCode.SUCCESS)
					{
						userId = obj.getUserId();
						token = obj.getToken();
						deviceGuid = obj.getDeviceGuid();
						deviceType = DcpBaseType.eDeviceType.values()[obj.getDeviceType()];
						deviceUniqueId = obj.getDeviceUniqueId();
					}
						
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	public static final Cache<String, AuthHelperModel.CheckUserResponse> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(1, TimeUnit.HOURS).build(); 

	public AuthHelperModel.CheckUserResponse CheckUserResponseCallable(
			final String key) throws ExecutionException {
		// 没有使用CacheLoader
		AuthHelperModel.CheckUserResponse resultVal = cache.get(key,new Callable<AuthHelperModel.CheckUserResponse>(){
					@Override
					public AuthHelperModel.CheckUserResponse call() {
						String baseUrl = ConfigurationManager.GetInstance().CheckTokenURL();
						AuthHelperModel.CheckUserResponse obj = null;
						String strRet;
						try {
							strRet = Util.HttpGet(baseUrl + key);
							if (strRet != null && strRet != "") {
								obj = Util.JsonToObject(strRet,AuthHelperModel.CheckUserResponse.class);

							}
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						
						return obj;
					}
				});
		

		return resultVal;

	}
			
	public AuthHelper()
	{
		this.token = "";
		this.userId = -1;
		isServiceToken = false;
		
		deviceType = DcpBaseType.eDeviceType.Android;
		
		ServiceHeaderHelper helper = new ServiceHeaderHelper();

		localAddr = helper.getLocalAddr();
		
		String strToken = helper.getHeader("Token");
		String strServiceToken = helper.getHeader("ServiceToken");

		if (strToken != null && !strToken.isEmpty())
		{
			try{
				AuthHelperModel.CheckUserResponse obj = CheckUserResponseCallable(strToken);
				
				if (obj.getResponseCode() == ResponseCode.SUCCESS)
				{
					this.userId = obj.getUserId();
					this.token = obj.getToken();
					this.deviceGuid = obj.getDeviceGuid();
					deviceType = DcpBaseType.eDeviceType.values()[obj.getDeviceType()];
					deviceUniqueId = obj.getDeviceUniqueId();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if (strServiceToken != null && !strServiceToken.isEmpty())
		{
			isServiceToken = ConfigurationManager.GetInstance().IsServiceToken(strServiceToken);
			setServiceToken(strServiceToken);
		}
		else
		{
			deviceType = DcpBaseType.eDeviceType.values()[GetHeadValue(helper, "DeviceType", 0)];
		}
		
		appType = DcpBaseType.eAppType.values()[GetHeadValue(helper, "AppType", 0)];
	}	
	
	public boolean isGuest()
	{
		return token == null || token.isEmpty();
	}
	
	public boolean isService()
	{
		return isServiceToken;
	}
	
	public int getUserId()
	{
		return userId;
	}
	
	public String getToken()
	{
		return token;
	}
	public DcpBaseType.eDeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DcpBaseType.eDeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceGuid() {
		return deviceGuid;
	}

	public void setDeviceGuid(String deviceGuid) {
		this.deviceGuid = deviceGuid;
	}
	public String getDeviceUniqueId() {
		return deviceUniqueId;
	}

	public void setDeviceUniqueId(String deviceUniqueId) {
		this.deviceUniqueId = deviceUniqueId;
	}
	
	public String getLocalAddr() {
		return localAddr;
	}

	public void setLocalAddr(String localAddr) {
		this.localAddr = localAddr;
	}

	public DcpBaseType.eAppType getAppType() {
		return appType;
	}

	public void setAppType(DcpBaseType.eAppType appType) {
		this.appType = appType;
	}
	public String getServiceToken() {
		return serviceToken;
	}

	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}
	// Here are the properties of the client if the request is from the client;
	private String token;
	private int userId;
	private DcpBaseType.eDeviceType deviceType;
	private DcpBaseType.eAppType appType;
	private String deviceGuid;
	private String deviceUniqueId;
	
	private String localAddr;
	
	// Here are the properties of the service if the request is from the service.
	private String serviceToken;
	private boolean isServiceToken;
	
	private int GetHeadValue(ServiceHeaderHelper helper, String headName, int defaultValue)
	{
		int value = defaultValue;
		String strValue = helper.getHeader(headName);
		if (strValue != null && !strValue.isEmpty()){
			try
			{
				value = Integer.parseInt(strValue);
			}
			catch(Exception e)
			{
				value = defaultValue;
			}
		}
		
		return value;
	}
}
