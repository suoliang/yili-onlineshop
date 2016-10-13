package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuExtraInfoDao;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.service.SkuExtraInfoService;

@Service
public class SkuExtraInfoServiceImpl implements SkuExtraInfoService<SkuExtraInfo>{

	@Autowired
	private SkuExtraInfoDao skuExtraInfoDao;
	
	public void add(SkuExtraInfo skuExtraInfo) {
		skuExtraInfoDao.add(skuExtraInfo);
	}

	public SkuExtraInfo findBySkuCode(String skuCode) {
		return skuExtraInfoDao.findBySkuCode(skuCode);
	}
	
	public void deleteBySkuCode(String skuCode){
		skuExtraInfoDao.deleteBySkuCode(skuCode);
	}
	
	public void update(SkuExtraInfo skuExtraInfo){
		skuExtraInfoDao.update(skuExtraInfo);
	}

	public void addOrUpdate(SkuExtraInfo skuExtraInfo) {
		if(skuExtraInfo.getId()==null){
			this.add(skuExtraInfo);
			return ;
		}
		this.update(skuExtraInfo);
		
	}

	public List<SkuExtraInfo> findByIsMemberSku(String isMemberSku) {
		return skuExtraInfoDao.findByIsMemberSku(isMemberSku);
	}
}
