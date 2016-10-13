package com.fushionbaby.member.model;

import java.util.Date;

/**
 * 会员设备记录表
 * @author chenyingtao
 *
 */
public class MemberDevice {
	
	private Long id;
	/**会员手机号*/
	private String phone;
	/**移动设备的标志(mac地址）*/
	private String mac;
	/**类型（1、注册，2、活动）*/
	private String type;
	/**来源（1、安卓  2、ios）*/
	private String sourceCode;
	
	private Date createTime;
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
