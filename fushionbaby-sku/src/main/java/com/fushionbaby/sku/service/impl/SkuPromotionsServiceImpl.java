/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:51:43
 */
package com.fushionbaby.sku.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sku.dao.SkuPromotionsDao;
import com.fushionbaby.sku.model.SkuPromotions;
import com.fushionbaby.sku.service.SkuPromotionsService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月9日上午11:51:43
 */
@Service
public class SkuPromotionsServiceImpl implements SkuPromotionsService {

	@Autowired
	private SkuPromotionsDao dao;
	
	public List<SkuPromotions> queryByPmCode(String pmCode) {
		// TODO Auto-generated method stub
		return dao.queryByPmCode(pmCode);
	}

	public SkuPromotions queryByPmCodeAndSkuCode(String pmCode,String skuCode) {
		// TODO Auto-generated method stub
		return dao.queryByPmCodeAndSkuCode(pmCode,skuCode);
	}

}
