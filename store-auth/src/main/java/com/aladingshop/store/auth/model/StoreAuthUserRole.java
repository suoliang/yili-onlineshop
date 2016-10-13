package com.aladingshop.store.auth.model;

import java.util.Date;
/***
 * 门店系统     用户角色关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月25日下午2:02:03
 */
public class StoreAuthUserRole {
	/** 自增id*/
	private Long id;
	/**用户id */
	private Long userId;
	/**角色id*/
	private Long roleId;
	/**更新时间*/
	private Date updateTime;
    /** 更新者id*/
	private Long updateId;


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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}





}
