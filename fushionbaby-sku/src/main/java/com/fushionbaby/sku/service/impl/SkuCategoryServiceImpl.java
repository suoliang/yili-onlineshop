package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.sku.dao.SkuCategoryDao;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.service.SkuCategoryService;

/**
 * 
 * @author King
 * 
 */
@Service
public class SkuCategoryServiceImpl implements SkuCategoryService<SkuCategory> {

	@Autowired
	private SkuCategoryDao categoryDao;

	public void add(SkuCategory maCategory) throws DataAccessException {
		categoryDao.add(maCategory);
	}

	public void deleteById(Long id) throws DataAccessException {
		categoryDao.deleteById(id);
	}

	public void update(SkuCategory maCategory) throws DataAccessException {
		categoryDao.update(maCategory);
	}

	public SkuCategory findById(Long id) throws DataAccessException {
		return categoryDao.findById(id);
	}

	public List<SkuCategory> findAllCategory(String storeCode) throws DataAccessException {
		return categoryDao.findAllCategory(storeCode);
	}


	public SkuCategory getCategoryByCode(String storeCode,String code) {
		SkuCategoryQueryCondition queryCondition = new  SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCode(code);
		List<SkuCategory> categoryList = categoryDao.findByCondition(queryCondition);
		if(categoryList==null || categoryList.size()==0){
			return null;
		}
		return categoryList.get(0);
	}

	public List<SkuCategory> findCategoryByGrandcategoryCode(String storeCode,String grandcategoryCode) {
		SkuCategoryQueryCondition queryCondition = new  SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setGrandCode(grandcategoryCode);
		return categoryDao.findByCondition(queryCondition);
	}

	public List<SkuCategory> getCategoryByLevel(String storeCode,Integer level) {
		
		SkuCategoryQueryCondition queryCondition = new  SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCategoryLevel(level);
		
		return categoryDao.findByCondition(queryCondition);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuCategoryService#getLowestChildCategorysByCode(java.lang.String)
	 */
	public List<String> getLowestChildCategorysByCode(String storeCode,String categoryCode) throws Exception  {

		List<String> categorys = new ArrayList<String>();
		if (StringUtils.isBlank(categoryCode)) {
			return categorys;
		}
		SkuCategory category = this.getCategoryByCode(storeCode,categoryCode);
		if (category == null) {
			throw new Exception("查询商品分类时出现异常，返回结果是：null");
		}

		if (category.getCategoryLevel() == 3) {
			categorys.add(category.getCode());
		} else if (category.getCategoryLevel() == 2) {
			List<SkuCategory> categoryList = this.findCategoryByGrandcategoryCode(storeCode,categoryCode);
			for (SkuCategory skuCategory : categoryList) {
				categorys.add(skuCategory.getCode());
			}
		} else if (category.getCategoryLevel() == 1) {
			List<SkuCategory> categoryList = this.findCategoryByGrandcategoryCode(storeCode,categoryCode);
			for (SkuCategory skuCategory : categoryList) {
				List<SkuCategory> childCategoryList = this.findCategoryByGrandcategoryCode(storeCode,skuCategory
						.getCode());
				for (SkuCategory childCategory : childCategoryList) {
					categorys.add(childCategory.getCode());
				}
			}
		}
		if (categorys.size() < 1) {
			categorys.add(category.getCode());
		}

		return categorys;
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuCategoryService#getIdenticalCategory(java.lang.String)
	 */
	public List<SkuCategory> getIdenticalCategory(String storeCode,String categoryCode) {
		
		SkuCategoryQueryCondition queryCondition = new  SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCode(categoryCode);
		
		List<SkuCategory> skuCategoryList = categoryDao.findByCondition(queryCondition);
		if(skuCategoryList == null || skuCategoryList.size() == 0 || StringUtils.isBlank(skuCategoryList.get(0).getGrandcategoryCode())){
			return new ArrayList<SkuCategory>();
		}
		List<SkuCategory> categoryList = this.findCategoryByGrandcategoryCode(skuCategoryList.get(0).getGrandcategoryCode(),storeCode) ;
		
		return categoryList;
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuCategoryService#getParentCategory(java.lang.String)
	 */
	public SkuCategory getParentCategory(String storeCode,String categoryCode) {
		
		
		SkuCategoryQueryCondition queryCondition = new  SkuCategoryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCode(categoryCode);
		List<SkuCategory> skuCategoryList = categoryDao.findByCondition(queryCondition);
		
		if(skuCategoryList == null || skuCategoryList.size()==0){
			return new SkuCategory();
		}
		SkuCategory curCategory = skuCategoryList.get(0);
		Integer level = curCategory.getCategoryLevel();
		if(level == 1){
			return curCategory;
		}else if(level == 2){
			return this.getCategoryByCode(curCategory.getGrandcategoryCode(),storeCode);
		}
		
		SkuCategoryQueryCondition queryConditionByGrand = new  SkuCategoryQueryCondition();
		queryConditionByGrand.setGrandCode(curCategory.getGrandcategoryCode());
		queryConditionByGrand.setStoreCode(storeCode);
		String parentCode = categoryDao.findByCondition(queryConditionByGrand).get(0).getGrandcategoryCode();
		
		SkuCategoryQueryCondition queryConditionByCode = new  SkuCategoryQueryCondition();
		queryConditionByCode.setCode(parentCode);
		queryConditionByCode.setStoreCode(storeCode);
		
		List<SkuCategory> skuCategorys = categoryDao.findByCondition(queryConditionByCode);
		
		return skuCategorys==null ? null : skuCategorys.get(0);
		
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuCategoryService#findByCondition(com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition)
	 */
	public List<SkuCategory> findByCondition(
			SkuCategoryQueryCondition queryCondition) {
		
		return categoryDao.findByCondition(queryCondition);
	}
	

}