/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:57:51
 */
package com.fushionbaby.cms.dto;

import java.util.Date;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:57:51
 */
public class LogPromotionsRecordDto {
	
	
	private Long id;
	
	/** 活动信息编号*/
	private String pmCode;
	
	/** 活动名称*/
	private String promotionsName;

	/** 用户号*/
	private Long memberId;
	
	/** 用户名*/
	private String loginName;
	
	/** 商品编号*/
	private String skuCode;
	
	/** 商品名称*/
	private String skuName;
	
	/** 订单编号*/
	private String orderCode;
	
	/** 交易状态*/
	private String recordStatus;
	
	private Date createTime;

	
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getPromotionsName() {
		return promotionsName;
	}

	public void setPromotionsName(String promotionsName) {
		this.promotionsName = promotionsName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPmCode() {
		return pmCode;
	}

	public void setPmCode(String pmCode) {
		this.pmCode = pmCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	
}
