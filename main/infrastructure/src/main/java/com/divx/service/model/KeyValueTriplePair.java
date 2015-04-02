package com.divx.service.model;

public class KeyValueTriplePair<K, V1, V2> {
	public KeyValueTriplePair(K k, V1 v1, V2 v2)
	{
		this.key = k;
		this.value1 = v1;
		this.value2 = v2;
		
	}
	public KeyValueTriplePair(){}
	private K key;
	private V1 value1;
	private V2 value2;
	
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V1 getValue1() {
		return value1;
	}
	public void setValue1(V1 value1) {
		this.value1 = value1;
	}
	public V2 getValue2() {
		return value2;
	}
	public void setValue2(V2 value2) {
		this.value2 = value2;
	}
	
	
	
	

}
