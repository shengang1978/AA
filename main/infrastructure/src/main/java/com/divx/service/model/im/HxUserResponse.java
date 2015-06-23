package com.divx.service.model.im;

import com.divx.service.model.ServiceResponse;

public class HxUserResponse<T> extends ServiceResponse {
	private String statusCode;
	private String error;
	private String exception;
	private String error_description;
	private String action;
	private String application;
	private String path;
	private String uri;
	private T entities;
	private String timestamp;
	private int duration;
	private String organization;
	private String applicationName;
	private String cursor;
	private int count;
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
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
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
	public T getEntities() {
		return entities;
	}
	public void setEntities(T entities) {
		this.entities = entities;
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
	public String getCursor() {
		return cursor;
	}
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
}
