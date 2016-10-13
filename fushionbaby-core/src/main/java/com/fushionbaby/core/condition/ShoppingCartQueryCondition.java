/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月14日上午10:04:30
 */
package com.fushionbaby.core.condition;

import com.fushionbaby.common.condition.QueryCondition;

/**
 * @description 购物车查询条件
 * @author 孟少博
 * @date 2015年12月14日上午10:04:30
 */
public class ShoppingCartQueryCondition extends QueryCondition {

	/** 用户ID*/
	private Long memberId;
	
	/** 门店编号*/
	private String storeCode;
	
	/** 商品编号*/
	private String skuCode;
	
	/** 是否选中*/
    private String isSelected;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(String isSelected) {
		this.isSelected = isSelected;
	}
    
     
}
