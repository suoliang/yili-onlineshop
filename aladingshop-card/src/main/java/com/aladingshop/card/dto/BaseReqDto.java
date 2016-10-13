/**
 * 
 */
package com.aladingshop.card.dto;

/**
 * @description 请求共性字段
 * @author 孙涛
 * @date 2015年9月21日下午2:45:40
 */
public class BaseReqDto {
	/** 用户SID */
	private String sid;

	/** 如意宝登陆sid */
	private String alabaoSid;

	/** 来源 */
	private String sourceCode;

	/** 益多宝卡号 */
	private String cardNo;
	
	/** 短信验证码 */
	private String smsCode; 
	/**手机号*/
	private String phone;
	
	/**缓存中 短信验证码对应的key */
	private String smsRandomNum;
	

	public String getSmsRandomNum() {
		return smsRandomNum;
	}

	public void setSmsRandomNum(String smsRandomNum) {
		this.smsRandomNum = smsRandomNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getAlabaoSid() {
		return alabaoSid;
	}

	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}


	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

}
