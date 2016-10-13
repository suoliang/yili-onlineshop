package com.fushionbaby.common.dto.app;

import java.io.Serializable;

public class SysArticleCommentDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;// 唯一ID
	private String name;// 用户昵称
	private String date;// 时间
	private String content;// 内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
