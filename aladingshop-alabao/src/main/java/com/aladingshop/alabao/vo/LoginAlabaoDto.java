package com.aladingshop.alabao.vo;
/**
 * @description APP登录DTO
 * @author 索亮
 * @date 2015年9月9日下午4:00:12
 */
public class LoginAlabaoDto {
	/** 用户登录APP的标识 */
	private String sid;
	/** 登录用户名 */
	private String account;
	/** 登录密码 */
	private String loginPassword;

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
