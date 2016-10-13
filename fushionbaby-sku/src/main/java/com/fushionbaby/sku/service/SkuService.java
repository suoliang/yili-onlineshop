/**
 * 
 */
package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.sku.dto.SkuSearchDto;
import com.fushionbaby.sku.model.Sku;

/**
 * @author mengshaobo
 * 
 */
public interface SkuService {

	/**
	 * 条件查询商品列表
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @return
	 */
	List<Sku> queryByCondition(SkuEntryQueryCondition queryCondition);

	/**
	 * 条件查询商品列表数量
	 * 
	 * @param queryCondition
	 * @return
	 */
	Integer queryTotalByCondition(SkuEntryQueryCondition queryCondition);


	/**
	 * 通过商品编号查询商品信息
	 * 
	 * @param skuCode
	 *            商品编号
	 * @return
	 */
	Sku queryBySkuCode(String skuCode);
	
	/***
	 * 通过产品编号查询商品列表
	 * @param productCode 产品编号
	 * @return
	 */
	List<Sku> queryByProductCode(String productCode);
	
	/**
	 * 通过标签关联的商品编号查询商品集合
	 * @param relUnCodes 
	 * @return
	 */
	List<Sku> querySkuListByLabelRel(List<String> relUnCodes,String storeCode);
	
	
	/**
	 * 查询当天操作过的商品
	 * @return
	 */
	List<SkuSearchDto> queryBySameDayOperateing();
	
	
	

}
