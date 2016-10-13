package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuHot;

/**
 * @author xupeijun
 * 
 */
public interface SkuHotDao {
	void add(SkuHot skuHot);

	void deleteById(Long id);

	SkuHot findById(Long id);

	List<SkuHot> findAll();

	void update(SkuHot skuHot);

	Integer getTotal(Map<String, Object> map);

	List<SkuHot> getListPage(Map<String, Object> map);


	List<SkuHot> getHotTop(Integer size);

	void updateCount(Map<String, String> map);
}
