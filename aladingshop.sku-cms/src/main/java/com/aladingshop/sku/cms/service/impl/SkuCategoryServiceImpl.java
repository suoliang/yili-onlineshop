package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuCategoryDao;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.fushionbaby.common.condition.sku.SkuCategoryQueryCondition;
import com.fushionbaby.common.util.BasePagination;

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


	public BasePagination getListPage(BasePagination pageParams)
			throws DataAccessException {

		Integer total = categoryDao.getTotal(pageParams.getSearchParamsMap());
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			List<SkuCategory> list = categoryDao.getListPage(pageParams
					.getSearchParamsMap());
			pageParams.setResult(list);
		} else {
			pageParams.setResult(new ArrayList<SkuCategory>());
		}
		return pageParams;
	}


	public List<SkuCategory> findCategoryByGrandcategoryCode(String grandcategoryCode,String storeCode) {
		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setGrandCode(grandcategoryCode);
		queryCondition.setStoreCode(storeCode);
		return categoryDao.queryByCondition(queryCondition);
	}

	public List<SkuCategory> getCategoryByLevel(Integer level,String storeCode) {
		
		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setCategoryLevel(level);
		queryCondition.setStoreCode(storeCode);
		return categoryDao.queryByCondition(queryCondition);
	}

	public SkuCategory findByCode(String code,String storeCode) {
		SkuCategoryQueryCondition queryCondition = new SkuCategoryQueryCondition();
		queryCondition.setCode(code);
		queryCondition.setStoreCode(storeCode);
		
		List<SkuCategory> categoryList = categoryDao.queryByCondition(queryCondition);
		if(categoryList==null || categoryList.size()==0){
			return new SkuCategory();
		}
		
		return categoryList.get(0);
	}

	public List<SkuCategory> queryByCondition(
			SkuCategoryQueryCondition queryCondition) {
		return categoryDao.queryByCondition(queryCondition);
	}

	public Integer queryTotalByCondition(
			SkuCategoryQueryCondition queryCondition) {
		
		Map<String, Object> searchParamsMap = new HashMap<String, Object>();
		searchParamsMap.put("categoryLevel", queryCondition.getCategoryLevel());
		searchParamsMap.put("code", queryCondition.getCode());
		searchParamsMap.put("storeCode", queryCondition.getStoreCode());
		searchParamsMap.put("isShow", queryCondition.getIsShow());
		searchParamsMap.put("grandcategoryCode", queryCondition.getGrandCode());
		searchParamsMap.put("name", queryCondition.getName());
		
		Integer total = categoryDao.getTotal(searchParamsMap);
		
		return total;
	}

	public String findMaxCategoryCode(SkuCategoryQueryCondition queryCondition) {
		
		String maxCode = categoryDao.findMaxCategoryCode(queryCondition);
		if(StringUtils.isBlank(maxCode)){
			return null;
		}
		
		
		return this.getMaxCode(maxCode, queryCondition.getStoreCode());
	}
	
	private String getMaxCode(String maxCode,String storeCode){
		
		
		if(NumberUtils.isNumber(maxCode)){
			return String.valueOf(Long.valueOf(maxCode) + 1);
		}
		
		maxCode = maxCode + 0;
		SkuCategory s = this.findByCode(maxCode, storeCode);
		if(s!=null && StringUtils.isNotBlank(s.getCode())){
			maxCode = s.getCode();
			this.getMaxCode(maxCode, storeCode);
		}
		return maxCode;
		
	}

	public List<String> getLowestChildCategorysByCode(String storeCode,String categoryCode) throws Exception  {

		List<String> categorys = new ArrayList<String>();
		if (StringUtils.isBlank(categoryCode)) {
			return categorys;
		}
		SkuCategory category = this.findByCode(categoryCode, storeCode);
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


}