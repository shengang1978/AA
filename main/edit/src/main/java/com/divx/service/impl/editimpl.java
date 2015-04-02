package com.divx.service.impl;

import javax.jws.WebService;

import com.divx.service.edit;
import com.divx.service.model.ServiceResponse;

@SuppressWarnings("restriction")
@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.edit", portName = "editimplPort", serviceName = "editimplService")
public class editimpl implements edit{

	@Override
	public ServiceResponse BeginEdit(int assetId, String location) {
		ServiceResponse res = new ServiceResponse();
		res.setResponseCode(0);
		res.setResponseMessage("Success");
		return res;
	}

	@Override
	public ServiceResponse CancelEdit(int assetId) {
		ServiceResponse res = new ServiceResponse();
		res.setResponseCode(0);
		res.setResponseMessage("Success");
		return res;
	}

}
