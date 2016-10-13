/**
 * 
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.sku.model.SkuProductAttr;

/**
 * @author mengshaobo
 * 
 */
public interface SkuProductAttrService<T extends SkuProductAttr> extends BaseService<T> {

	/**
	 * 通过产品Id删除该产品下的属性
	 * 
	 * @param pid
	 */
	void delAttrByProductCode(String productCode);
	
	/**
	 * 通过产品编号查询产品属性
	 * @param productCode 产品编号
	 * @return
	 */
	List<SkuProductAttr> findByProductCode(String productCode);

}
