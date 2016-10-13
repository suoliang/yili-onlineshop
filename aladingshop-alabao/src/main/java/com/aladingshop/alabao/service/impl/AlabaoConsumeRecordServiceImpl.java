package com.aladingshop.alabao.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.dao.AlabaoConsumeRecordDao;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.fushionbaby.common.util.BasePagination;

@Service
@Transactional
public class AlabaoConsumeRecordServiceImpl implements AlabaoConsumeRecordService<AlabaoConsumeRecord> {

	@Autowired
	private AlabaoConsumeRecordDao alabaoConsumeRecordDao;
	
	public void add(AlabaoConsumeRecord alabaoConsumeRecord) {
		alabaoConsumeRecordDao.add(alabaoConsumeRecord);
		
	}

	public void deleteById(Long id) {
		alabaoConsumeRecordDao.deleteById(id);
		
	}

	public AlabaoConsumeRecord findById(Long id) {
		return alabaoConsumeRecordDao.findById(id);
	}

	public void updateByAccount(AlabaoConsumeRecord alabaoConsumeRecord) {
		alabaoConsumeRecordDao.updateByAccount(alabaoConsumeRecord);
		
	}

	public void deleteByAccount(String account) {
		alabaoConsumeRecordDao.deleteByAccount(account);
	}


	public AlabaoConsumeRecord findByAccount(String account) {
		return alabaoConsumeRecordDao.findByAccount(account);
	}

	public BasePagination getListPage(BasePagination pageParams) {
		Map<String, Object> map = pageParams.getSearchParamsMap();
		List<AlabaoConsumeRecord> alabaoConsumeRecordList = new ArrayList<AlabaoConsumeRecord>();
		Integer total = alabaoConsumeRecordDao.getTotal(map);
		pageParams.setCurrentTotal(total);
		if (total > 0) {
			alabaoConsumeRecordList = alabaoConsumeRecordDao.getList(map);
		}
		pageParams.setResult(alabaoConsumeRecordList);
		return pageParams;
	}

	public List<AlabaoConsumeRecord> findAllByMemberId(Long memberId) {
		return alabaoConsumeRecordDao.findAllByMemberId(memberId);
	}

	public List<AlabaoConsumeRecord> findAllByAccount(String account) {
		return alabaoConsumeRecordDao.findAllByAccount(account);
	}

	public AlabaoConsumeRecord findByMemberIdAndOrderCode(Long memberId,
			String orderCode) {
		return this.alabaoConsumeRecordDao.findByMemberIdAndOrderCode(memberId, orderCode);
	}

	public void deleteByOrderCodeAndMemberId(String orderCode, Long memberId) {
		this.alabaoConsumeRecordDao.deleteByOrderCodeAndMemberId(orderCode, memberId);
	}

	public AlabaoConsumeRecord findByOrderCode(String orderCode) {
		return this.alabaoConsumeRecordDao.findByOrderCode(orderCode);
	}

	public List<AlabaoConsumeRecord> getListPageParam(String account,Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("start", (start-1)*limit);
		map.put("limit", limit);
		return alabaoConsumeRecordDao.getList(map);
	}

	public List<AlabaoConsumeRecord> findBillRecordByTime(String account) {
		return alabaoConsumeRecordDao.findBillRecordByTime(account);
	}
	
	public String getListPageMoney(BasePagination page) {
		Map<String,Object> map= this.alabaoConsumeRecordDao.getListPageMoney(page.getSearchParamsMap());
		BigDecimal totalMoney = new BigDecimal("0");
		if(null != map){
		 totalMoney= (BigDecimal) map.get("total_income_money");
		}
		return String.valueOf(totalMoney);
	}
	
	

}
