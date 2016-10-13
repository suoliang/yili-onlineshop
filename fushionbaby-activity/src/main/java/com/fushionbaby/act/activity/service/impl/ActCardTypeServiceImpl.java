package com.fushionbaby.act.activity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.act.activity.dao.ActCardTypeDao;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardTypeService;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author cyla
 * 
 */
@Service
public class ActCardTypeServiceImpl implements ActCardTypeService<ActCardType> {
	@Autowired
	private ActCardTypeDao actCardTypeDao;
	

	public void add(ActCardType actCardType) {
		actCardTypeDao.add(actCardType);
	}

	public void deleteById(Long id) {
		actCardTypeDao.deleteById(id);
	}

	public void update(ActCardType actCardType) {
		actCardTypeDao.update(actCardType);
	}

	public ActCardType findById(Long id) throws DataAccessException {
		return actCardTypeDao.findById(id);
	}

	public List<ActCardType> findAll() throws DataAccessException {
		return actCardTypeDao.findAll();
	}

	public BasePagination getListPage(BasePagination page) {
		// 查询总数量
		page.setTotal(actCardTypeDao.getTotal(page.getSearchParamsMap()));
		// 判断总数量是否大于0
		if (page.getTotal() > 0) {
			
			// 显示分页结果
			page.setResult(actCardTypeDao.getListPage(page.getSearchParamsMap()));
		} else {
			page.setResult(new ArrayList<ActCardType>());
		}
		
		return page;
	}

	public Integer findLimitCountById(Long cardTypeId) {
		ActCardType act = this.actCardTypeDao.findById(cardTypeId);
		if (act != null) {
			return act.getUseCountLimit();
		} else {
			return null;
		}
	}

	public ActCardType findByCode(String code) {
		return this.actCardTypeDao.findByCode(code);
	}

}
