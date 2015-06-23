package com.divx.service.model.im;

import com.divx.service.model.ServiceResponse;

public class HxResponse<T> extends ServiceResponse {
	private String statusCode;
	private String error;
	private String exception;
	private String error_description;
	private String action;
	private String applicatiion;
	private String params;
	private String path;
	private String uri;
	private String timestamp;
	private int duration;
	private String organization;
	private String applicationName;
	private T data;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getApplicatiion() {
		return applicatiion;
	}
	public void setApplicatiion(String applicatiion) {
		this.applicatiion = applicatiion;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
