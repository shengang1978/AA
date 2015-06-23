package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DownCountResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownCountResponse")
public class DownCountResponse extends ServiceResponse{
	private int downCount;

	public int getDownCount() {
		return downCount;
	}

	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}

}
