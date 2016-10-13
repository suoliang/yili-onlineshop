package com.fushionbaby.sku.service;

import java.util.List;

import com.fushionbaby.sku.model.SkuExtraInfo;

/**
 * @author xpf
 * 
 */
public interface SkuExtraInfoService<T extends SkuExtraInfo> {

	void add(SkuExtraInfo skuExtraInfo);
	
	SkuExtraInfo findBySkuCode(String skuCode);
	
	void deleteBySkuCode(String skuCode);
	
	void update(SkuExtraInfo skuExtraInfo);
	
	void addOrUpdate(SkuExtraInfo skuExtraInfo);
	
	List<SkuExtraInfo> findByIsMemberSku(String isMemberSku);
	

}


