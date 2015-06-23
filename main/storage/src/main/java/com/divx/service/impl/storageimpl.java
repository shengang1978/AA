package com.divx.service.impl;

import javax.jws.WebService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.Util;
import com.divx.service.storage;
import com.divx.service.model.*;
import com.divx.service.model.EndPublishOption;
import com.divx.service.domain.manager.StorageManager;
import com.divx.service.domain.manager.UploadManager;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.storage", portName = "storageImplPort", serviceName = "storageImplService")
public class storageimpl implements storage {
	private UploadManager uploadManager;
	private StorageManager storageManager;
	@Autowired
	public void setUploadManager(UploadManager uploadManager)
	{
		this.uploadManager = uploadManager;
	}
	@Autowired
	public void setStorageManager(StorageManager storageManager)
	{
		this.storageManager = storageManager;
	}

	@Override
	public Response GetUploadInfo(int mediaId) {
		
		/*AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			UploadInfoResponse res = new UploadInfoResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}*/
		
		return  Util.ServiceResponseToResponse(uploadManager.GetUploadInfo(mediaId, null));
		
	}

	@Override
	public Response CancelUpload(int mediaId) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			UploadInfoResponse res = new UploadInfoResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Response.status(Status.UNAUTHORIZED).entity(res).build();
		}
		
		return  Util.ServiceResponseToResponse(uploadManager.CancelUpload(mediaId));
	}


	@Override
	public Response EndPublish(EndPublishOption endPublishOption) {
		
		return  Util.ServiceResponseToResponse(storageManager.EndPublish(endPublishOption));
	}

	
}
