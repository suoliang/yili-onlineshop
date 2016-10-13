package com.fushionbaby.sku.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.sku.dao.SkuLabelRelationDao;
import com.fushionbaby.sku.service.SkuLabelRelationService;
/**
 *
 * @author 孟少博  2015-7-8
 */
@Service
public class SkuLabelRelationServiceImpl implements SkuLabelRelationService {

	@Autowired
	private SkuLabelRelationDao dao;
	
	
	public List<String> querySkuUnCodeListByLabel(
			SkuLabelRelationQueryCondition queryCondition) {
		
		return dao.querySkuUnCodeListByLabel(queryCondition);
	}


	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuLabelRelationService#findLabelCodesBySkuCodes(java.lang.String)
	 */
	public List<String> findLabelCodesBySkuCodes(List<String> skuCodes) {
		
		if(CollectionUtils.isEmpty(skuCodes)){
			return new ArrayList<String>();
		}
		return dao.findLabelCodesBySkuCodes(skuCodes);
	}


	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuLabelRelationService#getSkuCodeAryByLabelCode(java.lang.String)
	 */
	public String getSkuCodeAryByLabelCode(String labelCode) {
		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();
		queryCondition.setLabelCode(labelCode);

		List<String> skuCodeList = this.querySkuUnCodeListByLabel(queryCondition);

		StringBuffer skuCodeBuffer = new StringBuffer();
		if (skuCodeList != null && skuCodeList.size() > 0) {
			for (int i = 0; i < skuCodeList.size(); i++) {
				String skuCode = skuCodeList.get(i);
				skuCodeBuffer.append(skuCode);
				if (i != skuCodeList.size() - 1) {
					skuCodeBuffer.append(",");
				}

			}
		}
		return skuCodeBuffer.toString();
	}


	public List<String> queryBySameDayOperateing() {
		// TODO Auto-generated method stub
		return dao.queryBySameDayOperateing();
	}

}
