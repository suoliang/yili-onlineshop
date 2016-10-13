package com.aladingshop.alabao.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.common.util.BasePagination;
import com.aladingshop.alabao.model.AlabaoBankConfig;

public interface AlabaoBankConfigService<T extends AlabaoBankConfig> { 
	
	
	void deleteById(Long id);

	void add(AlabaoBankConfig object);

	AlabaoBankConfig findById(Long id);

	List<AlabaoBankConfig> findAll();

	void update(AlabaoBankConfig object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	AlabaoBankConfig findByBankCode(String bankCode);
	
	AlabaoBankConfig findByBankName(String bankName);
}
