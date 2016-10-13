package com.aladingshop.alabao.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoTurnOutService<T extends AlabaoTurnOut> { 
	
	
	void deleteById(Long id);

	void add(AlabaoTurnOut object);

	AlabaoTurnOut findById(Long id);

	List<AlabaoTurnOut> findAll();

	void update(AlabaoTurnOut object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	void updateStatus(String turnOutStatus,Long id);

	AlabaoTurnOut findBySerialNum(String serialNum);
}
