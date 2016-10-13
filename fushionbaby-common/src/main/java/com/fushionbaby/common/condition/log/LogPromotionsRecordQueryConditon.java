/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日下午1:32:36
 */
package com.fushionbaby.common.condition.log;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @description 活动商品交易记录查询条件
 * @author 孟少博
 * @date 2015年9月9日下午1:32:36
 */
public class LogPromotionsRecordQueryConditon  extends QueryCondition{
	/** 活动信息编号*/
	private String pmCode;

	/** 用户号*/
	private Long memberId;
	
	/** 商品编号*/
	private String skuCode;
	
	/** 订单编号*/
	private String orderCode;
	
	/** 是否 查询只当天记录(y：是)*/
	private String isToDay;

	
	
	public String getIsToDay() {
		return isToDay;
	}

	public void setIsToDay(String isToDay) {
		this.isToDay = isToDay;
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
