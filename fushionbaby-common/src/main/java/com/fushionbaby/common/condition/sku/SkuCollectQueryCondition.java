/**
 * 
 */
package com.fushionbaby.common.condition.sku;

/**
 * @author mengshaobo 商品收藏条件
 */
public class SkuCollectQueryCondition extends BaseSkuQueryCondition {
	/** 用户编号 */
	private Long memberId;
	
	private String skuCode;

	/** 是否取消收藏 */
	private String isAttention;

	public String getIsAttention() {
		return isAttention;
	}

	public void setIsAttention(String isAttention) {
		this.isAttention = isAttention;
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

}
