package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "LikeOption")
public enum LikeOption {
	none,
	good,
	bad
}
