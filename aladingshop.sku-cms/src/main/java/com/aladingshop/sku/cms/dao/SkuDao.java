package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;

public interface SkuDao {
	
	/**
	 * 添加商品 
	 * @param skuEntry
	 * @return 添加数量
	 */
	Long insert(Sku skuEntry);
	
	/** 
	 * 通过商品编号删除
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 修改商品信息
	 * @param skuEntry
	 */
	void update(Sku skuEntry);
	
	/**
	 * 通过商品Id查询
	 * @param id
	 * @return
	 */
	Sku findById(Long id);
	
	/**
	 * 通过商品编号查询商品
	 * 
	 * @param skuCode
	 *            商品编号
	 * @return 该商品
	 */
	Sku queryBySkuNo(@Param("skuNo")String skuNo,@Param("storeCode")String storeCode);
	
	/**
	 * 通过商品条形码查询商品
	 * @param barCode
	 * @return
	 */
	Sku queryByBarCode(@Param("barCode")String barCode,@Param("storeCode")String storeCode);
	/**
	 * 通过商品唯一编号查询商品
	 * 
	 * @param skuCode
	 *            商品编号
	 * @return 该商品
	 */
	Sku queryByUniqueCode(String uniqueCode);

	/**
	 * 通过产品编号查询商品
	 * 
	 * @param productId
	 *            产品编号
	 * @return
	 */
	List<Sku> queryByProductCode(String productCode);
	
	
	Integer getTotal(Map<String, Object> params);
	
	
	List<Sku> getPageList(Map<String, Object> params);
	
	
	List<Sku> queryBySameDayOperateing();
	
	
	/**
	 * 条件查询
	 * @param queryCondition
	 * @return
	 */
	List<Sku> queryByCondition(SkuEntryQueryCondition queryCondition);
	
	
	List<Sku> queryByBrandCode(String brandCode);

	/**
	 * 查询所有的商品信息
	 * @return
	 */
	List<Sku> findAll();
	

	List<Sku> findExcelAll(Map<String , Object> map);
	

	List<Sku> findSkusByLikeName(String name);

	Sku queryByStroeCodeAndSkuNo(Map<Object, Object> map);

	List<SkuCategory> queryByStroeCode(String code);
}
