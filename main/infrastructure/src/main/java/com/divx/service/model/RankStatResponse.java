package com.divx.service.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

public class RankStatResponse<T> extends ServiceResponse
{
	public enum eStatType
	{
		StoryPlayRank,	//Story Play Count
	}
	
	public enum eSort
	{
		A,	//ascending
		D	//descending
	}
	
	//@XmlTransient
	private List<T> items;
	private eStatType statType;
	private int startPos;
	private int count;
	private eSort sort;
	
	//@XmlTransient
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public int getStartPos() {
		return startPos;
	}
	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public eSort getSort() {
		return sort;
	}
	public void setSort(eSort sort) {
		this.sort = sort;
	}
	public eStatType getStatType() {
		return statType;
	}
	public void setStatType(eStatType statType) {
		this.statType = statType;
	}
}
