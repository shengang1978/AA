package com.divx.service.model;

import java.util.List;

import com.divx.service.model.edu.Lesson;

public class Story extends Media {

	private String recordUrl;
	private String configs;
	private Integer lessonId;
	
	public String getRecordUrl() {
		return recordUrl;
	}
	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	public String getConfigs() {
		return configs;
	}
	public void setConfigs(String configs) {
		this.configs = configs;
	}
	public Integer getLessonId() {
		return lessonId;
	}
	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}
}
