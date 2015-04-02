package com.divx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.ServiceHeaderHelper;
import com.divx.service.Util;
import com.divx.service.message;
import com.divx.service.domain.manager.DeviceManager;
import com.divx.service.domain.manager.MessageManager;
import com.divx.service.model.Message;
import com.divx.service.model.MyMessage;
import com.divx.service.model.MyMessagesResponse;
import com.divx.service.model.RegisterDeviceOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ResponseOption;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.SysMessage;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.message", portName = "messageImplPort", serviceName = "messageImplService")
public class messageImpl implements message {
	
	private MessageManager messageManager;
	private DeviceManager deviceManager;
	
	@Autowired
	public void setMessageManager(MessageManager messageManager)
	{
		this.messageManager = messageManager;
	}
	
	@Autowired
	public void setDeviceManager(DeviceManager deviceManager)
	{
		this.deviceManager = deviceManager;
	}
	
	@Override
	public Response SendUserMessage(Message msg) {
		ServiceResponse res = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(messageManager.AddMessage(msg, helper.getUserId()));
	}

	@Override
	public Response SendSystemMessage(SysMessage msg) {

		return Util.ServiceResponseToResponse(messageManager.AddMessage(msg));
	}

	@Override
	public Response MyMessages() {
		MyMessagesResponse res = new MyMessagesResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(messageManager.GetMessages(helper.getDeviceGuid()));
	}

	@Override
	public Response ResponseMessages(ResponseOption option) {
		ServiceResponse res = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Util.ServiceResponseToResponse(res);
		}
		
		return Util.ServiceResponseToResponse(messageManager.SetMessageStatus(helper.getUserId(), option.getMessageIds()));
	}

	@Override
	public String HasMessage() {

		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			return "0";
		}
		
		return messageManager.HasMessage(helper.getDeviceGuid()) ? "1" : "0";
	}

	@Override
	public Response RegisterDevice(RegisterDeviceOption option) {

		return Util.ServiceResponseToResponse(deviceManager.RegisterDevice(option));
	}

	@Override
	public Response UnregisterDevice(String deviceGuid) {

		return Util.ServiceResponseToResponse(deviceManager.UnregisterDevice(deviceGuid));
	}

}
