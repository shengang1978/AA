package com.divx.service.model.ugc;

import java.util.List;

public class Chapter
{
	private int action;             //操作 1增加，2修改 3 删除
	private int id;
	private int bookId;             //所属book的id
	private String title;			//标题
	private String desc;			//描述
	private int index;				//Chapter在Book中的排序位置
	private List<Story> stories;	//故事集。按索引从小到大排序
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<Story> getStories() {
		return stories;
	}
	public void setStories(List<Story> stories) {
		this.stories = stories;
	}
	
	
}