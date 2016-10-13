/**
 * 
 */
package com.aladingshop.card.service;

import java.util.List;

import com.aladingshop.card.model.MemberCardBack;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 退卡明细Service
 * @author 孙涛
 * @date 2015年10月10日下午2:40:49
 */
public interface MemberCardBackService<T extends MemberCardBack> extends BaseService<T> {
	List<MemberCardBack> findAll();
	/***
	 * 分页查询
	 * @param page
	 * @return
	 */
	BasePagination getListPage(BasePagination page);

	MemberCardBack findByCardNo(String cardNo);
}
