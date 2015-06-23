package com.divx.service.model.edu;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "SetResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetResponse")
public class SetResponse extends ServiceResponse {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
