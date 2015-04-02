package com.divx.service;

import java.util.Date;

import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;







public class StorageServiceHelper extends ServiceResponse {
	
	
		
	

	public static ServiceResponse EndPublish(String endPublishOption){
		ServiceResponse res = new ServiceResponse(); 
		try{
			String baseUrl = ConfigurationManager.GetInstance().StorageServiceBaseUrl();
			String url = String.format("%s/storage/EndPublish", baseUrl);
			String ret = Util.HttpPostJson(url, endPublishOption);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call uploadToCloud");
				return res;
			}
			res = Util.JsonToObject(ret, ServiceResponse.class);
		}catch(Exception ex){
			
		}
		return res;
	}
	
}
