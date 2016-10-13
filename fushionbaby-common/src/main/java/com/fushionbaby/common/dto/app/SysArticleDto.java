package com.fushionbaby.common.dto.app;

import java.io.Serializable;

/***
 * 亲子课程
 * 
 * @author King 索亮
 * 
 */
public class SysArticleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String article_id;// 唯一ID
	private String img_path;// 图片URL 亲子课程和风尚宝贝共用
	private String title;// 活动标题    风尚宝贝标题
	private String date;// 时间
	private String content;// 内容
	
	private String web_url;//风尚宝贝的  详情页面的网页地址

	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}
}
