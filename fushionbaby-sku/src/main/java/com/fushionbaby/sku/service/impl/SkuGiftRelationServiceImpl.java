/**
 * 
 */
package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.common.condition.sku.SkuGiftQueryCondition;
import com.fushionbaby.sku.dao.SkuGiftRelationDao;
import com.fushionbaby.sku.model.SkuGiftRelation;
import com.fushionbaby.sku.service.SkuGiftRelationService;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuGiftRelationServiceImpl implements SkuGiftRelationService<SkuGiftRelation> {

	@Autowired
	private SkuGiftRelationDao skuGiftDao;

	public void add(SkuGiftRelation entity) throws DataAccessException {
		skuGiftDao.add(entity);
	}

	public void update(SkuGiftRelation entity) throws DataAccessException {
	}

	public void deleteById(Long id) throws DataAccessException {
		skuGiftDao.deleteById(id);
	}

	public SkuGiftRelation findById(Long id) throws DataAccessException {
		return skuGiftDao.findById(id);
	}

	public List<SkuGiftRelation> findAll() throws DataAccessException {
		return skuGiftDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuGiftService#insertList(java.util.List)
	 */
	@Transactional
	public void insertList(List<SkuGiftRelation> list) {
		if (list != null && list.size() > 0)
			for (SkuGiftRelation skuGift : list) {
				skuGiftDao.add(skuGift);
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuGiftService#queryByCondition(com.fushionbaby
	 * .sku.condition.SkuGiftQueryCondition)
	 */
	public List<SkuGiftRelation> queryByCondition(SkuGiftQueryCondition queryCondition) {
		return skuGiftDao.queryByCondition(queryCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuGiftService#deleteSkuGiftByCondition(com
	 * .fushionbaby.sku.condition.SkuGiftQueryCondition)
	 */
	@Transactional
	public void deleteSkuGiftByCondition(SkuGiftQueryCondition queryCondition) {
		List<SkuGiftRelation> skuGiftList = this.queryByCondition(queryCondition);
		for (SkuGiftRelation skuGift : skuGiftList) {
			skuGiftDao.deleteById(skuGift.getId());
		}
	}

	/**
	 * 获取商品赠品编号集合
	 * 
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	public String getGiftSkuCodeAryBySkuCode(String skuCode) {
		SkuGiftQueryCondition queryCondition = new SkuGiftQueryCondition();
		queryCondition.setSkuCode(skuCode);
		List<SkuGiftRelation> giftList = skuGiftDao.queryByCondition(queryCondition);

		StringBuffer skuGiftCodeBuffer = new StringBuffer();
		if (giftList != null && giftList.size() > 0) {
			for (int i = 0; i < giftList.size(); i++) {
				SkuGiftRelation rel = giftList.get(i);
				skuGiftCodeBuffer.append(rel.getGiftSkuCode());
				if (i != giftList.size() - 1) {
					skuGiftCodeBuffer.append(",");
				}

			}
		}

		return skuGiftCodeBuffer.toString();
	}

}
