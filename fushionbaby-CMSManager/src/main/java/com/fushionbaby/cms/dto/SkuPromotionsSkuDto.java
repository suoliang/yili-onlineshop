package com.fushionbaby.cms.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SkuPromotionsSkuDto {
	private Long id;
	/** 标签编码 */
	private String pmCode;
	
	/** 商品编码 */
	private String skuNo;
	
	/** 商品条形码 */
	private String barCode;
	
	/**商品唯一编号*/
	private String uniqueCode;
	
	/** 活动状态 */
	private String skuPromotionsStatus;
	
	/** 显示顺序*/
	private String sort;
	
	/** 商品名称*/
	private String skuName;
	
	/** 商品状态*/
	private String skuStatus;
	
	/** 创建时间*/
	private Date createTime;
	
	private BigDecimal specialPrice;
	
	private Integer limitCount;

	
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	public String getSkuPromotionsStatus() {
		return skuPromotionsStatus;
	}

	public void setSkuPromotionsStatus(String skuPromotionsStatus) {
		this.skuPromotionsStatus = skuPromotionsStatus;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(String skuStatus) {
		this.skuStatus = skuStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(BigDecimal specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Integer getLimitCount() {
		return limitCount;
	}

	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}
	
	

}
