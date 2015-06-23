package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ValidateTokenResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateTokenResponse")
public class ValidateTokenResponse extends ServiceResponse {
	private boolean isTokenValid;

	public boolean isTokenValid() {
		return isTokenValid;
	}

	public void setTokenValid(boolean isTokenValid) {
		this.isTokenValid = isTokenValid;
	}
}
