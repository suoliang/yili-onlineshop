package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuExtraInfoDao;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.fushionbaby.common.util.BasePagination;

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

	public BasePagination getPageList(BasePagination page) {
		Integer total = skuExtraInfoDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuExtraInfo> list = skuExtraInfoDao.getPageList(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuExtraInfo>());
		}
		return page;
	}
}
