package com.divx.service.model.edu;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LessonsOption")
public class LessonsOption {
	private int mediaId;
	private List<Lesson>	lessons;

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public int getMediaId() {
		return mediaId;
	}

	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
}
