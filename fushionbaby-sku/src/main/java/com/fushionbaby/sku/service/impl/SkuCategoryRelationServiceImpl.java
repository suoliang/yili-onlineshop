/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.condition.sku.SkuCategoryRelationQueryCondition;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.sku.dao.SkuCategoryRelationDao;
import com.fushionbaby.sku.model.SkuCategoryRelation;
import com.fushionbaby.sku.service.SkuCategoryRelationService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuCategoryRelationServiceImpl implements
		SkuCategoryRelationService<SkuCategoryRelation> {

	@Autowired
	private SkuCategoryRelationDao maSkuCategoryRelationDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaSkuCategoryRelationService#add(com.fushionbaby
	 * .web.model.MaSkuCategoryRelation)
	 */
	public void add(SkuCategoryRelation maSkuCategoryRelation) {
		if(maSkuCategoryRelation.getShowOrder() == null){
			maSkuCategoryRelation.setShowOrder(SortTypeConstant.SHOW_ORDER);
		}
		maSkuCategoryRelationDao.add(maSkuCategoryRelation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaSkuCategoryRelationService#deleteById(java
	 * .lang.Integer)
	 */
	public void deleteById(Long id) {
		maSkuCategoryRelationDao.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.web.service.MaSkuCategoryRelationService#findById(java
	 * .lang.Integer)
	 */
	public SkuCategoryRelation findById(Long id) {
		return maSkuCategoryRelationDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.web.service.MaSkuCategoryRelationService#findAll()
	 */
	public List<SkuCategoryRelation> findAll() {
		return maSkuCategoryRelationDao.findAll();
	}

	public void update(SkuCategoryRelation entity) throws DataAccessException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.MaSkuCategoryRelationService#addOrUpdate(
	 * com.fushionbaby.sku.model.MaSkuCategoryRelation)
	 */
	public void addOrUpdate(SkuCategoryRelation skuCategoryRelation) {
		SkuCategoryRelation queryMaSkuCategoryRelation = maSkuCategoryRelationDao
				.findBySkuCode(skuCategoryRelation.getSkuCode());
		if (queryMaSkuCategoryRelation != null && skuCategoryRelation.getCategoryCode() != null) {
			skuCategoryRelation.setId(queryMaSkuCategoryRelation.getId());
			maSkuCategoryRelationDao.update(skuCategoryRelation);
		}
		if (queryMaSkuCategoryRelation == null && skuCategoryRelation.getCategoryCode() != null) {
			if(skuCategoryRelation.getShowOrder() == null){
				skuCategoryRelation.setShowOrder(SortTypeConstant.SHOW_ORDER);
			}
			maSkuCategoryRelationDao.add(skuCategoryRelation);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuCategoryRelationService#findSkuIdsByCategoryId
	 * (java.lang.Long)
	 */
	public List<String> findSkuCodesByCategoryCode(String categoryCode, Integer curPage, Integer limit) {

		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("categoryCode", categoryCode);
		mapParams.put("start", (curPage - 1) * limit);
		mapParams.put("limit", limit);
		List<String> skuCodes = new ArrayList<String>();

		List<SkuCategoryRelation> skuCategoryRelations = maSkuCategoryRelationDao
				.findByCategoryCode(mapParams);

		if (!CollectionUtils.isEmpty(skuCategoryRelations) && skuCategoryRelations.size() > 0) {
			for (SkuCategoryRelation rel : skuCategoryRelations) {
				skuCodes.add(rel.getSkuCode());
			}
		}
		return skuCodes;
	}

	public List<String> findSkuCodesByCategoryCode(String categoryCode) {

		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("categoryCode", categoryCode);
		mapParams.put("start", null);
		mapParams.put("limit", null);
		List<String> skuCodes = new ArrayList<String>();
		List<SkuCategoryRelation> skuCategoryRelations = maSkuCategoryRelationDao
				.findByCategoryCode(mapParams);
		if (!CollectionUtils.isEmpty(skuCategoryRelations) && skuCategoryRelations.size() > 0) {
			for (SkuCategoryRelation rel : skuCategoryRelations) {
				skuCodes.add(rel.getSkuCode());
			}
		}
		return skuCodes;
	}
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuCategoryRelationService#getTotalByCategoryId
	 * (java.lang.Long)
	 */
	public Integer getTotalByCategoryCode(String categoryCode) {

		return maSkuCategoryRelationDao.getTotalByCategoryCode(categoryCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.sku.service.SkuCategoryRelationService#
	 * findSkuIdsByQueryCondition
	 * (com.fushionbaby.sku.condition.SkuCategoryRelationQueryCondition)
	 */
	public List<String> findSkuCodesByQueryCondition(SkuCategoryRelationQueryCondition queryCondition) {
		List<SkuCategoryRelation> skuCategoryRelations = maSkuCategoryRelationDao
				.findByCondition(queryCondition);
		List<String> skuCodes = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(skuCategoryRelations) && skuCategoryRelations.size() > 0) {
			for (SkuCategoryRelation rel : skuCategoryRelations) {
				skuCodes.add(rel.getSkuCode());
			}
		}
		return skuCodes;
	}
	
	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,分类id
	 * @param skuIds
	 * @return
	 */
	public List<String> findCategoryCodesBySkuCodes(String skuCodes){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("skuCodes",skuCodes);
		return maSkuCategoryRelationDao.findCategoryCodesBySkuCodes(map);
	}

	public List<SkuCategoryRelation> queryByCondition(
			SkuCategoryRelationQueryCondition queryCondition) {
		
		return maSkuCategoryRelationDao.findByCondition(queryCondition);
	}




}
