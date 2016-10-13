package com.fushionbaby.common.dto.app;

import java.io.Serializable;

/***
 * 户外活动列表
 * @author King 索亮
 * 
 */
public class SysActivitiesDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;// 唯一ID
	private String img_path;// 图片URL
	private String title;// 标题
	private String info;// 描述
	private String date_activity;// 活动日期+时间
	private String date_due;// 报名截止日期
	private String address;// 活动地址
	private String place_picture;// 活动地址图片地址位置
	private Integer person_max;// 参加人数最大值

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDate_activity() {
		return date_activity;
	}

	public void setDate_activity(String date_activity) {
		this.date_activity = date_activity;
	}

	public String getDate_due() {
		return date_due;
	}

	public void setDate_due(String date_due) {
		this.date_due = date_due;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlace_picture() {
		return place_picture;
	}

	public void setPlace_picture(String place_picture) {
		this.place_picture = place_picture;
	}

	public Integer getPerson_max() {
		return person_max;
	}

	public void setPerson_max(Integer person_max) {
		this.person_max = person_max;
	}

}
