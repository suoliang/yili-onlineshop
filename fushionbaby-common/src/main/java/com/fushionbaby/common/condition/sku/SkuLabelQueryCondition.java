/**
 * 
 */
package com.fushionbaby.common.condition.sku;

/**
 * @author mengshaobo 标签列表条件
 */
public class SkuLabelQueryCondition extends BaseSkuQueryCondition {
	/** 标签号 */
	private String code;
	/** 标签名称 */
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
