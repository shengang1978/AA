package com.divx.service.model.ugc;

import java.util.List;

public class Book
{
	private int action;             //操作 1增加，2修改 3 删除
	private int id;             //全局唯一
	private int version;		//书籍版本号。整数。修改就增加数值
	private String title;		//标题
	private String desc;		//描述
	private String cover;		//书籍封面 比例：3*4。 宽最小240， 不要超过300
	private long updateTime;    //最后更新时间
	private String filePath;           //存储路径zip包路径  相对路径/ugc/打头
	private List<Chapter> chapters; //章节。按索引从小到大排序
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
	
	
}