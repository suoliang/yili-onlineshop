package com.aladingshop.alabao.dao; 

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoBankConfig;

public interface AlabaoBankConfigDao { 


	void deleteById(Long id);

	void add(AlabaoBankConfig object);

	AlabaoBankConfig findById(Long id);

	List<AlabaoBankConfig> findAll();

	void update(AlabaoBankConfig object);
	
	List<AlabaoBankConfig> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	AlabaoBankConfig findByBankCode(String bankCode);

	AlabaoBankConfig findByBankName(String bankName);

}
