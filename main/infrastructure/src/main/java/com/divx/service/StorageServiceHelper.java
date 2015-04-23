package com.divx.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;







public class StorageServiceHelper extends ServiceResponse {
	
	
	private static final Logger log = Logger.getLogger(StorageServiceHelper.class);	
	

	public static ServiceResponse EndPublish(String endPublishOption){
		ServiceResponse res = new ServiceResponse(); 
		try{
			String baseUrl = ConfigurationManager.GetInstance().StorageServiceBaseUrl();
			String url = String.format("%s/storage/EndPublish", baseUrl);
			String ret = Util.HttpPost(url, endPublishOption);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call uploadToCloud");
				return res;
			}
			res = Util.JsonToObject(ret, ServiceResponse.class);
		}catch(Exception ex){
			Util.LogError(log, "Exception for storageHelper--", ex);
		}
		return res;
	}
	
}
