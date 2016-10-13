package com.fushionbaby.common.util.jsonp;

import java.util.HashMap;

import com.fushionbaby.common.constants.CommonConstant;

/**
 * Created by duxihu on 14-10-31.
 */
public class Jsonp {

	private java.util.Map<String, Object> jsonp;

	private Jsonp() {
		jsonp = new HashMap<String, Object>();
	}

	private Jsonp(String code, String msg) {
		jsonp = new HashMap<String, Object>();
		setResponseCode(code);
		setMsg(msg);
	}

	public String getResponseCode() {
		return (String) jsonp.get("response_code");
	}

	public Jsonp setResponseCode(String code) {
		if (null == code)
			jsonp.put("response_code", "");
		else
			jsonp.put("response_code", code);
		return this;
	}

	public String getMsg() {
		return (String) jsonp.get("msg");
	}

	public Jsonp setMsg(String msg) {
		if (null == msg)
			jsonp.put("msg", "");
		else {
			jsonp.put("msg", msg);
		}
		return this;
	}

	public static Jsonp success() {
		return newInstance(CommonConstant.CommonCode.SUCCESS_CODE, CommonConstant.CommonMessage.SUCCESS_MESSAGE);
	}

	public static Jsonp error() {
		return newInstance(CommonConstant.CommonCode.ERROR_CODE, CommonConstant.CommonMessage.ERROR_MESSAGE);
	}

	public static Jsonp error_md5() {
		return newInstance(CommonConstant.CommonCode.MD5_ERROR_CODE, CommonConstant.CommonMessage.MD5_ERROR_MESSAGE);
	}

	/** 短时间内短信发送量超出限制提示 */
	public static Jsonp smsNumberLimit() {
		return newInstance(CommonConstant.CommonCode.SMS_LIMIT_CODE, CommonConstant.CommonMessage.SMS_LIMIT_MESSAGE);
	}
	
	public static Jsonp error(String msg) {
		return newInstance(CommonConstant.CommonCode.ERROR_CODE, msg);
	}

	public static Jsonp paramError() {
		return newInstance(CommonConstant.CommonCode.PARAM_ERROR_CODE, CommonConstant.CommonMessage.PARAM_ERROR_MESSAGE);
	}

	public static Jsonp paramError(String msg) {
		return newInstance(CommonConstant.CommonCode.PARAM_ERROR_CODE, msg);
	}

	/** 用户没有登陆提示 */
	public static Jsonp noLoginError(String msg) {
		return newInstance(CommonConstant.CommonCode.NO_LOGIN_CODE, msg);
	}

	/** 结算序列失效 */
	public static Jsonp payOffFailedError(String msg) {
		return newInstance(CommonConstant.CommonCode.PAY_OFF_FAILED, msg);
	}

	/** 系统最新的版本 */
	public static Jsonp currentVersionState(String msg) {
		return newInstance(CommonConstant.CommonCode.CURRENT_VERSION, msg);
	}

	/** 查询信息失败 */
	public static Jsonp searchFailureError(String msg) {
		return newInstance(CommonConstant.CommonCode.SEARCH_FAILURE_CODE, msg);
	}

	/** 账号密码验证失败 */
	public static Jsonp acountFailureError(String msg) {
		return newInstance(CommonConstant.CommonCode.ACOUNT_FAILURE_CODE, msg);
	}
	
	public static Jsonp newInstance(String code, String msg) {
		return new Jsonp(code, msg);
	}

}
