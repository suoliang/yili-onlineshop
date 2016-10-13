/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuTogetherDao;
import com.aladingshop.sku.cms.model.SkuTogether;
import com.aladingshop.sku.cms.service.SkuTogetherService;
import com.fushionbaby.common.util.BasePagination;
//import com.fushionbaby.sku.model.SkuAttrValue;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuTogetherServiceImpl implements SkuTogetherService<SkuTogether> {
	@Autowired
	private SkuTogetherDao skuTogetherDao;

	public Long add(SkuTogether skuTogether){
		return this.skuTogetherDao.add(skuTogether);
	}

	public void updateBySkuCode(SkuTogether skuTogether) {
		this.skuTogetherDao.updateBySkuCode(skuTogether);
		
	}

	/** 分页 */
	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = skuTogetherDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuTogether> list = skuTogetherDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuTogether>());
		}
		return page;
	}

	public void updateById(SkuTogether skuTogether) {
		this.skuTogetherDao.updateById(skuTogether);
	}
	
	public void deleteById(Long id) {
		this.skuTogetherDao.deleteById(id);
	}


	public Integer getTotal(Map<String, Object> map) {
		return this.skuTogetherDao.getTotal(map);
	}

	public SkuTogether findById(Long id) {
		return this.skuTogetherDao.findById(id);
	}


	public List<SkuTogether> findBySkuCodeAndDistrict(String skuCode,String district) {
		return this.skuTogetherDao.findBySkuCodeAndDistrict(skuCode, district);
	}
	
	

}
