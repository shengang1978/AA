package com.divx.service.model;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "StartupResponse")
public class StartupResponse extends ServiceResponse {

	private List<KeyValueStringPair>	baseUrls;
	public List<KeyValueStringPair> getBaseUrls() {
		return baseUrls;
	}
	public void setBaseUrls(List<KeyValueStringPair> baseUrls) {
		this.baseUrls = baseUrls;
	}
}
