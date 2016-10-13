package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.aladingshop.sku.cms.model.SkuPromotionsSku;

/**
 * 
 * @author King
 * 
 */
public interface SkuPromotionsSkuDao {

	void add(SkuPromotionsSku skuPromotionsSku);

	void deleteById(Long id);
	
	void deleteByPmCodeAndSkuCode(@Param("pmCode")String pmCode,@Param("skuCode")String skuCode);
	
	void deleteByPmCode(String pmCode);
	
	void updateById(SkuPromotionsSku skuPromotionsSku);
	
	void updateByPmCodeAndSkuCode(SkuPromotionsSku skuPromotionsSku);

	SkuPromotionsSku findById(Long pmid);
	
	SkuPromotionsSku findByPmCodeAndSkuCode(@Param("pmCode")String pmCode,@Param("skuCode")String skuCode);
	
	List<String> findSkuCodesByPmCode(String pmCode);
	/**
	 * 分页查询
	 * @author suoliang
	 */
	List<SkuPromotionsSku> getList(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);


}