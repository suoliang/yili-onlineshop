package com.aladingshop.alabao.vo;

import java.io.Serializable;

/**
 * @description APP开通我的阿拉宝DTO
 * @author 索亮
 * @date 2015年9月9日下午2:06:01
 */
public class OpenAlabaoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 用户标识 */
	private String sid;
	/** 如意宝账户 */
	private String account;
	/** 登录密码 */
	private String loginPassword;
	/** 支付密码 */
	private String payPassword;
	/** 真实姓名 */
	private String trueName;
	/** 身份证号 */
	private String identityCardNo;
	/** 注册标识码 */
	private String registerCode;
	/** 来源 */
	private String sourceCode;
	/** 短信验证码 */
	private String smsCode;

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
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getIdentityCardNo() {
		return identityCardNo;
	}
	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
