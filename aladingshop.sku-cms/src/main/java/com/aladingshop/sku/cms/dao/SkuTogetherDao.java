/**
 * 
 */
package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuTogether;

/**
 * @author mengshaobo
 * 
 */
public interface SkuTogetherDao {
	
	Long add(SkuTogether skuTogether);

	void updateBySkuCode(SkuTogether skuTogether);
	
	void updateById(SkuTogether skuTogether);

	void deleteById(Long id);
	/**
	 * 分页查询
	 * 
	 * @author suoliang
	 */
	List<SkuTogether> getListPage(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);

	SkuTogether findById(Long id);
	
	List<SkuTogether> findBySkuCodeAndDistrict(@Param(value="skuCode")String skuCode,@Param(value="district")String district);
}
