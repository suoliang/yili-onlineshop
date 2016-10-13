package com.fushionbaby.auth.model;

import java.util.Date;
/***
 * 后台用户订单关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月28日上午9:17:12
 */
public class AuthUserOrderRelation {
	private Long id;
	/** 创建时间*/
	private Date createTime;
	/** 后台关联用户的userId*/
	private Long userId;
	/**更新时间*/
	private Date updateTime;
	/**订单的会员id*/
	private Long memberId;
	/**订单号*/
	private String orderCode;
	/**更新者的id*/
	private Long updateId;
	/**后台登陆的用户名（和userId一致）*/
	private String userName;
	
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

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Long getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}



}