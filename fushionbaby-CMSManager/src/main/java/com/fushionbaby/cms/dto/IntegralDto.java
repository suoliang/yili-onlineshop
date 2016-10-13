package com.fushionbaby.cms.dto;

public class IntegralDto {

	
	/**商品编码*/
	private String skuCode;
	/**商品名称*/
	private String skuName;
	/**订单来源*/
	private String sourceCode;
	/**创建开始时间*/
	private String createTimeFrom;
	/**创建结束时间*/
	private String createTimeTo;
	
	public String getCreateTimeFrom() {
		return createTimeFrom;
	}
	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}
	public String getCreateTimeTo() {
		return createTimeTo;
	}
	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}
