/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:54:53
 */
package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuPromotionsInfoDao;
import com.fushionbaby.sku.model.SkuPromotionsInfo;
import com.fushionbaby.sku.service.SkuPromotionsInfoService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:54:53
 */
@Service
public class SkuPromotionsInfoServiceImpl implements SkuPromotionsInfoService {

	@Autowired
	private SkuPromotionsInfoDao dao;
	/* (non-Javadoc)
	 * @see com.fushionbaby.sku.service.SkuPromotionsInfoService#queryByPmCode(java.lang.String)
	 */
	public SkuPromotionsInfo queryByPmCode(String promotionsCode) {
		// TODO Auto-generated method stub
		//return dao.queryByPmCode(promotionsCode);
		return dao.queryByPmCode(promotionsCode);
	}
	public List<SkuPromotionsInfo> queryAll() {
		// TODO Auto-generated method stub
		return dao.queryAll();
	}

}
