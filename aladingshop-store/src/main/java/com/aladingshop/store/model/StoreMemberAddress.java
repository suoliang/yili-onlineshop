package com.aladingshop.store.model;

import java.util.Date;

/**
 * 
 * @description 门店用户地址
 * @author 孟少博
 * @date 2015年12月15日上午11:15:35
 */
public class StoreMemberAddress {

	
	private Long id;
	
	/** 用户ID*/
	private Long memberId;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 联系人*/
	private String contactor;
	
	/** 手机*/
	private String mobile;
	
	/** 详细地址*/
	private String address;
	
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

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
