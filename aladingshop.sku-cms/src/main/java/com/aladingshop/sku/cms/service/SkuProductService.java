package com.aladingshop.sku.cms.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.sku.cms.model.SkuProduct;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author glc
 * 
 */
public interface SkuProductService<T extends SkuProduct> extends BaseService<T> {

	BasePagination getListPage(BasePagination page) throws DataAccessException;

	/**
	 * 通过产品编号查询产品
	 * 
	 * @param code
	 * @return
	 */
	SkuProduct findByCode(String code);

	/**
	 * 通过产品号查询产品是否存在
	 * 
	 * @param code
	 * @return
	 */
	SkuProduct existByCode(String code);
	
	
	/**
	 * 查询当天操作过的索引产品
	 * @return
	 */
	List<SkuProduct> queryBySameDayOperateing();
}
