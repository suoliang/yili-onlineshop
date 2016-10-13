package com.fushionbaby.auth.model;

import java.util.Date;
/***
 * 后台用户
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月27日下午6:14:13
 */
public class AuthUser {
	private Long id;
	/** 创建时间*/
	private Date createTime;
	/** 用户email*/
	private String email;
	/**更新时间*/
	private Date updateTime;
	/** 登陆的姓名*/
	private String loginName;
	/**用户描述*/
	private String memo;
	/**用户登录的密码*/
	private String password;
	/** 用户的电话*/
	private String phone;
	/** 版本*/
	private Date version;
    /** 用户状态  默认为1时不禁用，2禁用*/
	private Integer status;
	/**用户类型 1为系统用户，2为普通用户*/
    private Integer userType;
    /** 用户等级 1可处理所有订单  2可处理部分订单*/
    private Integer level;
    
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName == null ? null : loginName.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}


}