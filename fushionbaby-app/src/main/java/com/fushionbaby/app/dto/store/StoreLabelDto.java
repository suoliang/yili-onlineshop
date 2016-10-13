/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月12日下午1:47:08
 */
package com.fushionbaby.app.dto.store;

import java.util.List;

/**
 * @description 门店的标签下面的商品
 * @author 孟少博
 * @date 2015年12月12日下午1:47:08
 */
public class StoreLabelDto {

	/** 标签编号*/
	private String labelCode;
	
	/** 标签名称*/
	private String labelName;
	
	/** 标签商品*/
	private List<StoreSkuDto> storeSkuList;
	
	
	

	public List<StoreSkuDto> getStoreSkuList() {
		return storeSkuList;
	}

	public void setStoreSkuList(List<StoreSkuDto> storeSkuList) {
		this.storeSkuList = storeSkuList;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	
	
	
}
