package com.divx.service.domain.manager;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.MediaServiceHelper;
import com.divx.service.StorageServiceHelper;
import com.divx.service.Util;
import com.divx.service.impl.StorageAdapter;
import com.divx.service.impl.StorageAdapterFactory;
import com.divx.service.model.Adapter.AdapterType;
import com.divx.service.model.EndPublishOptionShell;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.EndPublishOption;
@Service
public class StorageManager {
	private static final Logger log = Logger.getLogger(StorageManager.class);	
	public ServiceResponse EndPublish(EndPublishOption endPublishOption){
		ServiceResponse res = new ServiceResponse();
		try{
			String storageAdapterType = ConfigurationManager.GetInstance().StorageAdapterType();
			
			AdapterType adapterType = null;
			/*switch(storageAdapterType){
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
			}*/
			if("File".equals(storageAdapterType)){
				adapterType = AdapterType.File;
			}else if("Hadoop".equals(storageAdapterType)){
				adapterType = AdapterType.Hadoop;
			}else if("AmazonS3".equals(storageAdapterType)){
				adapterType = AdapterType.AmazonS3;
			}else if("OSS".equals(storageAdapterType)){
				adapterType = AdapterType.OSS;
			}else{
				adapterType = AdapterType.File;
			}
			StorageAdapter storageAdapter= new StorageAdapterFactory().create(adapterType);
			
			String smilUrl = storageAdapter.process(endPublishOption);
		
			if(!"".equals(smilUrl) && !smilUrl.isEmpty()){
				endPublishOption.setSmilPath(smilUrl);	
				
			}
			EndPublishOptionShell opt = new EndPublishOptionShell();
			opt.EndPublishOption.setAssetId(endPublishOption.getAssetId());
			opt.EndPublishOption.setSmilPath(endPublishOption.getSmilPath());
			opt.EndPublishOption.setStatus(endPublishOption.getStatus());
			opt.EndPublishOption.setFileType(endPublishOption.getFileType());
			opt.EndPublishOption.setMessage(endPublishOption.getMessage());
			if(MediaServiceHelper.EndPublish(Util.ObjectToJson(opt))){
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			} else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("fail to call media endpublish");
			}
		}catch(Exception ex){
			Util.LogError(log, "Exception for StorageManager--", ex);
		}
		
		return res;
	}

}
