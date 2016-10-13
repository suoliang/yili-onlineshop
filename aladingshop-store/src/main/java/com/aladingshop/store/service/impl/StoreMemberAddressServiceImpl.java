/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月15日下午1:22:53
 */
package com.aladingshop.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.store.dao.StoreMemberAddressDao;
import com.aladingshop.store.model.StoreMemberAddress;
import com.aladingshop.store.querycondition.StoreMemberAddressQueryCondition;
import com.aladingshop.store.service.StoreMemberAddressService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月15日下午1:22:53
 */
@Service
public class StoreMemberAddressServiceImpl implements StoreMemberAddressService<StoreMemberAddress> {

	@Autowired
	private StoreMemberAddressDao storeMemberAddressDao;
	
	public void add(StoreMemberAddress storeMemberAddress) {

		storeMemberAddressDao.add(storeMemberAddress);
		
	}

	public void update(StoreMemberAddress storeMemberAddress) {
		
		storeMemberAddressDao.update(storeMemberAddress);
		
	}
	
	public void addOrUpdate(StoreMemberAddress storeMemberAddress) {
		if(storeMemberAddress.getId()==null){
			this.add(storeMemberAddress);
			return ;
		}
		this.update(storeMemberAddress);
		
	}

	public StoreMemberAddress findById(Long id) {
		
		return storeMemberAddressDao.findById(id);
	}

	public StoreMemberAddress findByMemberIdAndStoreCode(Long memberId,
			String storeCode) {
		
		StoreMemberAddressQueryCondition queryCondition = new StoreMemberAddressQueryCondition();
		queryCondition.setMemberId(memberId);
		queryCondition.setStoreCode(storeCode);
		List<StoreMemberAddress> results = storeMemberAddressDao.findByQueryCondition(queryCondition);
		if(results!=null && results.size() > 0){
			return results.get(0);
		}
		
		return null;
	}

	public List<StoreMemberAddress> findByQueryCondition(
			StoreMemberAddressQueryCondition queryCondition) {
		
		return storeMemberAddressDao.findByQueryCondition(queryCondition);
		
	}



}
