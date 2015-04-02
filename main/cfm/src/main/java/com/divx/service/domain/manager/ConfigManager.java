package com.divx.service.domain.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.domain.model.DcpConfig;
import com.divx.service.domain.repository.ConfigDao;
import com.divx.service.domain.repository.impl.DbConfigSource;
import com.divx.service.model.Config;
import com.divx.service.model.ConfigResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.FixedDelayPollingScheduler;
import org.apache.log4j.Logger;

@Service
public class ConfigManager {
	private static final Logger log = Logger.getLogger(ConfigManager.class);
	
	private ConfigDao configDao;
	
	@Autowired
	public void setConfigDao(ConfigDao configDao)
	{
		this.configDao = configDao;
	}
	
	public ConfigManager()
	{
		PolledConfigurationSource source = new DbConfigSource();
		DynamicConfiguration configuration = new DynamicConfiguration(source, new FixedDelayPollingScheduler(300000, 600000, true));
		ConfigurationManager.install(configuration);
	}

	public Response GetConfigValue(String key, String defaultVal)
	{		
		DynamicStringProperty cfg = DynamicPropertyFactory.getInstance().getStringProperty(key, defaultVal);

		return Response.ok().entity(cfg.getValue()).build();
	}
	
	public Response GetConfigs()
	{
		try
		{
			List<DcpConfig> cfgs = configDao.GetConfigs();
			
			ConfigResponse res = new ConfigResponse();
			List<Config> ret = new ArrayList<Config>();
			
			for(DcpConfig obj: cfgs)
			{
				ret.add(ConfigHandlerFactory.CreateProduct(obj.getKey()).GetValue(obj.getKey(), obj.getValue()));
			}
			
			res.setConfigs(ret);
			return Response.ok().entity(res).build();
		}
		catch(Exception e)
		{
			ConfigManager.log.error("GetConfigs", e);
			return Response.status(Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
		}
	}
}
