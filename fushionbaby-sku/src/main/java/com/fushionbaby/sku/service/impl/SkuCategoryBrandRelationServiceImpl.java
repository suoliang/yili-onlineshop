/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuCategoryBrandRelationDao;
import com.fushionbaby.sku.model.SkuCategoryBrandRelation;
import com.fushionbaby.sku.service.SkuCategoryBrandRelationService;

/**
 * @author mengshaobo
 *
 */
@Service
public class SkuCategoryBrandRelationServiceImpl implements SkuCategoryBrandRelationService<SkuCategoryBrandRelation>{

	@Autowired
	private SkuCategoryBrandRelationDao skuCategoryBrandRelationDao;
	public void add(SkuCategoryBrandRelation skuCategoryBrandRelation) {
		skuCategoryBrandRelationDao.add(skuCategoryBrandRelation);
		
	}

	public void deleteById(Long id) {
		skuCategoryBrandRelationDao.deleteById(id);
		
	}

	public void deleteByCategoryAndBrandCode(String categoryCode,String brandCode) {
		SkuCategoryBrandRelation	categoryBrandRelation = skuCategoryBrandRelationDao.findByCategoryCodeAndBrandCode(categoryCode, brandCode);
		
		if(categoryBrandRelation==null){
			return ;
		}
		skuCategoryBrandRelationDao.deleteById(categoryBrandRelation.getId());
	}
	
	public void update(SkuCategoryBrandRelation skuCategoryBrandRelation) {
		skuCategoryBrandRelationDao.update(skuCategoryBrandRelation);
		
	}

	public List<SkuCategoryBrandRelation> findById(Long id) {
		
		return skuCategoryBrandRelationDao.findById(id);
	}


	public List<SkuCategoryBrandRelation> findByCategoryCode(String categoryCode) {
		
		return skuCategoryBrandRelationDao.findByCategoryCode(categoryCode);
	}

	public SkuCategoryBrandRelation findByCategoryCodeAndBrandCode(
			String categoryCode, String brandCode) {
		
		return skuCategoryBrandRelationDao.findByCategoryCodeAndBrandCode(categoryCode, brandCode);
	}

}
