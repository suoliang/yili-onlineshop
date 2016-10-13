package com.aladingshop.alabao.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoTurnToAlabao;

public interface AlabaoTurnToAlabaoDao {
	/**添加*/
	void add(AlabaoTurnToAlabao alabaoTurnToAlabao);
	
	
	List<AlabaoTurnToAlabao> findByBatchNum(String batchNum);
	
	
	List<AlabaoTurnToAlabao> getListPage(Map<String, Object> map);
	
	
	Integer getTotal(Map<String, Object> map);
	
	
	List<AlabaoTurnToAlabao> findByAccountCode(String accountCode);
	 
	 
	void updateStatus(AlabaoTurnToAlabao alabaoTurnToAlabao);
	 
	 
	 AlabaoTurnToAlabao findBySerialNum(String serialNum);
}
