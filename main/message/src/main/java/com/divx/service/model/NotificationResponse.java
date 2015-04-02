package com.divx.service.model;

import java.util.List;

import com.divx.service.model.KeyValueStringPair;
import com.divx.service.model.ServiceResponse;

public class NotificationResponse extends ServiceResponse {
	private List<KeyValuePair<String, Integer>> result;

	public List<KeyValuePair<String, Integer>> getResult() {
		return result;
	}

	public void setResult(List<KeyValuePair<String, Integer>> result) {
		this.result = result;
	}
}
