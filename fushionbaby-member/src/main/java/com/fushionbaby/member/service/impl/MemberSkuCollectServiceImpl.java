package com.fushionbaby.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.member.dao.MemberSkuCollectDao;
import com.fushionbaby.member.model.MemberSkuCollect;
import com.fushionbaby.member.service.MemberSkuCollectService;

/**
 * 
 * @author King
 * 
 */
@Service
public class MemberSkuCollectServiceImpl implements MemberSkuCollectService<MemberSkuCollect> {

	@Autowired
	private MemberSkuCollectDao skuCollectDao;

	public void add(MemberSkuCollect skuCollect) throws DataAccessException {
		skuCollectDao.add(skuCollect);
	}

	public void deleteById(Long id) throws DataAccessException {
		skuCollectDao.deleteById(id);
	}

	public void update(MemberSkuCollect skuCollect) throws DataAccessException {
		skuCollectDao.update(skuCollect);
	}

	public MemberSkuCollect findById(Long id) throws DataAccessException {
		return skuCollectDao.findById(id);
	}

	public List<MemberSkuCollect> findAll() throws DataAccessException {
		return skuCollectDao.findAll();
	}

	public void addFavorites(MemberSkuCollect skuCollect) {
			add(skuCollect);
	}

	public boolean deleteFavorites(MemberSkuCollect skuCollect) {
		SkuCollectQueryCondition queryCondition = new SkuCollectQueryCondition();
		queryCondition.setMemberId(skuCollect.getMemberId());
		queryCondition.setSkuCode(skuCollect.getSkuCode());		
		List<MemberSkuCollect> result = skuCollectDao.queryByCondition(queryCondition);
		if (result != null && result.size() > 0) {
			for (MemberSkuCollect temp : result) {
				temp.setIsAttention(CommonConstant.NO);
				this.update(temp);
			}
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.sku.service.SkuCollectService#queryBySkuId(java.lang.
	 * String, java.lang.Long)
	 */
	public List<MemberSkuCollect> queryByCondition(
			SkuCollectQueryCondition queryCondition) {

		return skuCollectDao.queryByCondition(queryCondition);
	}
/*
 * (non-Javadoc)
 * @see com.fushionbaby.sku.service.SkuCollectService#getTotalByCondition(com.fushionbaby.common.condition.sku.SkuCollectQueryCondition)
 */
	public Integer getTotalByCondition(SkuCollectQueryCondition queryCondition) {
		
		return skuCollectDao.getTotalByCondition(queryCondition);
	}

}
