package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuPriceDao;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuPriceService;

@Service
public class SkuPriceServiceImpl implements SkuPriceService<SkuPrice>{

	@Autowired
	private SkuPriceDao skuPriceDao;
	
	public void add(SkuPrice skuPrice) {
		skuPriceDao.add(skuPrice);
	}

	public SkuPrice findBySkuCode(String skuCode) {
		return skuPriceDao.findBySkuCode(skuCode);
	}

	public List<SkuPrice> findAll() {
		return skuPriceDao.findAll();
	}

	public void deleteById(Long id){
		skuPriceDao.deleteById(id);
	}
	
	public void deleteBySkuCode(String skuCode){
		skuPriceDao.deleteBySkuCode(skuCode);
	}
	
	public void update(SkuPrice skuPrice){
		skuPriceDao.update(skuPrice);
	}

	public void addOrUpdate(SkuPrice skuPrice) {
		if(skuPrice.getId()==null){
			this.add(skuPrice);
			return;
		}
		this.update(skuPrice);
	}
	
}
