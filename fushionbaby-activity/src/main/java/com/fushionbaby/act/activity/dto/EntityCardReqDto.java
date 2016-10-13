package com.fushionbaby.act.activity.dto;
/***
 * 实体卡相关的请求参数
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月10日上午9:42:21
 */
public class EntityCardReqDto {

	/** 用户登录标志*/
	private String sid;
	
	/**实体卡编码*/
	private String entityCode;
	
    /** 阿拉宝sid*/
	private String alabaoSid;
	
	/**实体卡的密码*/
	private String password;
	
	/** 来源*/
	private String sourceCode;

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getAlabaoSid() {
		return alabaoSid;
	}

	public void setAlabaoSid(String alabaoSid) {
		this.alabaoSid = alabaoSid;
	}



}
