package com.fushionbaby.sysactivity.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.Date;

/***
 * 抽奖明细
 * 
 * @author xupeijun
 * 
 */
public class LotteryDetail {

	private Long id;
	private Long memberId;
	private String loginName;
	private Long prizeId;
	private String name;
	private String level;
	private Date createTime;
	private String showTime;
	private Long createId;
	private Date updateTime;
	private Long updateId;
	private String code;
	
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

	public Long getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getShowTime() {
		if(createTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			setShowTime(sdf.format(createTime));
		}
		return showTime;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



}
