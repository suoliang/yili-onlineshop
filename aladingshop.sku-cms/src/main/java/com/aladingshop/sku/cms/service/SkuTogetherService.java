/**
 * 
 */
package com.aladingshop.sku.cms.service;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuTogether;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author mengshaobo
 * 
 */
public interface SkuTogetherService<T extends SkuTogether>{

	Long add(SkuTogether skuTogether);

	void updateBySkuCode(SkuTogether skuTogether);
	
	void updateById(SkuTogether skuTogether);
	
	void deleteById(Long id);

	/**
	 * 分页查询
	 * 
	 * @author suoliang
	 */
	BasePagination getListPage(BasePagination page);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);
	
	SkuTogether findById(Long id);
	
	List<SkuTogether> findBySkuCodeAndDistrict(String skuCode,String district) ;
 
}
