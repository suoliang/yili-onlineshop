package com.aladingshop.sku.cms.model;

import java.util.Date;
/***
 * 商品和储值卡关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月7日下午1:50:17
 */
public class SkuCardRelation {
	private Long id;
	/** 商品code*/
	private String skuCode;
	/**储值卡 卡号*/
	private String cardCode;
	/** 创建时间 */
	private Date createTime;
	/** 创建id */
	private Long createId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateId() {
		return createId;
	}
	public void setCreateId(Long createId) {
		this.createId = createId;
	}


	
}