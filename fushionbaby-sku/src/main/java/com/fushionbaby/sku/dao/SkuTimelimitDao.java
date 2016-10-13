/**
 * 
 */
package com.fushionbaby.sku.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.sku.model.SkuTimelimit;

/**
 * @author mengshaobo
 *
 */
public interface SkuTimelimitDao {
	
	void add(SkuTimelimit skuTimelimit);
	
	void update(SkuTimelimit skuTimelimit);
	
	void deleteById(Long id);
	
	SkuTimelimit getBySkuCode(String skuCode);
	
	
	List<SkuTimelimit> findByTop(@Param("size")Integer size,@Param("currentTime")Date currentTime);
	
	/**
	 * 通过商品状态查询商品集合
	 * @param status
	 * @return
	 */
	List<SkuTimelimit> findByStatus(String status);
	
	/**
	 * 查询全部
	 * @return
	 */
	List<SkuTimelimit> findAll();
	
	List<SkuTimelimit> getPageList(Map<String, Object> params);
	
	Integer getTotal(Map<String, Object> params);
}
