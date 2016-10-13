/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年10月28日下午2:02:59
 */
package com.fushionbaby.common.dto.verification;

/***
 * app 请求验证要传数据 封装对象
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月30日下午1:34:08
 */
public class VerificationReqDto {
	
    /**sid*/
	private String sid;
    /**alabaosid*/
    private String alabaoSid;	
	/** 银行卡号*/
	private String bankCardNo;
	/** 真实姓名*/
	private String trueName;
    /** 身份证号*/
	private String ID;
	
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getAlabaoSid() {
		return alabaoSid;
	}
	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}


}
