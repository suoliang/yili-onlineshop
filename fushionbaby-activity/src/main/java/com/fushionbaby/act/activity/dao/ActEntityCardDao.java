package com.fushionbaby.act.activity.dao; 

import java.util.List;
import java.util.Map;

import com.fushionbaby.act.activity.model.ActEntityCard;

public interface ActEntityCardDao { 


	void deleteById(Long id);

	void add(ActEntityCard object);

	ActEntityCard findById(Long id);

	List<ActEntityCard> findAll();

	void update(ActEntityCard object);
	
	List<ActEntityCard> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
	
	ActEntityCard findByCardNoPwd(Map<String, Object> map);

	void updatesurplusMoneyById(ActEntityCard object);
	
	ActEntityCard findByCardNo(String cardNo);
	
}
