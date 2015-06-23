package com.divx.service.model.edu;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Score")
public class Score {
	private int scoreId;
	private int lessonId;
	private int listen;
	private int read;
	private int record;
	private int totalScore;
	private int listenDuration;
	private int readDuration;
	private int recordDuration;
	private int readCount;
	private Date date;
	
	public int getListenDuration() {
		return listenDuration;
	}
	public void setListenDuration(int listenDuration) {
		this.listenDuration = listenDuration;
	}
	public int getReadDuration() {
		return readDuration;
	}
	public void setReadDuration(int readDuration) {
		this.readDuration = readDuration;
	}
	public int getRecordDuration() {
		return recordDuration;
	}
	public void setRecordDuration(int recordDuration) {
		this.recordDuration = recordDuration;
	}
	public int getScoreId() {
		return scoreId;
	}
	public void setScoreId(int scoreId) {
		this.scoreId = scoreId;
	}
	public int getLessonId() {
		return lessonId;
	}
	public void setLessonId(int lessonId) {
		this.lessonId = lessonId;
	}
	public int getListen() {
		return listen;
	}
	public void setListen(int listen) {
		this.listen = listen;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	
}
