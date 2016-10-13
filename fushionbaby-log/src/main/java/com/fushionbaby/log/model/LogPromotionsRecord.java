/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:57:51
 */
package com.fushionbaby.log.model;

import java.util.Date;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:57:51
 */
public class LogPromotionsRecord {
	
	
	private Long id;
	
	/** 活动信息编号*/
	private String pmCode;

	/** 用户号*/
	private Long memberId;
	
	/** 商品编号*/
	private String skuCode;
	
	/** 订单编号*/
	private String orderCode;
	
	/** 交易状态*/
	private String recordStatus;
	/** 创建时间*/
	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
