package com.fushionbaby.act.activity.service; 

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.fushionbaby.act.activity.dto.EntityCardReqDto;
import com.fushionbaby.act.activity.dto.EntityCardResDto;
import com.fushionbaby.act.activity.model.ActEntityCard;
import com.fushionbaby.common.util.BasePagination;

public interface ActEntityCardService<T extends ActEntityCard> { 
	
	
	void deleteById(Long id);

	void add(ActEntityCard object);

	ActEntityCard findById(Long id);

	List<ActEntityCard> findAll();

	void update(ActEntityCard object);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;

	ActEntityCard findByCardNoPwd(String cardNo,String pwd);
	
	void updatesurplusMoneyById(ActEntityCard object);
	
	ActEntityCard findByCardNo(String cardNo);

	/***
	 * 通过code 获得返回给app 端的数据
	 * @param entityCode
	 * @return
	 */
	EntityCardResDto getCardInfoByCode(String entityCode);
	/***
	 * 使用该实体卡  转入如意消费卡
	 * @param entityCode
	 * @param password
	 * @return
	 */
	EntityCardResDto useEntityCard(EntityCardReqDto reqDto);
}
