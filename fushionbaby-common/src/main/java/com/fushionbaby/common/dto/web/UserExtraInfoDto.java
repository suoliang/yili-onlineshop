package com.fushionbaby.common.dto.web;

import java.io.Serializable;

public class UserExtraInfoDto implements Serializable {
	private static final long serialVersionUID = -8088422720980414367L;
	private Long member_id;// 会员ID
	private String nickname;// 会员昵称
	private int baby_fm;// 宝宝的爸-妈
	private int baby_gender;// 宝宝性别
	private String baby_birthday;// 宝宝生日
	private String weixin_no;//用户的微信号
	private String img_path;// 用户头像

	public Long getMember_id() {
		return member_id;
	}

	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getBaby_fm() {
		return baby_fm;
	}

	public void setBaby_fm(int baby_fm) {
		this.baby_fm = baby_fm;
	}

	public int getBaby_gender() {
		return baby_gender;
	}

	public void setBaby_gender(int baby_gender) {
		this.baby_gender = baby_gender;
	}

	public String getBaby_birthday() {
		return baby_birthday;
	}

	public void setBaby_birthday(String baby_birthday) {
		this.baby_birthday = baby_birthday;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getWeixin_no() {
		return weixin_no;
	}

	public void setWeixin_no(String weixin_no) {
		this.weixin_no = weixin_no;
	}
}
