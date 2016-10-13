package com.aladingshop.store.auth.model;

import java.util.Date;
/***
 * 门店系统   后台用户
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月25日下午2:03:33
 */
public class StoreAuthUser {
	/** 自增id*/
	private Long id;
	/** 登陆用户名*/
	private String name;
	/** 登陆密码*/
	private String password;
	/** 门店编号*/
	private String storeNumber;
	/** 门店编码*/
	private String storeCode;
	/** Email*/
	private String email;
	/** 电话*/
	private String phone;
	 /** 用户类型（系统，普通）*/
    private Integer userType;
	/** 创建时间*/
	private Date createTime;
    /** 创建者id*/
	private Long createId;
	/** 更新者id*/
	private Long updateId;
	/** 更新时间*/
	private Date updateTime;
	/** 说明*/
	private String memo;
	/** 是否禁用登陆  y 禁，n 不禁*/
	private String isDisabled;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getIsDisabled() {
		return isDisabled;
	}
	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}
	public String getStoreNumber() {
		return storeNumber;
	}
	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}
	
}
   