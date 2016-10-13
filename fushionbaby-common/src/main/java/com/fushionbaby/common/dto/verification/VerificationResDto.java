package com.fushionbaby.common.dto.verification;

/***
 * 身份校验的返回结果封装  (包括  身份证姓名和 银行卡姓名的验证)
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月23日上午11:13:59
 */
public class VerificationResDto {
	/**返回数据 包含 code和message*/
	private String data;
	/** 请求返回处理状态  2001=正常服务  2002=第三方服务器异常   2003=服务器维护 2004=账号余额不足 2005=参数异常*/
	private String status;
	/** 处理结果 
	 *  1000=一致    1001=不一致    1002=库中无此号   1003=请稍后再试    1110=核验失败，请稍候再试  1101=商家 ID 不合法    1102=身份证姓名不合法 1103=身份证号码不合法
        1104=签名不合法  1105=第三方服务器异常  1106=账户余额不足  1107=tm 不合法     1108=其他异常       1109=账号被暂停*/
	private String code;
	/** 返回信息*/
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "VerificationResDto [data=" + data + ", status=" + status
				+ ", code=" + code + ", message=" + message + "]";
	}
	
	
}
