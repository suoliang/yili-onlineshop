/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuProductAttr;

/**
 * @author mengshaobo
 * 
 */
public interface SkuProductAttrDao {
	void add(SkuProductAttr skuProductAttrValue);

	void deleteById(Long id);

	void update(SkuProductAttr skuProductAttrValue);

	SkuProductAttr findById(Long id);

	List<SkuProductAttr> findAll();

	/**
	 * 根据产品查询商品属性值
	 * 
	 * @param pid
	 * @return
	 */
	List<SkuProductAttr> findByProductCode(String productCode);

	/**
	 * 通过产品Id删除产品属性信息
	 * 
	 * @param pid
	 */
	void delProductAttrByProductCode(String productCode);


}
