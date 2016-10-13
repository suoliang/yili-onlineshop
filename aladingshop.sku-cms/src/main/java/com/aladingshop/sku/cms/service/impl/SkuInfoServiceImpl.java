package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuDao;
import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.util.BasePagination;

@Service
public class SkuInfoServiceImpl implements SkuInfoService {

	@Autowired
	private SkuDao skuInfoDao;

	public Long insert(Sku skuEntry) {
		return skuInfoDao.insert(skuEntry);
	}

	public void deleteById(Long id) {
		skuInfoDao.deleteById(id);
	}

	public void update(Sku skuEntry) {
		skuInfoDao.update(skuEntry);
	}

	public Sku findById(Long id) {
		return skuInfoDao.findById(id);
	}

	public Sku queryBySkuNo(String skuNo,String storeCode) {
		return skuInfoDao.queryBySkuNo(skuNo, storeCode);
	}

	public List<Sku> queryByProductCode(String productCode) {
		return skuInfoDao.queryByProductCode(productCode);
	}

	public void addOrUpdate(Sku skuEntry) {
		if (skuEntry.getId() == null) {
			this.insert(skuEntry);
			return;
		}
		this.update(skuEntry);

	}

	public BasePagination getPageList(BasePagination page) {
		Integer total = skuInfoDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			
			List<Sku> list = skuInfoDao.getPageList(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<Sku>());
		}
		return page;
	}

	public Sku queryByUniqueCode(String uniqueCode) {
		return skuInfoDao.queryByUniqueCode(uniqueCode);
	}


	public Sku queryByBarCode(String barCode,String storeCode) {
		return skuInfoDao.queryByBarCode(barCode,storeCode);
	}

	public List<Sku> queryByBrandCode(String brandCode) {
		return skuInfoDao.queryByBrandCode(brandCode);
	}

	public List<Sku> findAll() {
		return skuInfoDao.findAll();
	}

	public List<Sku> findExcelAll(Map<String, Object> map) {
		return skuInfoDao.findExcelAll(map);
	}

	public List<Sku> findSkusByLikeName(String name) {
		return skuInfoDao.findSkusByLikeName(name);
	}

	public Sku queryBySkuNo(String skuNo) {
		
		return skuInfoDao.queryBySkuNo(skuNo, null);
	}

	public Sku queryByBarCode(String barCode) {
		return skuInfoDao.queryByBarCode(barCode, null);
	}

	public Sku queryByStroeCodeAndSkuNo(String storeCode, String skuNo) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("storeCode", storeCode);
		map.put("skuNo", skuNo);
		
		return skuInfoDao.queryByStroeCodeAndSkuNo(map);
	}

	public List<Sku> queryByCondition(SkuEntryQueryCondition queryCondition) {
		
		return skuInfoDao.queryByCondition(queryCondition);
	}

	public List<SkuCategory> queryByStroeCode(String code) {
		return skuInfoDao.queryByStroeCode(code);
	}
}
