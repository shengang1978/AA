package com.divx.service.model.edu;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "LessonsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LessonsResponse")
public class LessonsResponse extends ServiceResponse {
	private List<Lesson> lessons;

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
}
