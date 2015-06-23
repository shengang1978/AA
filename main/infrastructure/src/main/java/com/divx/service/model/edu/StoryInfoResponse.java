package com.divx.service.model.edu;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;
import com.divx.service.model.edu.Lesson;

@XmlRootElement(name="StoryInfoResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="StoryInfoResponse")
public class StoryInfoResponse extends ServiceResponse{
	private Story story;
	private List<Lesson> lessons;
	public Story getStory() {
		return story;
	}
	public void setStory(Story story) {
		this.story = story;
	}
	public List<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
	

}
