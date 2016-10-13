package com.fushionbaby.act.activity.service;


import java.util.List;

import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author cyla
 * 
 */
public interface ActCardTypeService <T extends ActCardType>extends BaseService<T>{
	
	void add(ActCardType actCardType);

	void deleteById(Long id);

	void update(ActCardType actCardType);

	BasePagination getListPage(BasePagination page);
	
	List<ActCardType> findAll();

	/***
	 * 通过卡的类型id得到，该卡类型的最大使用次数（限制次数）
	 * 
	 * @param cardTypeId
	 * @return
	 */
	Integer findLimitCountById(Long cardTypeId);
	/***
	 * 通过该代金券的类型编码查询
	 * @param code
	 * @return
	 */
	ActCardType findByCode(String code);

}
