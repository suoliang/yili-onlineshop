package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuBrand;
import com.fushionbaby.common.condition.sku.SkuBrandQueryCondition;

/**
 * 
 * @author King
 * 
 */
public interface SkuBrandDao {
	/**
	 * 
	 * @return 查询全部品牌
	 */
	List<SkuBrand> findAll();

	/**
	 * 
	 * @param maBrand
	 */
	void add(SkuBrand maBrand);

	/***
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 
	 * @param maBrand
	 */

	void update(SkuBrand maBrand);

	/**
	 * 
	 * @param id
	 * @return
	 */
	SkuBrand findById(Long id);

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return List<MaBrand>
	 */
	List<SkuBrand> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 前台app页面展示列表用到
	 * 
	 * @param map
	 * @return
	 */
	List<SkuBrand> findAllApp(Map<String, Object> map);

	/**
	 * 前台web页面展示列表详情
	 * 
	 * @return
	 */
	List<SkuBrand> findAllWeb(Map<String, Object> map);
	/**
	 * 按条件查询商品品牌列表
	 * @param queryCondition 查询条件
	 * @return
	 */
	List<SkuBrand> findByCondition(SkuBrandQueryCondition queryCondition);
	
	SkuBrand findByBrandCode(String brandCode);

}