package com.divx.service.domain.manager;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.Util;
import com.divx.service.domain.model.DcpRecver;
import com.divx.service.domain.model.DcpUserdevice;
import com.divx.service.domain.repository.DeviceDao;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.RegisterDeviceOption;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

@Service
public class DeviceManager {
	private Logger log = Logger.getLogger(DeviceManager.class);
	private DeviceDao	deviceDao;
	
	@Autowired
	public void setDeviceDao(DeviceDao deviceDao)
	{
		this.deviceDao = deviceDao;
	}
	
	public ServiceResponse RegisterDevice(RegisterDeviceOption option)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			KeyValuePair<Integer, String> kvp = Util.DecryptDeviceGuid(option.getDeviceGuid());
			if (kvp == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid Device Guid");
				return res;
			}
			
			DcpRecver recver = deviceDao.GetRecver(kvp.getKey());
			if (recver == null)
			{
				recver = new DcpRecver();
				recver.setUserId(kvp.getKey());
				recver.setUsername(option.getUsername());
				recver.setNickname(option.getNickname());
				recver.setPhotourl(option.getPhotourl());
				recver.setIsactive(true);
				recver.setDatecreated(new Date());
				deviceDao.AddRecver(recver);
			}
			else
			{
				//recver.setUserId(kvp.getKey());
				recver.setUsername(option.getUsername());
				recver.setNickname(option.getNickname());
				recver.setPhotourl(option.getPhotourl());
				recver.setIsactive(true);
				//recver.setDatecreated(new Date());
				deviceDao.UpdateRecver(recver);
			}
			
			DcpUserdevice dev = deviceDao.GetDevice(option.getDeviceGuid());
			if (dev == null)
			{
				dev = new DcpUserdevice();
				dev.setDatecreated(new Date());
				dev.setDatemodified(new Date());
				dev.setDeviceGuid(option.getDeviceGuid());
				dev.setDevicetype(option.getDeviceType());
				dev.setDeviceuniqueid(kvp.getValue());
				dev.setIsactive(true);
				dev.setUserId(kvp.getKey());
				deviceDao.AddDevice(dev);
			}
			else
			{
				//dev.setDatecreated(new Date());
				dev.setDatemodified(new Date());
				//dev.setDeviceGuid(option.getDeviceGuid());
				dev.setDevicetype(option.getDeviceType());
				//dev.setDeviceuniqueid(kvp.getValue());
				dev.setIsactive(true);
				//dev.setUserId(kvp.getKey());
				deviceDao.UpdateDevice(dev);
			}
			dev.setDatecreated(new Date());
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			if (option != null)
				Util.LogError(log, String.format("RegisterDevice(%s,%d) exception", option.getUsername(), option.getDeviceGuid()), ex);
			else
				Util.LogError(log, String.format("RegisterDevice(null) exception"), ex);
		}
		return res;
	}
	
	public ServiceResponse UnregisterDevice(String deviceGuid)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpUserdevice dev = deviceDao.GetDevice(deviceGuid);
			if (dev == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid Device Guid");
				return res;
			}
			
			dev.setIsactive(false);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("UnregisterDevice(%s) exception", deviceGuid), ex);
		}
		return res;
	}
}
