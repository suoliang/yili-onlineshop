package com.aladingshop.alabao.dao; 

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoTurnOut;

public interface AlabaoTurnOutDao { 


	void deleteById(Long id);

	void add(AlabaoTurnOut object);

	AlabaoTurnOut findById(Long id);

	List<AlabaoTurnOut> findAll();

	void update(AlabaoTurnOut object);
	
	List<AlabaoTurnOut> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	void updateStatus(Map<String , Object> map);

	AlabaoTurnOut findBySerialNum(String serialNum);
}
