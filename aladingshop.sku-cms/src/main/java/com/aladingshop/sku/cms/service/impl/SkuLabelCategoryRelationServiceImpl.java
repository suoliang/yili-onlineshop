package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dao.SkuLabelCategoryRelationDao;
import com.aladingshop.sku.cms.dao.SkuLabelDao;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuLabel;
import com.aladingshop.sku.cms.model.SkuLabelCategoryRelation;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuLabelCategoryRelationService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.CheckObjectIsNull;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午7:22:44
 */
@Service
public class SkuLabelCategoryRelationServiceImpl implements SkuLabelCategoryRelationService<SkuLabelCategoryRelation> {
	@Autowired
	private SkuLabelCategoryRelationDao labelCategoryConfigDao;
	@Autowired
	private SkuLabelDao skuLabelDao;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	
	public void add(SkuLabelCategoryRelation labelCategoryConfig) {
		labelCategoryConfigDao.add(labelCategoryConfig);
	}

	public void deleteById(Long id) {
		labelCategoryConfigDao.deleteById(id);
	}
	
	public void update(SkuLabelCategoryRelation labelCategoryConfig) {
		labelCategoryConfigDao.update(labelCategoryConfig);
	}

	public List<SkuLabelCategoryRelation> findListByLabelCode(String labelCode) {
		return labelCategoryConfigDao.findListByLabelCode(labelCode);
	}

	public SkuLabelCategoryRelation findObjectById(Long id) {
		return labelCategoryConfigDao.findObjectById(id);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = labelCategoryConfigDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		List<SkuLabelCategoryRelation> slcrList = new ArrayList<SkuLabelCategoryRelation>();
		if (total > 0) {
			slcrList = labelCategoryConfigDao.getListPage(page.getSearchParamsMap());
			for (SkuLabelCategoryRelation skuLabelCategoryRelation : slcrList) {
				SkuLabel skuLabel = skuLabelDao.getByCode(skuLabelCategoryRelation.getLabelCode());
				SkuCategory skuCategory = skuCategoryService.findByCode(skuLabelCategoryRelation.getCategoryCode(),null);
				if (CheckObjectIsNull.isNull(skuLabel,skuCategory)) {
					continue;
				}
				skuLabelCategoryRelation.setLabelName(skuLabel.getName());
				skuLabelCategoryRelation.setCategoryName(skuCategory.getName());
			}
		} 
		page.setResult(slcrList);
		return page;
	}

	public void delete(String labelCode, String categoryCode) {
		SkuLabelCategoryRelation slcr = new SkuLabelCategoryRelation();
		slcr.setLabelCode(labelCode);
		slcr.setCategoryCode(categoryCode);
		labelCategoryConfigDao.delete(slcr);
	}

}
