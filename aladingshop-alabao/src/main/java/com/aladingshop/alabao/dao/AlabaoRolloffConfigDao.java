package com.aladingshop.alabao.dao; 

import java.util.List;
import java.util.Map;

import com.aladingshop.alabao.model.AlabaoRolloffConfig;

public interface AlabaoRolloffConfigDao { 


	void deleteById(Long id);

	void add(AlabaoRolloffConfig object);

	AlabaoRolloffConfig findById(Long id);

	List<AlabaoRolloffConfig> findAll();

	void update(AlabaoRolloffConfig object);
	
	List<AlabaoRolloffConfig> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	AlabaoRolloffConfig findByRollOffCode(String rollOffCode);

}
