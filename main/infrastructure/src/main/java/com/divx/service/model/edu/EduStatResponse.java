package com.divx.service.model.edu;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "StatResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatResponse")
public class EduStatResponse  extends ServiceResponse{
	private List<ScoreStat>	scoreStat;

	public List<ScoreStat> getScoreStat() {
		return scoreStat;
	}

	public void setScoreStat(List<ScoreStat> scoreStat) {
		this.scoreStat = scoreStat;
	}
}
