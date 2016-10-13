/**
 * 
 */
package com.aladingshop.card.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.card.dao.MemberCardBackDao;
import com.aladingshop.card.model.MemberCardBack;
import com.aladingshop.card.service.MemberCardBackService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 退卡明细ServiceImpl
 * @author 孙涛
 * @date 2015年10月10日下午2:57:58
 */
@Service
public class MemberCardBackServiceImpl implements MemberCardBackService<MemberCardBack> {
	@Autowired
	private MemberCardBackDao memberCardBackDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#add(java.lang.Object)
	 */
	public void add(MemberCardBack cardBack) throws DataAccessException {
		memberCardBackDao.add(cardBack);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.common.service.BaseService#deleteById(java.lang.Long)
	 */
	public void deleteById(Long id) throws DataAccessException {
		memberCardBackDao.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#update(java.lang.Object)
	 */
	public void update(MemberCardBack cardBack) throws DataAccessException {
		memberCardBackDao.update(cardBack);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#findById(java.lang.Long)
	 */
	public MemberCardBack findById(Long id) throws DataAccessException {
		return memberCardBackDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aladingshop.card.service.MemberCardBackService#findAll()
	 */
	public List<MemberCardBack> findAll() {
		return memberCardBackDao.findAll();
	}
	
	public BasePagination getListPage(BasePagination page) {
		Integer total = this.memberCardBackDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberCardBack> list = this.memberCardBackDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberCardBack>());
		}
		return page;
	}
	
	public MemberCardBack findByCardNo(String cardNo) throws DataAccessException {
		return memberCardBackDao.findByCardNo(cardNo);
	}

}
