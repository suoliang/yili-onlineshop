package com.aladingshop.store.service;

import java.util.List;

import com.aladingshop.store.model.StoreMemberAddress;
import com.aladingshop.store.querycondition.StoreMemberAddressQueryCondition;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月15日上午11:59:57
 */
public interface StoreMemberAddressService<T extends StoreMemberAddress> {
	void add(StoreMemberAddress storeMemberAddress);
	
	void update(StoreMemberAddress storeMemberAddress);
	
	void addOrUpdate(StoreMemberAddress storeMemberAddress);
	
	StoreMemberAddress findById(Long id);
	
	StoreMemberAddress findByMemberIdAndStoreCode(Long memberId,String storeCode);
	
	List<StoreMemberAddress> findByQueryCondition(StoreMemberAddressQueryCondition queryCondition);
}
