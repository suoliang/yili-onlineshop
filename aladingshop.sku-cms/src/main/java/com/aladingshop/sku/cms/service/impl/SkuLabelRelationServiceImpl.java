/**
 * 
 */
package com.aladingshop.sku.cms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aladingshop.sku.cms.dao.SkuLabelRelationDao;
import com.aladingshop.sku.cms.model.SkuLabelRelation;
import com.aladingshop.sku.cms.service.SkuLabelRelationService;
import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author mengshaobo
 * 
 */
@Service
public class SkuLabelRelationServiceImpl implements
		SkuLabelRelationService<SkuLabelRelation> {

	@Autowired
	private SkuLabelRelationDao skuLabelRelationDao;

	public void add(SkuLabelRelation entity) throws DataAccessException {
		if (entity.getShowOrder() == null) {
			entity.setShowOrder(SortTypeConstant.SHOW_ORDER);
		}
		skuLabelRelationDao.add(entity);

	}

	public void update(SkuLabelRelation entity) throws DataAccessException {
		skuLabelRelationDao.update(entity);

	}

	public SkuLabelRelation findById(Long id) throws DataAccessException {

		return skuLabelRelationDao.findById(id);
	}

	public List<SkuLabelRelation> findAll() throws DataAccessException {

		return skuLabelRelationDao.findAll();
	}
	
	public List<SkuLabelRelation> queryByLabelCode(String labelCode,
			String sortParam, String sortType, Integer pageIndex,
			Integer limit) {

		List<SkuLabelRelation> skuLabelRels = null;

		try {
			SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
			queryCondition.setStart((pageIndex - 1) * limit);
			queryCondition.setLimit(limit);
			queryCondition.setLabelCode(labelCode);
			queryCondition.setSortParam(sortParam);
			queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			if (StringUtils.isNotBlank(sortType)) {
				queryCondition.setSortType(sortType);
			}
			skuLabelRels = skuLabelRelationDao
					.querySkuLabelRelationByCondition(queryCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return skuLabelRels;
	}

	public Integer getTotalByLabelCode(String labelCode) {
		Map<String, Object> paramMaps = new HashMap<String, Object>();
		paramMaps.put("labelCode", labelCode);
		paramMaps.put("status", SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());

		return skuLabelRelationDao.getTotal(paramMaps);
	}

	@Transactional
	public void insertList(List<SkuLabelRelation> list) {
		if (list != null && list.size() > 0) {
			for (SkuLabelRelation rel : list) {
				if (rel.getShowOrder() == null) {
					rel.setShowOrder(SortTypeConstant.SHOW_ORDER);
				}
				skuLabelRelationDao.add(rel);
			}
		}
	}


	public void deleteBySkuCodeAndLabelCode(SkuLabelRelation skuLabelRelation) {
		
		
		SkuLabelRelation result	 = this.findBySkuCodeAndLabel(skuLabelRelation.getSkuCode(), skuLabelRelation.getLabelCode());
		if(result==null){
			return ;
		}

		skuLabelRelationDao.deleteById(result.getId());

	}

	public String getSkuCodeAryByLabelCode(String labelCode) {
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setLabelCode(labelCode);

		List<SkuLabelRelation> labelRelationList = skuLabelRelationDao
				.querySkuLabelRelationByCondition(queryCondition);

		StringBuffer skuIdBuffer = new StringBuffer();
		if (labelRelationList != null && labelRelationList.size() > 0) {
			for (int i = 0; i < labelRelationList.size(); i++) {
				SkuLabelRelation rel = labelRelationList.get(i);
				skuIdBuffer.append(rel.getSkuCode());
				if (i != labelRelationList.size() - 1) {
					skuIdBuffer.append(",");
				}
			}
		}
		return skuIdBuffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.sku.service.SkuLabelRelationService#getPageList(com.
	 * fushionbaby.common.util.BasePagination)
	 */
	public BasePagination getPageList(BasePagination page) {

		Integer total = skuLabelRelationDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<SkuLabelRelation> list = skuLabelRelationDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<SkuLabelRelation>());
		}
		return page;
	}

	public void deleteByLabelCode(String labelCode,String storeCode) {
		
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setLabelCode(labelCode);
		queryCondition.setStoreCode(storeCode);
		List<SkuLabelRelation> relList = skuLabelRelationDao.querySkuLabelRelationByCondition(queryCondition);
		if(relList==null){
			return ;
		}
		for (SkuLabelRelation skuLabelRelation : relList) {
			skuLabelRelationDao.deleteById(skuLabelRelation.getId());
		}

	}

	/**
	 * 条件查询,根据多个sku_id,in查询、得到所有sku_id对应的,label_id
	 * 
	 * @param skuIds
	 * @return
	 */
	public List<String> findLabelCodesBySkuCodes(String skuCodes) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("skuCodes", skuCodes);
		return this.skuLabelRelationDao.findLabelCodesBySkuCodes(map);
	}

	public List<SkuLabelRelation> queryByCondition(
			SkuLabelRelationQueryCondition queryCondition) {
		return skuLabelRelationDao
				.querySkuLabelRelationByCondition(queryCondition);
	}

	public List<String> findSkuCodesByLabel(String labelCode,String storeCode) {
		
		
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setLabelCode(labelCode);
		queryCondition.setStoreCode(storeCode);
		List<SkuLabelRelation> relList = skuLabelRelationDao.querySkuLabelRelationByCondition(queryCondition);
		
		List<String> skuCodes = new ArrayList<String>();
		
		for (SkuLabelRelation skuLabelRelation : relList) {
			skuCodes.add(skuLabelRelation.getSkuCode());
		}
		
		return skuCodes;
	}

	public void deleteById(Long id) throws DataAccessException {
		
		skuLabelRelationDao.deleteById(id);

	}


	public SkuLabelRelation findBySkuCodeAndLabel(String skuCode,String labelCode) {
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setSkuCode(skuCode);
		queryCondition.setLabelCode(labelCode);
		List<SkuLabelRelation> relList = skuLabelRelationDao.querySkuLabelRelationByCondition(queryCondition);
		if(!CollectionUtils.isEmpty(relList) && relList.size() > 0){
			return relList.get(0);
		}
		return null;  
		
	}

	public List<String> queryBySameDayOperateing() {
		return skuLabelRelationDao.queryBySameDayOperateing();
	}

	public SkuLabelRelation queryBySkuCode(String skuCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
