package com.divx.service.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.divx.service.model.RegisterOption.eRegisterType;
@XmlRootElement(name = "UserProfile")
public class UserProfile{
	private int userId;
	private String realname;
	private Integer	gender; //0: boy, 1:girl, 3:secret
	private Date	birthday;
	private String selfIntroduce;
	private String signature;	
	private String mobile2;
	private String email2;
	private String im;	//instant messaging.
	private String im2;	//instant messaging 2
	private String telephone;
	private String comefrom;
	private String tdcImgUrl;
	private String homepage;
	private String nickname;
	private String photourl;
	private eRegisterType registerType;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSelfIntroduce() {
		return selfIntroduce;
	}
	public void setSelfIntroduce(String selfIntroduce) {
		this.selfIntroduce = selfIntroduce;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getComefrom() {
		return comefrom;
	}
	public void setComefrom(String comefrom) {
		this.comefrom = comefrom;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public String getTdcImgUrl() {
		return tdcImgUrl;
	}
	public void setTdcImgUrl(String tdcImgUrl) {
		this.tdcImgUrl = tdcImgUrl;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getIm2() {
		return im2;
	}
	public void setIm2(String im2) {
		this.im2 = im2;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public eRegisterType getRegisterType() {
		return registerType;
	}
	public void setRegisterType(eRegisterType registerType) {
		this.registerType = registerType;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
}
