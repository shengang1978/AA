package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.divx.service.model.BaseSocialType.eLikeOption;

@XmlRootElement(name = "Like")
public class Like {
	
	private eLikeOption option;
	private int shareId;
	private int point;
	public eLikeOption getOption() {
		return option;
	}
	public void setOption(eLikeOption option) {
		this.option = option;
	}
	public int getShareId() {
		return shareId;
	}
	public void setShareId(int shareId) {
		this.shareId = shareId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
}
