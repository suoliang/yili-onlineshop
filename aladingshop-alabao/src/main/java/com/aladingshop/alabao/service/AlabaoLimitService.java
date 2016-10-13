package com.aladingshop.alabao.service;

import com.aladingshop.alabao.model.AlabaoLimit;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoLimitService<T extends AlabaoLimit> {
	/**添加*/
	void add(AlabaoLimit alabaoLimit);
	/**通过如意宝账户查看对象*/
	AlabaoLimit findByAccount(String account);
	
	void update(AlabaoLimit alabaoLimit);
	
	BasePagination  getListPage(BasePagination page);
	
	void deleteById(Long id);
}
