/**
 * 
 */
package com.fushionbaby.common.condition.sku;

/**
 * 商品搭配查询条件
 * 
 * @author mengshaobo
 * 
 */
public class SkuLinkSkusQueryCondition extends BaseSkuQueryCondition {
	/** 搭配的商品编号 */
	private String linkSkuCode;

	/** 用户号 */
	private Long adminId;


	public String getLinkSkuCode() {
		return linkSkuCode;
	}

	public void setLinkSkuCode(String linkSkuCode) {
		this.linkSkuCode = linkSkuCode;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

}
