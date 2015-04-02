package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CommentOption")
public class CommentOption {
	private Comment comment;
	private CommentAsset asset;
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public CommentAsset getAsset() {
		return asset;
	}
	public void setAsset(CommentAsset asset) {
		this.asset = asset;
	}
}
