package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.Score;

@XmlRootElement(name = "MediaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MediaResponse")
public class MediaResponse extends ServiceResponse {
	private Media media;
	private List<Lesson> lessons;
	private List<Score> scores;

	public Media getMedia() {
		return media;
	}

	public List<Lesson> getLesson() {
		return lessons;
	}

	public void setLesson(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public List<Score> getScore() {
		return scores;
	}

	public void setScore(List<Score> scores) {
		this.scores = scores;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
}
