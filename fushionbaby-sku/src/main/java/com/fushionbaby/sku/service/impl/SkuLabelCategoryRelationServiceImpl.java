package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.LabelCategoryRelationQueryCondition;
import com.fushionbaby.sku.dao.SkuLabelCategoryRelationDao;
import com.fushionbaby.sku.model.SkuLabelCategoryRelation;
import com.fushionbaby.sku.service.SkuLabelCategoryRelationService;

/**
 * @description 标签和分类的关联
 * @author 索亮
 * @date 2015年7月27日下午7:22:44
 */
@Service
public class SkuLabelCategoryRelationServiceImpl implements SkuLabelCategoryRelationService<SkuLabelCategoryRelation> {
	@Autowired
	private SkuLabelCategoryRelationDao labelCategoryConfigDao;

	public List<SkuLabelCategoryRelation> findListByLabelCode(String labelCode) {
		return labelCategoryConfigDao.findListByLabelCode(labelCode);
	}

	public SkuLabelCategoryRelation findObjectById(Long id) {
		return labelCategoryConfigDao.findObjectById(id);
	}

	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuLabelCategoryRelationService#findSkuCodeByLabelAndCategory(java.lang.String, java.lang.String)
	 */
	public List<String> findSkuCodeByLabelAndCategory(LabelCategoryRelationQueryCondition queryCondtion) {
		
		return labelCategoryConfigDao.findSkuCodeByLabelAndCategory(queryCondtion);
	}

}
