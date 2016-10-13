package com.fushionbaby.auth.model;

import java.util.Date;

/***
 * 
 * @author King 索亮 
 * 用户角色中间表
 * 
 */
public class AuthUserRole {
	// 自增id字段
	private Long id;
	// 用户id
	private Long userId;
	// 角色id
	private Long roleId;
	// 版本时间
	private Date version;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}



}
