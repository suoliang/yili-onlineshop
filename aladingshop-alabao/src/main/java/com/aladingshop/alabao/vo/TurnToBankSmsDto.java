package com.aladingshop.alabao.vo;

import java.io.Serializable;

/**
 * @description 转出到银行卡短信验证
 * @author 索亮
 * @date 2015年11月2日上午11:14:10
 */
public class TurnToBankSmsDto implements Serializable {
	private static final long serialVersionUID = -8521196506870032830L;

	/** 用户登录如意宝标识 */
	private String alabaoSid;
	/** 如意宝账户 */
	private String account;
	/** 短信标识码 */
	private String bankSmsCode;
	/** 来源 */
	private String sourceCode;

	public String getAlabaoSid() {
		return alabaoSid;
	}
	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getBankSmsCode() {
		return bankSmsCode;
	}
	public void setBankSmsCode(String bankSmsCode) {
		this.bankSmsCode = bankSmsCode;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

}
