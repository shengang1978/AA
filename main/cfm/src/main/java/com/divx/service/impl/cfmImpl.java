package com.divx.service.impl;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.cfm;
import com.divx.service.domain.manager.ConfigManager;

@WebService(targetNamespace = "http://impl.service.divx.com/", endpointInterface = "com.divx.service.cfm", portName = "cfmImplPort", serviceName = "cfmImplService")
public class cfmImpl implements cfm {

	private ConfigManager configManager;
	
	@Autowired
	public void setConfigManager(ConfigManager configManager)
	{
		this.configManager = configManager;
	}

	@Override
	public Response GetConfigValue(String configKey) {
		return configManager.GetConfigValue(configKey, "");
	}

	@Override
	public Response GetConfigs() {
		return configManager.GetConfigs();
	}

}
