package com.aladingshop.alabao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.dao.AlabaoShiftToRecordDao;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.fushionbaby.common.util.BasePagination;
/**
 * @description 阿拉宝转入金额记录表
 * @author 索亮
 * @date 2015年9月8日下午3:02:35
 */
@Service
@Transactional(propagation=Propagation.REQUIRED,rollbackFor={Exception.class})
public class AlabaoShiftToRecordServiceImpl implements AlabaoShiftToRecordService<AlabaoShiftToRecord>{
	@Autowired
	private AlabaoShiftToRecordDao alabaoShiftToRecordDao;
	public void add(AlabaoShiftToRecord alabaoShiftToRecord) {
		alabaoShiftToRecordDao.add(alabaoShiftToRecord);
		
	}

	public List<AlabaoShiftToRecord> findAllByMemberId(Long memberId) {
		return alabaoShiftToRecordDao.findAllByMemberId(memberId);
	}

	public List<AlabaoShiftToRecord> findAllByAccount(String account) {
		return alabaoShiftToRecordDao.findAllByAccount(account);
	}

	public List<AlabaoShiftToRecord> findAllByBatchNum(String batchNum) {
		return alabaoShiftToRecordDao.findAllByBatchNum(batchNum);
	}

	public BasePagination getListPage(BasePagination page) {
		Integer total = alabaoShiftToRecordDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoShiftToRecord> list = alabaoShiftToRecordDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoShiftToRecord>());
		}
		return page;
	}
	
	public List<AlabaoShiftToRecord> getListPageParam(String account,Integer start,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("start", (start-1)*limit);
		map.put("limit", limit);
		return alabaoShiftToRecordDao.getListPage(map);
	}

	public void deleteByAccountAndBatchNum(String accountCode, String batchNum) {
		alabaoShiftToRecordDao.deleteByAccountAndBatchNum(accountCode, batchNum);
	}

	public List<AlabaoShiftToRecord> findBillRecordByTime(String account) {
		return alabaoShiftToRecordDao.findBillRecordByTime(account);
	}
	
	public void insertBatch(List<AlabaoShiftToRecord> list) {
		for (AlabaoShiftToRecord alabaoShiftToRecord : list) {
			alabaoShiftToRecordDao.add(alabaoShiftToRecord);
		}
	}
}
