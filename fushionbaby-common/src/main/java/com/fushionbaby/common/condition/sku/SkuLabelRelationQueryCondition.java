/**
 * 
 */
package com.fushionbaby.common.condition.sku;

/**
 * @author mengshaobo
 * 
 */
public class SkuLabelRelationQueryCondition extends BaseSkuQueryCondition {
	
	/** 是否是首页*/
	private String appHome;
	
	/** 标签序号 */
	private String labelCode;
	
	/** 门店编号*/
	private String storeCode;

	/** 是否禁用 */
	private String disabled;
	
	/** 商品状态 */
	private String status;
	
	
	
	

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getAppHome() {
		return appHome;
	}

	public void setAppHome(String appHome) {
		this.appHome = appHome;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
