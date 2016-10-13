package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuPromotionsInfo;

/**
 * 
 * @author King
 * 
 */
public interface SkuPromotionsInfoDao {

	void add(SkuPromotionsInfo skuPromotionsInfo);

	void deleteByPmid(Long pmid);
	
	void deleteByPromotionsCode(String promotionsCode);

	void updateByPmid(SkuPromotionsInfo skuPromotionsInfo);
	
	void updateByPromotionsCode(SkuPromotionsInfo skuPromotionsInfo);

	SkuPromotionsInfo findByPmid(Long pmid);
	
	SkuPromotionsInfo findByPromotionsCode(String promotionsCode);
	
	SkuPromotionsInfo findByPromotionsName(String promotionsName);

	/**
	 * 分页查询
	 * @author suoliang
	 */
	List<SkuPromotionsInfo> getList(Map<String, Object> map);

	/**
	 * 分页查询的总记录数
	 * 
	 * @param map
	 * @return 总记录数
	 */
	Integer getTotal(Map<String, Object> map);


}