package com.divx.service.model.ugc;

import java.util.List;

public class Story
{
	private int action;             //操作 1增加，2修改 3 删除
	private int id;
	private int chapterId;      //所属Chapter的id
	private String title;		//故事标题
	private String desc;		//描述
	private int index;			//故事在Chapter中的排序位置
	private String cover;		//故事封面。比例：3*4。 宽最小240， 不要超过300
	private List<String> photos;	//图片集。按索引从小到大排序
	private String audio;		//声音文件在zip文件内的相对位置
	private String config;		//图片、声音对应配置。已半角逗号分隔，毫秒为单位的浮点数。记录页面截止播放时间点，用逗号分隔
	private String video;		//可选
	private long updateTime;    //最后更新时间
	private int version;		//书籍版本号。整数。修改就增加数值
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
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public List<String> getPhotos() {
		return photos;
	}
	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	
}