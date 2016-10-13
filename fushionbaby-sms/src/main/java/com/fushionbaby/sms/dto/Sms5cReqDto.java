package com.fushionbaby.sms.dto;

/***
 * 美联软通短信服务   请求参数
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月24日下午4:10:23
 */
public class Sms5cReqDto {
	
	
	/**要发送的商户名*/
	private  String  userName="shyili"; 
	/** 商户密码*/
	private  String  password="asdf123"; 
	/** 商户调用app的key*/
	private  String  apiKey="bbd1cf5ce9f7477cd21d5f6be4c5af30"; 
	/**接受的手机号*/
	private String mobile;
	/** 发送的短信内容*/
	private String content;
	/**要绑定的ip，多个用逗号分开*/
	private String ip;
	/** ip 绑定的查询  0 位绑定，1为查询 2 位清空*/
	private String action;
	
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getApiKey() {
		return apiKey;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Sms5cReqDto(String mobile, String content) {
		super();
		this.mobile = mobile;
		this.content = content;
	}
	public Sms5cReqDto() {
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}


}
