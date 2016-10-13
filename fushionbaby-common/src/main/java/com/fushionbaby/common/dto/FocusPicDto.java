package com.fushionbaby.common.dto;

import java.io.Serializable;

/**
 * 
 * @author guolongchao 焦点图Dto
 */
public class FocusPicDto implements Serializable {
	private static final long serialVersionUID = 2821287270805463219L;
	/** 焦点图地址 */
	private String picturePath;
	/** 点击图片跳转到的地址 */
	private String url;
	/** 图片高度*/
	private String height;
	


	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

}
