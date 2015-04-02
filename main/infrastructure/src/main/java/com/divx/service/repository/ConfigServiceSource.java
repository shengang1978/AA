package com.divx.service.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.divx.service.Util;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;

public class ConfigServiceSource implements PolledConfigurationSource {

	public class Config {
		public Config(String k, String v)
		{
			key = k;
			value = v;
		}
		private String key;
		private String value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public class ConfigResponse {
		private List<Config>	configs;

		public List<Config> getConfigs() {
			return configs;
		}

		public void setConfigs(List<Config> configs) {
			this.configs = configs;
		}

	}
	
	public ConfigServiceSource(String cfmServiceBaseUrl)
	{
		this.cmfServiceBaseUrl = cfmServiceBaseUrl;
	}
	
	public PollResult poll(boolean initial, Object checkPoint) throws Exception {
		String reqUrl = cmfServiceBaseUrl + "/cfm/Configs";
		String strRet = Util.HttpGet(reqUrl);
		
		ConfigServiceSource.ConfigResponse cr = Util.JsonToObject(strRet, ConfigServiceSource.ConfigResponse.class);
		
		Map<String, Object> cfs = new HashMap<String, Object>();
		for(Config obj : cr.getConfigs())
		{
			cfs.put(obj.getKey(), obj.getValue());
		}
		return PollResult.createFull(cfs);
	}
	
	private String cmfServiceBaseUrl;
}
