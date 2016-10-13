/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月15日上午11:33:27
 */
package com.aladingshop.store.querycondition;

import com.fushionbaby.common.condition.QueryCondition;


/**
 * @description 门店用户地址查询条件
 * @author 孟少博
 * @date 2015年12月15日上午11:33:27
 */
public class StoreMemberAddressQueryCondition extends QueryCondition {

	/** 用户Id*/
	private Long memberId;
	
	/** 门店编号*/
	private String StoreCode;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getStoreCode() {
		return StoreCode;
	}

	public void setStoreCode(String storeCode) {
		StoreCode = storeCode;
	}
	
	
	
}
