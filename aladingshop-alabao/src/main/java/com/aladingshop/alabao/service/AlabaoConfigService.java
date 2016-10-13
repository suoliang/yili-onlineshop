package com.aladingshop.alabao.service;


import org.springframework.dao.DataAccessException;

import com.aladingshop.alabao.model.AlabaoConfig;
import com.fushionbaby.common.util.BasePagination;

public interface AlabaoConfigService <T extends AlabaoConfig>{
	
	public void add(AlabaoConfig alabaoConfig);
	
	public void deleteById(Long id);
	
	public void updateById(AlabaoConfig alabaoConfig);
	
	public AlabaoConfig findById(Long id);

	public AlabaoConfig findByRateCode(String rateCode);
	
	/**
	 *            分页
	 */
	BasePagination getListPage(BasePagination page) throws DataAccessException;
}
