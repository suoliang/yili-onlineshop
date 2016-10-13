package com.fushionbaby.sku.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sku.model.SkuImage;

/**
 * 
 * @author xupeijun
 * 
 */
public interface SkuImageDao {

	void add(SkuImage skuImg);

	void deleteById(Long id);

	void update(SkuImage skuImg);

	SkuImage findById(Long id);

	List<SkuImage> findAll();

	/**
	 * 通过每个商品的商品id得到 该商品的所有图片
	 * 
	 * @param skuId
	 * @return
	 */
	List<SkuImage> findBySkuCode(String skuCode);
	
	SkuImage findImageBySkuCode(
			@Param("skuCode") String skuCode, 
			@Param("imageType") String imageType);

	/**
	 * 通过商品编号和图片类型得到图片集合
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param imageTypeCode
	 *            图片类型
	 * @return
	 */
	List<SkuImage> findBySkuCodeByImageTypeCode(
			@Param("skuCode") String skuCode,
			@Param("imageTypeCode") String imageTypeCode);

	/***
	 * 分页
	 * 
	 * @param searchParamsMap
	 * @return
	 */
	Integer getTotal(Map<String, Object> searchParamsMap);

	/***
	 * 分页
	 * 
	 * @param searchParamsMap
	 * @return
	 */
	List<SkuImage> getListPage(Map<String, Object> searchParamsMap);
}
