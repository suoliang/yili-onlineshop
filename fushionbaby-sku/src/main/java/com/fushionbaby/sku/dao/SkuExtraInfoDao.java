package com.fushionbaby.sku.dao;

import java.util.List;

import com.fushionbaby.sku.model.SkuExtraInfo;

/**
 * @author xpf
 * 
 */
public interface SkuExtraInfoDao {

	void add(SkuExtraInfo skuExtraInfo);
	
	SkuExtraInfo findBySkuCode(String skuCode);
	
	void deleteBySkuCode(String skuCode);
	
	void update(SkuExtraInfo skuExtraInfo);
	
	List<SkuExtraInfo> findByIsMemberSku(String isMemberSku);

}
