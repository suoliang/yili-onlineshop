package com.aladingshop.store.auth.model;

import java.util.Date;

/***
 * 门店系统  角色权限关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月25日下午2:03:58
 */
public class StoreAuthRolePrivilege {
	/** 自增id*/
	private Long id;
	/** 角色id*/
	private Long roleId;
	/** 权限id*/
	private Long privilegeId;
	/**更新者id*/
	private Long updateId;
	/** 跟新时间*/
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
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

	
}