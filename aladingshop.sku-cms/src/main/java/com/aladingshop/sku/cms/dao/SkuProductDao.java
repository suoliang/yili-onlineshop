package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuProduct;

/**
 * @author glc
 * 
 */
public interface SkuProductDao {

	public void add(SkuProduct model);

	public void deleteById(Long id);

	public void update(SkuProduct model);

	public SkuProduct findById(Long id);

	public List<SkuProduct> findAll();

	public List<SkuProduct> getListPage(Map<String, Object> map);

	public Integer getTotal(Map<String, Object> map);

	public SkuProduct findByCode(@Param("code") String code);

	public SkuProduct existByCode(@Param("code") String code);
	
	List<SkuProduct>  queryBySameDayOperateing();
}
