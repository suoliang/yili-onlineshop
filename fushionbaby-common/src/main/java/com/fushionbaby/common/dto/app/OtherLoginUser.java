package com.fushionbaby.common.dto.app;

/***
 * 
 * @author xupeijun 用于解析 app 端传过来的第三方登陆用户信息json数据
 */
public class OtherLoginUser {

	/** 第三方唯一标志*/
	private String openid;
	/** 第三方用户昵称*/
	private String nickname;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
