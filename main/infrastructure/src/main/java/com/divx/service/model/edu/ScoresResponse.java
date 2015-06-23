package com.divx.service.model.edu;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "ScoresResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScoresResponse")
public class ScoresResponse extends ServiceResponse {
	private List<Score>	scores;

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
}
