package com.aladingshop.sku.cms.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.sku.cms.model.SkuExtraInfo;

/**
 * @author xpf
 * 
 */
public interface SkuExtraInfoDao {

	void add(SkuExtraInfo skuExtraInfo);
	
	SkuExtraInfo findBySkuCode(String skuCode);
	
	void deleteBySkuCode(String skuCode);
	
	void update(SkuExtraInfo skuExtraInfo);
	
	Integer getTotal(Map<String, Object> params);
	
	List<SkuExtraInfo> getPageList(Map<String, Object> params);
			
			

}
