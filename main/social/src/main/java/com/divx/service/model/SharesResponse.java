package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "SharesResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SharesResponse")
public class SharesResponse extends ServiceResponse {

	private List<Share> shares;
	private int startPos;
	private int count;

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}

	public int getStartPos() {
		return startPos;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

}
