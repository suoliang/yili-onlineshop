package com.fushionbaby.act.activity.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;

import com.fushionbaby.act.activity.model.ActEntityCardConfig;

public interface ActEntityCardConfigService<T extends ActEntityCardConfig> { 
	
	
	void deleteById(Long id);

	void add(ActEntityCardConfig object);

	ActEntityCardConfig findById(Long id);

	List<ActEntityCardConfig> findAll();

	void update(ActEntityCardConfig object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;
	
	ActEntityCardConfig findByName(String name);
	
	void updateIsDisabled(ActEntityCardConfig object);

}
