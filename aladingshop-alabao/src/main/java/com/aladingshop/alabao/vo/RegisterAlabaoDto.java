package com.aladingshop.alabao.vo;

import java.io.Serializable;

/**
 * @description APP阿拉宝注册DTO
 * @author 索亮
 * @date 2015年9月9日下午2:41:33
 */
public class RegisterAlabaoDto implements Serializable{
	private static final long serialVersionUID = -4780270408848467833L;
	/**用户标识*/
	private String sid;
	/**注册手机号*/
	private String phone;
	/**注册标识码*/
	private String registerCode;
	/**来源*/
	private String sourceCode;

	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

}
