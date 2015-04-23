package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "V2GOption")
public class V2GOption {
	private int start;	//��ʼתʱ�䣬��λ ��
	private int duration; //��Ҫת�ĳ��ȣ���λ�롣 -1��ʾȫ����startת������
	private int x;		//clip���Ͻ�����x
	private int y;		//clip���Ͻ�����y
	private int width;	//-1:��ʾ����clip
	private int height;
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
