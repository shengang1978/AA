package com.divx.service.model.edu;


public class ScoreStat {
	public enum eStatDuration
	{
		Today,
		Total
	}
	private int listen;
	private int read;
	private int record;
	private int totalScore;	
	private int listenDuration;	//second
	private int readDuration;	//second
	private int recordDuration;	//second
	private eStatDuration statDuration;
	
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
	public eStatDuration getStatType() {
		return statDuration;
	}
	public void setStatType(eStatDuration statDuration) {
		this.statDuration = statDuration;
	}
}
