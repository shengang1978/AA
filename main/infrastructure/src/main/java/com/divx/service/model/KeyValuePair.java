package com.divx.service.model;

public class KeyValuePair<K, V> {
	public KeyValuePair(K k, V v)
	{
		this.key = k;
		this.value = v;
	}
	
	public KeyValuePair(){}
	private K	key;
	private V	value;
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
}
