package com.aladingshop.alabao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.dao.AlabaoTurnToAlabaoDao;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.fushionbaby.common.util.BasePagination;

@Service
@Transactional
public class AlabaoTurnToAlabaoServiceImpl implements AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> {

	@Autowired
	private AlabaoTurnToAlabaoDao alabaoTurnToAlabaoDao;
	
	public void add(AlabaoTurnToAlabao alabaoTurnToAlabao) {
		alabaoTurnToAlabaoDao.add(alabaoTurnToAlabao);
	}

	public List<AlabaoTurnToAlabao> findByBatchNum(String batchNum) {
		return alabaoTurnToAlabaoDao.findByBatchNum(batchNum);
	}

	public BasePagination getListPage(BasePagination page) {
		
		Integer total = alabaoTurnToAlabaoDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoTurnToAlabao> list = alabaoTurnToAlabaoDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoTurnToAlabao>());
		}
		return page;
	}

	public List<AlabaoTurnToAlabao> findByAccountCode(String accountCode) {
		return alabaoTurnToAlabaoDao.findByAccountCode(accountCode);
	}

	public void updateStatus(AlabaoTurnToAlabao alabaoTurnToAlabao) {
		
		alabaoTurnToAlabaoDao.updateStatus(alabaoTurnToAlabao);
	}

	public AlabaoTurnToAlabao findBySerialNum(String serialNum) {
		return alabaoTurnToAlabaoDao.findBySerialNum(serialNum);
	}

}
