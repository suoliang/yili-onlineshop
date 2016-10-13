package com.aladingshop.alabao.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;
import com.aladingshop.alabao.model.AlabaoRolloffConfig;

public interface AlabaoRolloffConfigService<T extends AlabaoRolloffConfig> { 
	
	
	void deleteById(Long id);

	void add(AlabaoRolloffConfig object);

	AlabaoRolloffConfig findById(Long id);

	List<AlabaoRolloffConfig> findAll();

	void update(AlabaoRolloffConfig object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	AlabaoRolloffConfig findByRollOffCode(String rollOffCode);
}
