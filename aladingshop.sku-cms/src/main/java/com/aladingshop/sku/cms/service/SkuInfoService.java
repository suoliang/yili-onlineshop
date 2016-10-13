package com.aladingshop.sku.cms.service;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.util.BasePagination;

public interface SkuInfoService {
	/**
	 * 添加商品
	 * 
	 * @param skuEntry
	 * @return 添加数量
	 */
	Long insert(Sku skuEntry);

	/**
	 * 通过商品编号删除
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 修改商品信息
	 * 
	 * @param skuEntry
	 */
	void update(Sku skuEntry);

	/**
	 * 通过商品Id查询
	 * 
	 * @param id
	 * @return
	 */
	Sku findById(Long id);

	/**
	 * 通过唯一编号获取商品信息
	 * 
	 * @param uniqueCode
	 *            唯一编号
	 * @return
	 */
	Sku queryByUniqueCode(String uniqueCode);

	/**
	 * 通过商品编号查询商品
	 * 
	 * @param skuCode
	 *            商品编号
	 * @return 该商品
	 */
	Sku queryBySkuNo(String skuNo,String storeCode);
	
	/**
	 * 商品编号查询
	 * @param skuNo
	 * @return
	 */
	Sku queryBySkuNo(String skuNo);
	/**
	 * 通过商品条形码查询商品
	 * @param barCode 条形码
	 * @return
	 */
	Sku queryByBarCode(String barCode,String storeCode);

	
	Sku queryByBarCode(String barCode);
	/**
	 * 通过产品编号查询商品
	 * 
	 * @param productCode
	 *            产品编号
	 * @return
	 */
	List<Sku> queryByProductCode(String productCode);

	/**
	 * 通过品牌编号查询商品集合
	 * @param brandCode 品牌编号
	 * @return
	 */
	List<Sku> queryByBrandCode(String brandCode);
	/**
	 * 添加或修改商品
	 * @param skuEntry
	 */
	void addOrUpdate(Sku skuEntry);

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	BasePagination getPageList(BasePagination page);

	
	/**
	 * 条件查询商品集合
	 * @param queryCondition
	 * @return
	 */
	List<Sku> queryByCondition(SkuEntryQueryCondition queryCondition);

	/**
	 * 查询所有的商品信息
	 * @return
	 */
	List<Sku> findAll();
	

	List<Sku> findExcelAll(Map<String , Object> map);
	
	List<Sku> findSkusByLikeName(String name);

	Sku queryByStroeCodeAndSkuNo(String storeCode,String skuNo);

	List<SkuCategory> queryByStroeCode(String code);
}
