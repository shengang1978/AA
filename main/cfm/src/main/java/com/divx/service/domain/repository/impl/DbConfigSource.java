package com.divx.service.domain.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.domain.model.DcpConfig;
import com.divx.service.domain.repository.ConfigDao;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;


public class DbConfigSource implements PolledConfigurationSource {
	private ConfigDao	configDao;
	
	@Autowired
	public void setConfigDao(ConfigDao configDao)
	{
		this.configDao = configDao;
	}
	@Override
	public PollResult poll(boolean arg0, Object arg1) throws Exception {
		
		if (configDao == null)
		{
			configDao = new ConfigDaoImpl();
		}
		
		List<DcpConfig> cfgs = configDao.GetConfigs();
		if (arg0 || arg1 == null)
		{
			Map<String, Object> src = new HashMap<String, Object>();
			
			for(DcpConfig c: cfgs)
			{
				src.put(c.getKey(), c.getValue());
			}
			
			return PollResult.createFull(src);
		}
		
//		if (arg1 != null)
//		{
//			Map<String, Object> src = new HashMap<String, Object>();
//			
//			for(DcpConfig c: cfgs)
//			{
//				src.put(c.getKey(), c);
//			}
//			return PollResult.createIncremental(added, changed, deleted, checkPoint)
//		}
		
		return null;
	}

}
