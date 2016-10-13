package com.fushionbaby.common.condition.sku;

public class SkuCombinationQueryCondition extends BaseSkuQueryCondition {

	/** 商品组合描述*/
	private String descreption;
	/** 商品组合编号*/
	private String combinationId;

	public String getDescreption() {
		return descreption;
	}

	public void setDescreption(String descreption) {
		this.descreption = descreption;
	}

	public String getCombinationId() {
		return combinationId;
	}

	public void setCombinationId(String combinationId) {
		this.combinationId = combinationId;
	}
	
	
	
}
