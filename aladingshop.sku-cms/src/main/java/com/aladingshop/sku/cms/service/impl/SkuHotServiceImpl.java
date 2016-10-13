package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuHotDao;
import com.aladingshop.sku.cms.model.SkuHot;
import com.aladingshop.sku.cms.service.SkuHotService;
import com.fushionbaby.common.util.BasePagination;
/***
 * 
 * @author xupeijun
 *
 */
@Service
public class SkuHotServiceImpl implements SkuHotService<SkuHot> {

	@Autowired
	private SkuHotDao skuHotDao;

	public void add(SkuHot skuHot) {
		skuHotDao.add(skuHot);
	}

	public void deleteById(Long id) {
		skuHotDao.deleteById(id);
	}

	public SkuHot findById(Long id) {
		return skuHotDao.findById(id);
	}

	public List<SkuHot> findAll() {
		return skuHotDao.findAll();
	}

	public void update(SkuHot skuHot) throws DataAccessException {
		this.skuHotDao.update(skuHot);
	}

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {

		Integer total = this.skuHotDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuHot> list = this.skuHotDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuHot>());
		}
		return page;
	}

	public void updateCount(Long id, Integer count) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("count", String.valueOf(count));
		map.put("id", String.valueOf(id));
		this.skuHotDao.updateCount(map);
	}

	/**
	 * 得到所有的热销id集合
	 * 
	 * */
	public String findAllCodes() {
		List<SkuHot> skuHotList = this.skuHotDao.findAll();
		StringBuffer codes = new StringBuffer();
		for (int i = 0; i < skuHotList.size(); i++) {
			codes.append(skuHotList.get(i).getSkuCode());
			if (i != skuHotList.size() - 1) {
				codes.append("','");
			}
		}
		return codes.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuHotService#findByTop(java.lang.Integer)
	 */
	public List<SkuHot> findByTop(Integer size) {

		return skuHotDao.getHotTop(size);

	}



}
