package com.fushionbaby.sysactivity.model;

import java.util.Date;

/**
 * 抽奖记录表,记录了会员使用了多少次抽奖机会
 * 
 * @author King 索亮
 * 
 */
public class LotteryInfo {
	private Long id;
	private Long memberId;
	private String loginName;
	private Integer num;
	private Long createId;
	private Long updateId;
	private Date createTime;
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getNum() {
		return num == null ? 0 : num;
	}

	public void setNum(Integer num) {
		this.num = num;
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
