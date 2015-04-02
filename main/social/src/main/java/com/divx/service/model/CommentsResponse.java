package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CommentsResponse")
public class CommentsResponse extends ServiceResponse {
	private CommentAsset asset;
	private List<CommentFull> comments;

	public CommentAsset getAsset() {
		return asset;
	}
	public void setAsset(CommentAsset asset) {
		this.asset = asset;
	}
	public List<CommentFull> getComments() {
		return comments;
	}
	public void setComments(List<CommentFull> comments) {
		this.comments = comments;
	}
}
