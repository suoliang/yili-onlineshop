package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.common.condition.sku.SkuLinkSkusQueryCondition;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.dao.SkuLinkSkusRelationDao;
import com.fushionbaby.sku.model.SkuLinkSkusRelation;
import com.fushionbaby.sku.service.SkuLinkSkusRelationService;

/**
 * 
 * @author King suoliang
 * 
 */
@Service
public class SkuLinkSkusRelationServiceImpl implements SkuLinkSkusRelationService<SkuLinkSkusRelation> {

	@Autowired
	private SkuLinkSkusRelationDao skuLinkSkusDao;

	public void add(SkuLinkSkusRelation skuLinkSkus) throws DataAccessException {
		skuLinkSkusDao.add(skuLinkSkus);
	}

	public void deleteById(Long id) throws DataAccessException {
		skuLinkSkusDao.deleteById(id);
	}

	public void update(SkuLinkSkusRelation skuLinkSkus) throws DataAccessException {
		skuLinkSkusDao.update(skuLinkSkus);
	}

	public SkuLinkSkusRelation findById(Long id) throws DataAccessException {
		return skuLinkSkusDao.findById(id);
	}

	public List<SkuLinkSkusRelation> findAll() throws DataAccessException {
		return skuLinkSkusDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.sku.service.SkuLinkSkusService#queryByCondition(com.
	 * fushionbaby.sku.condition.SkuLinkSkusQueryCondition)
	 */
	public List<SkuLinkSkusRelation> queryByCondition(
			SkuLinkSkusQueryCondition queryCondition) {
		return skuLinkSkusDao.queryByCondition(queryCondition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuLinkSkusService#getByListPage(com.fushionbaby
	 * .common.util.BasePagination)
	 */
	public BasePagination getByListPage(BasePagination pageParams) {

		Integer total = skuLinkSkusDao
				.getTotal(pageParams.getSearchParamsMap());
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			List<SkuLinkSkusRelation> list = skuLinkSkusDao.getListPage(pageParams
					.getSearchParamsMap());
			pageParams.setResult(list);
		} else {
			pageParams.setResult(new ArrayList<SkuLinkSkusRelation>());
		}
		return pageParams;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuLinkSkusService#insertList(java.util.List)
	 */
	@Transactional
	public void insertList(List<SkuLinkSkusRelation> list, Long adminId) {
		for (SkuLinkSkusRelation linkSku : list) {
			linkSku.setAdminId(adminId);
			skuLinkSkusDao.add(linkSku);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuLinkSkusService#deleteByCondition(com.
	 * fushionbaby.sku.condition.SkuLinkSkusQueryCondition)
	 */
	public void deleteByCondition(SkuLinkSkusQueryCondition queryCondition) {
		List<SkuLinkSkusRelation> skuLinkSkusList = this
				.queryByCondition(queryCondition);
		for (SkuLinkSkusRelation skuLinkSkus : skuLinkSkusList) {
			skuLinkSkusDao.deleteById(skuLinkSkus.getId());
		}

	}

	/**
	 * 查询商品关联Id集合
	 * 
	 * @param skuId
	 * @return
	 */
	public String getLinkSkuCodeAry(String skuCode) {

		SkuLinkSkusQueryCondition queryCondition = new SkuLinkSkusQueryCondition();
		queryCondition.setSkuCode(skuCode);
		
		List<SkuLinkSkusRelation> linkSkuList = skuLinkSkusDao
				.queryByCondition(queryCondition);

		StringBuffer linkSkuCodeBuffer = new StringBuffer();
		if (linkSkuList != null && linkSkuList.size() > 0) {
			for (int i = 0; i < linkSkuList.size(); i++) {
				SkuLinkSkusRelation rel = linkSkuList.get(i);
				linkSkuCodeBuffer.append(rel.getLinkSkuCode());
				if (i != linkSkuList.size() - 1) {
					linkSkuCodeBuffer.append(",");
				}

			}
		}

		return linkSkuCodeBuffer.toString();
	}



}
