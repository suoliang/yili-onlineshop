package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuProduct;

/**
 * @author glc
 * 
 */
public interface SkuProductService<T extends SkuProduct> {

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
	 * 查询出当前时间操作的产品
	 * @return
	 */
	List<SkuProduct> queryBySameDayOperateing();
}
