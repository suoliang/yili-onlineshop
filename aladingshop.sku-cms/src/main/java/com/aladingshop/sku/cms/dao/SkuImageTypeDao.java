package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuImageType;

/***
 * 
 * @description 类描述... 图片类型
 * @author 徐培峻
 * @date 2015年8月12日上午11:00:52
 */
public interface SkuImageTypeDao {

	/**
	 * 
	 * @return 查询全部图片类型
	 */
	List<SkuImageType> findAll();

	/**
	 * 
	 * @param skuImageType
	 */
	void add(SkuImageType skuImageType);

	/***
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 
	 * @param skuImageType
	 */

	void update(SkuImageType skuImageType);

	/**
	 * 
	 * @param id
	 * @return
	 */
	SkuImageType findById(Long id);

	/**
	 * 分页查询
	 * 
	 * @param map
	 * @return List<skuImageType>
	 */
	List<SkuImageType> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	
	SkuImageType findByCode(String code);

	/***
	 * 跟新状态
	 * @param map
	 */
	void updateStatus(Map<String, Object> map);

	
}
