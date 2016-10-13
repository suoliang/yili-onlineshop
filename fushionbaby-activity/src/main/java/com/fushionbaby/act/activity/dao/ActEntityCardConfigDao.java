package com.fushionbaby.act.activity.dao; 

import java.util.List;
import java.util.Map;

import com.fushionbaby.act.activity.model.ActEntityCardConfig;

public interface ActEntityCardConfigDao { 


	void deleteById(Long id);

	void add(ActEntityCardConfig object);

	ActEntityCardConfig findById(Long id);

	List<ActEntityCardConfig> findAll();

	void update(ActEntityCardConfig object);
	
	List<ActEntityCardConfig> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);

	ActEntityCardConfig findByName(String name);
	
	void updateIsDisabled(ActEntityCardConfig object);
}
