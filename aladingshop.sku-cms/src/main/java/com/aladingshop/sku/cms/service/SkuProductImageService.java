package com.aladingshop.sku.cms.service;

import java.util.List;

import com.aladingshop.sku.cms.model.SkuProductImage;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author cyla
 * 
 */
public interface SkuProductImageService<T extends SkuProductImage> {
	/**
	 * 通过产品编号查询产品图片集合已用
	 * 
	 * @param skuProductCode
	 * @return
	 */
	List<SkuProductImage> findBySkuProductCode(String skuProductCode);

	/**
	 * 通过产品编号查询产品图片集合 cms端 全部
	 * 
	 * @param skuProductCode
	 * @return
	 */
	List<SkuProductImage> findAllBySkuProductCode(String skuProductCode);

	/**
	 * 通过编号查询该图片
	 * 
	 * @param id
	 * @return
	 */
	SkuProductImage findById(Long id);

	/**
	 * 添加产品图片
	 * 
	 * @param skuProductImage
	 */
	void add(SkuProductImage skuProductImage);

	/**
	 * 修改产品图片
	 * 
	 * @param skuProductImage
	 */
	void update(SkuProductImage skuProductImage);

	/**
	 * 通过productCode删除图片
	 * 
	 * @param id
	 */
	void deleteByProductCode(String productCode);

	/**
	 * 通过产品编号查询产品图片集合 cms端 实现分页
	 * 
	 * @param page
	 * @return
	 */
	BasePagination findBySkuProductCode(BasePagination page);

	/**
	 * 通过Id删除图片
	 * 
	 * @param id
	 */
	void deleteById(Long id);

	/**
	 * 通过产品编号和产品名称模糊查询产品图片集合 cms端 实现分页
	 * 
	 * @param page
	 * @return
	 */
	BasePagination findBySkuProductCodeAndProductName(BasePagination page);
}
