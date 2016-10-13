package com.fushionbaby.common.model;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * @author 张明亮
 * 向客户端响应json
 */
public class JsonResponseModel implements Serializable {
	
	/**
	 * 成功
	 */
	public static final String SUCCESS="success";
	
	/**
	 * 失败
	 */
	public static final String FAIL="fail";
	
	/**
	 * 给客户端响应成功还是失败
	 * success
	 * fail
	 */
	private String result;
	
	/**
	 * 消息体,操作成功提示文本,或者操作失败提示文本
	 */
	private String msg;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "JsonResponseModel [result=" + result + ", msg=" + msg + "]";
	}
	//操作成功的时候要用的方法，str为提示的返回信息
	public String Success(String str){
		this.setMsg(str);
		this.setResult(this.SUCCESS);
		return new Gson().toJson(this);
	}
	//操作失败的时候要用的方法，str为提示的返回信息
	public String Fail(String str){
		this.setMsg(str);
		this.setResult(this.FAIL);
		return new Gson().toJson(this);
	}
	
}
