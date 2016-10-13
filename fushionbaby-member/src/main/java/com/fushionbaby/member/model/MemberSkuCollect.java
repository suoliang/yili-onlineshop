package com.fushionbaby.member.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author King
 * 
 */
public class MemberSkuCollect {
	private Long id;

	private Long memberId;

	private String skuCode;

	private Date addTime;
	/***
	 * 是否取消收藏
	 */
	private String isAttention;

	private BigDecimal currentPrice;

	private BigDecimal retailPrice;

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


	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
	}

	@Override
	public String toString() {
		return "SkuCollect [id=" + id + ", memberId=" + memberId + ", skuCode="
				+ skuCode + ", addTime=" + addTime + ", isAttention="
				+ isAttention + ", currentPrice=" + currentPrice
				+ ", retailPrice=" + retailPrice + "]";
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(BigDecimal retailPrice) {
		this.retailPrice = retailPrice;
	}
}