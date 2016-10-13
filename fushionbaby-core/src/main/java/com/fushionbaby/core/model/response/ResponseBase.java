package com.fushionbaby.core.model.response;

/**
 * 所有APP请求返回基本类
 * 
 * @author sun tao 2015年7月14日
 */
public class ResponseBase {
	/** 结果码 **/
	private String resultCode;
	/** 错误描述 **/
	private String errorMsg;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
