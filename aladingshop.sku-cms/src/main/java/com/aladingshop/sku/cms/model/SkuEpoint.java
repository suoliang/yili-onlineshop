package com.aladingshop.sku.cms.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 积分商品信息
 * @author chenyingtao
 *
 *
 */
public class SkuEpoint {
	
	private Long id;
	/**商品code*/
	private String skuCode;
	/**商品名称*/
	private String skuName;
	
	private Date createTime;
	private Date updateTime;
	/**商品标签*/
	private String labelCode;
	/**兑换商品需要的积分*/
	private BigDecimal needEpoint;
	
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
	public BigDecimal getNeedEpoint() {
		return needEpoint;
	}
	public void setNeedEpoint(BigDecimal needEpoint) {
		this.needEpoint = needEpoint;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
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
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	
	
}
