package com.fushionbaby.common.dto.app;

import java.io.Serializable;

/***
 * 风尚宝贝
 * @author King 索亮
 *
 */
public class SysActivityWillDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String will_id;// 唯一ID
	private String img_path;// 图片URL 风尚宝贝
	private String web_url;//风尚宝贝的  详情页面的网页地址

	public String getWill_id() {
		return will_id;
	}

	public void setWill_id(String will_id) {
		this.will_id = will_id;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
}
