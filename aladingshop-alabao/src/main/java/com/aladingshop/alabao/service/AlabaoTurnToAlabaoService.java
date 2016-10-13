package com.aladingshop.alabao.service;

import java.util.List;

import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoTurnToAlabaoService<T extends AlabaoTurnToAlabao> {
	/**添加*/
	void add(AlabaoTurnToAlabao alabaoTurnToAlabao);
	
	List<AlabaoTurnToAlabao> findByBatchNum(String batchNum);
	
	AlabaoTurnToAlabao findBySerialNum(String serialNum);
	
	List<AlabaoTurnToAlabao> findByAccountCode(String accountCode);
	
	BasePagination getListPage(BasePagination page);
			

	void updateStatus(AlabaoTurnToAlabao alabaoTurnToAlabao);
	
}
