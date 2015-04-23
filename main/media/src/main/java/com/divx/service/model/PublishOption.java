package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PublishOption")
public class PublishOption {
	private TransOption TransOption;

	public TransOption getTransOption() {
		return TransOption;
	}

	public void setTransOption(TransOption transOption) {
		TransOption = transOption;
	}
	
}
