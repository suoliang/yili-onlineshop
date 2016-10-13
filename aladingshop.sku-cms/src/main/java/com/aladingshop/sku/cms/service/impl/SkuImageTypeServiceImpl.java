package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuImageTypeDao;
import com.aladingshop.sku.cms.model.SkuImageType;
import com.aladingshop.sku.cms.service.SkuImageTypeService;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月12日上午11:06:07
 */
@Service
public class SkuImageTypeServiceImpl implements SkuImageTypeService<SkuImageType> {

	@Autowired
	private SkuImageTypeDao imageTypeDao;
	public void add(SkuImageType entity) throws DataAccessException {
          this.imageTypeDao.add(entity);		
	}

	public void deleteById(Long id) throws DataAccessException {
		this.imageTypeDao.deleteById(id);
		
	}

	public void update(SkuImageType entity) throws DataAccessException {
		this.imageTypeDao.update(entity);
		
	}

	public SkuImageType findById(Long id) throws DataAccessException {
		return this.imageTypeDao.findById(id);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = this.imageTypeDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuImageType> list = this.imageTypeDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuImageType>());
		}
		return page;
	}

	public List<SkuImageType> findAll() {
		return this.imageTypeDao.findAll();
	}

	public SkuImageType findByCode(String code) {
		return this.imageTypeDao.findByCode(code);
	}

	public void updateStatus(Long id, String status) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		this.imageTypeDao.updateStatus(map);
	}

}
