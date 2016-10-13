package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuLabelDao;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.service.SkuLabelService;

/**
 * 
 * @author King
 * 
 */
@Service
public class SkuLabelServiceImpl implements SkuLabelService<SkuLabel> {

	@Autowired
	private SkuLabelDao labelDao;

	public void add(SkuLabel maLabel) throws DataAccessException {
		labelDao.add(maLabel);
	}

	public void deleteById(Long id) throws DataAccessException {
		labelDao.deleteById(id);
	}

	public void update(SkuLabel maLabel) throws DataAccessException {
		labelDao.update(maLabel);
	}

	public SkuLabel findById(Long id) throws DataAccessException {
		return labelDao.findById(id);
	}

	public List<SkuLabel> findAll() throws DataAccessException {
		return labelDao.findAll();
	}

	/** 分页 */
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = labelDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuLabel> list = labelDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuLabel>());
		}
		return page;
	}
/*
 * (non-Javadoc)
 * @see com.fushionbaby.sku.service.SkuLabelService#getByCode(java.lang.String)
 */
	public SkuLabel getByCode(String code) {
		
		return labelDao.getByCode(code);
	}

	public void deleteByCode(String code) throws DataAccessException {
		labelDao.deleteByCode(code);
	}

	public List<SkuLabel> getLabelListByCode(String prefixCode) {
		return labelDao.getLabelListByCode(prefixCode);
	}

	public List<SkuLabel> getByType(String type) {
		return labelDao.getByType(type);
	}
}
