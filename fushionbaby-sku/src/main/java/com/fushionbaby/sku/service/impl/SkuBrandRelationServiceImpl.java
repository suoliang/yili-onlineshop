/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.condition.sku.SkuBrandRelationQueryCondition;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuBrandRelationDao;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.service.SkuBrandRelationService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuBrandRelationServiceImpl implements SkuBrandRelationService<SkuBrandRelation> {

	@Autowired
	private SkuBrandRelationDao skuBrandRelationDao;

	public void add(SkuBrandRelation entity) throws DataAccessException {
		if(entity.getShowOrder()== null){
			entity.setShowOrder(SortTypeConstant.SHOW_ORDER);
		}
		skuBrandRelationDao.add(entity);

	}

	public void update(SkuBrandRelation entity) throws DataAccessException {
		skuBrandRelationDao.update(entity);

	}

	public void deleteById(Long id) throws DataAccessException {
		skuBrandRelationDao.deleteById(id);

	}

	public SkuBrandRelation findById(Long id) throws DataAccessException {
		return skuBrandRelationDao.findById(id);
	}

	public List<SkuBrandRelation> findAll() throws DataAccessException {
		return skuBrandRelationDao.findAll();
	}

	/*
	 * .(non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.MaSkuBrandRelationService#addOrUpdate(com
	 * .fushionbaby.sku.model.MaSkuBrandRelation)
	 */
	public void addOrUpdate(SkuBrandRelation skuBrandRelation) {
		SkuBrandRelation queryMaSkuBrandRelation = skuBrandRelationDao
				.findBySkuCode(skuBrandRelation.getSkuCode());

		if (queryMaSkuBrandRelation != null && skuBrandRelation.getBrandCode() != null) {
			skuBrandRelation.setId(queryMaSkuBrandRelation.getId());
			skuBrandRelationDao.update(skuBrandRelation);
		} else if (queryMaSkuBrandRelation == null && skuBrandRelation.getBrandCode() != null) {
			if(skuBrandRelation.getShowOrder() == null){
				skuBrandRelation.setShowOrder(SortTypeConstant.SHOW_ORDER);
			}
			skuBrandRelationDao.add(skuBrandRelation);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuBrandRelationService#findByBrandId(java
	 * .lang.Long)
	 */
	public List<String> findSkuCodesByBrandCode(String brandCode) {
		List<SkuBrandRelation> relationList = skuBrandRelationDao.findByBrandCode(brandCode);
		List<String> skuCodeList = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(relationList) && relationList.size() > 0) {
			for (SkuBrandRelation rel : relationList) {
				skuCodeList.add(rel.getSkuCode());
			}
		}
		return skuCodeList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.sku.service.SkuBrandRelationService#getPageList(com.
	 * fushionbaby.common.util.BasePagination)
	 */
	public BasePagination getPageList(BasePagination pageParams) {

		Integer total = skuBrandRelationDao.getTotal(pageParams.getSearchParamsMap());
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			List<SkuBrandRelation> list = skuBrandRelationDao.getPageList(pageParams
					.getSearchParamsMap());
			pageParams.setResult(list);
		} else {
			pageParams.setResult(new ArrayList<SkuBrandRelation>());
		}

		return pageParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuBrandRelationService#queryByCondition(
	 * com.fushionbaby.sku.condition.SkuBrandRelationQueryCondition)
	 */
	public List<String> queryByCondition(SkuBrandRelationQueryCondition queryCondition) {
		List<SkuBrandRelation> relList = skuBrandRelationDao.queryByCondition(queryCondition);
		List<String> skuCodes = new ArrayList<String>();
		for (SkuBrandRelation rel : relList) {
			skuCodes.add(rel.getSkuCode());
		}
		return skuCodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuBrandRelationService#queryTotalByCondition
	 * (com.fushionbaby.sku.condition.SkuBrandRelationQueryCondition)
	 */
	public Integer queryTotalByCondition(SkuBrandRelationQueryCondition queryCondition) {
		return skuBrandRelationDao.queryTotalByCondition(queryCondition);
	}

	/**
	 * 条件查询,根据多个skuCode,in查询、得到所有skuCode对应的,品牌id
	 * @param skuIds
	 * @return
	 */
	public List<String> findBrandCodesBySkuCodes(String skuCodes){
		return skuBrandRelationDao.findBrandCodesBySkuCodes(skuCodes);
	}

	public List<SkuBrandRelation> queryListByCondtion(SkuBrandRelationQueryCondition queryCondition) {
		return skuBrandRelationDao.queryByCondition(queryCondition);
	}
}
