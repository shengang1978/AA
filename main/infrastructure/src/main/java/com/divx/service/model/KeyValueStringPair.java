package com.divx.service.model;

public class KeyValueStringPair {
	public KeyValueStringPair(String k, String v)
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
