package com.divx.service.domain.manager;

import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MediaServiceHelper;
import com.divx.service.Util;
import com.divx.service.impl.StorageAdapter;
import com.divx.service.impl.StorageAdapterFactory;
import com.divx.service.model.Adapter.AdapterType;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.EndPublishOptionShell.EndPublishOption;
@Service
public class StorageManager {
	public ServiceResponse EndPublish(EndPublishOption endPublishOption){
		ServiceResponse res = new ServiceResponse();
		try{
			String storageAdapterType = ConfigurationManager.GetInstance().StorageAdapterType();
			AdapterType adapterType = null;
			switch(storageAdapterType){
				case "File":
					adapterType = AdapterType.File;
				case "Hadoop":
					adapterType = AdapterType.Hadoop;
				case "AmazonS3":
					adapterType = AdapterType.AmazonS3;
				case "OSS":
					adapterType = AdapterType.OSS;
				default :
					adapterType = AdapterType.File;	
			}
			StorageAdapter storageAdapter= new StorageAdapterFactory().create(adapterType);
			String smilUrl = storageAdapter.process(endPublishOption);
			if(!"".equals(smilUrl) && !smilUrl.isEmpty()){
				endPublishOption.setSmilPath(smilUrl);	
			}
			MediaServiceHelper.EndPublish(Util.ObjectToJson(endPublishOption)) ;
		}catch(Exception ex){
			
		}
		
		return res;
	}

}
