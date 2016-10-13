package com.aladingshop.store.dao;

import java.util.List;

import com.aladingshop.store.model.StoreMemberAddress;
import com.aladingshop.store.querycondition.StoreMemberAddressQueryCondition;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月15日上午11:25:04
 */
public interface StoreMemberAddressDao {
	
	void add(StoreMemberAddress storeMemberAddress);
	
	void update(StoreMemberAddress storeMemberAddress);
	
	StoreMemberAddress findById(Long id);
	
	List<StoreMemberAddress> findByQueryCondition(StoreMemberAddressQueryCondition queryCondition);
}
