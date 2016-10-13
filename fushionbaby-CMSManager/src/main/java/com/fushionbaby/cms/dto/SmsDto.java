package com.fushionbaby.cms.dto;

/**
 * 短信发送成功和失败
 * 
 * @author King 索亮
 * 
 */
public class SmsDto {
	/**发送成功条数*/
	private int sendSuccess;
	/**发送失败条数*/
	private int sendFail;

	public int getSendSuccess() {
		return sendSuccess;
	}

	public void setSendSuccess(int sendSuccess) {
		this.sendSuccess = sendSuccess;
	}

	public int getSendFail() {
		return sendFail;
	}

	public void setSendFail(int sendFail) {
		this.sendFail = sendFail;
	}

}
