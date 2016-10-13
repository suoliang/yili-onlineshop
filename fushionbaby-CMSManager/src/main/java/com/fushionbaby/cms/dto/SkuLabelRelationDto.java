package com.fushionbaby.cms.dto;

import java.util.Date;

public class SkuLabelRelationDto {
	
	/** 标签编码 */
	private String labelCode;
	
	/** 条形码*/
	private String barCode;
	
	/** 是否会员*/
	private String isMemberSku;
	
	/** 商品编码 */
	private String skuNo;
	
	/**商品唯一编号*/
	private String unCode;
	
	/** 是否禁用 */
	private String disabled;
	
	/** 显示顺序*/
	private String showOrder;
	
	/** 商品名称*/
	private String skuName;
	
	/** 商品状态*/
	private String skuStatus;
	
	/** 创建时间*/
	private Date createTime;
	
	
	
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getIsMemberSku() {
		return isMemberSku;
	}

	public void setIsMemberSku(String isMemberSku) {
		this.isMemberSku = isMemberSku;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSkuStatus() {
		return skuStatus;
	}

	public void setSkuStatus(String skuStatus) {
		this.skuStatus = skuStatus;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}


	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(String showOrder) {
		this.showOrder = showOrder;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getUnCode() {
		return unCode;
	}

	public void setUnCode(String unCode) {
		this.unCode = unCode;
	}

}
