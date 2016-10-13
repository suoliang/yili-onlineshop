package com.aladingshop.alabao.service.impl; 


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.alabao.dao.AlabaoRollOffRecordDao;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.fushionbaby.common.util.BasePagination;

@Service
@Transactional
public class AlabaoRollOffRecordServiceImpl implements AlabaoRollOffRecordService<AlabaoRollOffRecord>  { 
	
	@Autowired
	private AlabaoRollOffRecordDao objectDao;
	
	public void add(AlabaoRollOffRecord object) {
		objectDao.add(object);
		
	}
	
	public AlabaoRollOffRecord findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(AlabaoRollOffRecord object) {
		objectDao.update(object);
		
	}
	
	public List<AlabaoRollOffRecord> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<AlabaoRollOffRecord> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<AlabaoRollOffRecord>());
		}
		return page;
	}

	public List<AlabaoRollOffRecord> findByMemberIdTime(Long memberId, Date createTime) {
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("createTime", createTime);
		return objectDao.findByMemberIdTime(map);
	}

	public List<AlabaoRollOffRecord> findAllByMemberId(Long memberId) {
		return objectDao.findAllByMemberId(memberId);
	}

	public List<AlabaoRollOffRecord> findAllByAccount(String account) {
		return objectDao.findAllByAccount(account);
	}

	public List<AlabaoRollOffRecord> findByBatchNum(String batchNum) {
		return objectDao.findByBatchNum(batchNum);
		
		
	}

	public List<AlabaoRollOffRecord> getListPageParam(String account,Integer start, Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("start", (start-1)*limit);
		map.put("limit", limit);
		return objectDao.getListPage(map);
	}

	public List<AlabaoRollOffRecord> findBillRecordByTime(String account) {
		return objectDao.findBillRecordByTime(account);
	}

	public AlabaoRollOffRecord findBySerialNum(String serialNum) {
		
		return this.objectDao.findBySerialNum(serialNum);
	}
	
}
