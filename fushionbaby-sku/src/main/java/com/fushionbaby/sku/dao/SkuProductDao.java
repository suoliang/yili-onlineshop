package com.fushionbaby.sku.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sku.model.SkuProduct;

/**
 * @author glc
 * 
 */
public interface SkuProductDao {

	public SkuProduct findById(Long id);

	public List<SkuProduct> queryBySameDayOperateing() ;

	public SkuProduct findByCode(@Param("code") String code);

	public SkuProduct existByCode(@Param("code") String code);
}
