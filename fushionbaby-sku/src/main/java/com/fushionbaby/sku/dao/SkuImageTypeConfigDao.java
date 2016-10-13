package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sku.model.SkuImageTypeConfig;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SkuImageTypeConfigDao {
	void add(SkuImageTypeConfig skuImgType);

	void deleteById(Long id);

	void update(SkuImageTypeConfig skuImgType);

	SkuImageTypeConfig findById(Long id);

	List<SkuImageTypeConfig> findAll();

	/**
	 * 分页 获得总数
	 * 
	 * @param searchParamsMap
	 * @return
	 */
	Integer getTotal(Map<String, Object> map);

	/**
	 * 分页
	 * 
	 * @param searchParamsMap
	 * @return
	 */
	List<SkuImageTypeConfig> getListPage(Map<String, Object> map);

	/***
	 * 通过后缀查找
	 * 
	 * @param string
	 * @return
	 */
	SkuImageTypeConfig findBySuffix(String string);
}
