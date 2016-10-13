package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.VerificationRecordDao;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.VerificationRecordService;
/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年12月7日下午3:53:11
 */
@Service
public class VerificationRecordServiceImpl  implements VerificationRecordService<VerificationRecord>{

	@Autowired
	private VerificationRecordDao verficationRecordDao;
	
	
	public void add(VerificationRecord record) throws DataAccessException {
		this.verficationRecordDao.add(record);
	}

	public void deleteById(Long id) throws DataAccessException {
        this.verficationRecordDao.deleteById(id);		
	}

	public void update(VerificationRecord entity) throws DataAccessException {
		this.verficationRecordDao.update(entity);
	}

	public VerificationRecord findById(Long id) throws DataAccessException {
		return this.verficationRecordDao.findById(id);
	}

	public List<VerificationRecord> findByBankCardNoAndID(String cardNo,String idCard, String trueName) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("bankCardNo", cardNo);
		map.put("identityCardNo", idCard);
		map.put("trueName", trueName);
		return this.verficationRecordDao.findByBankCardNoAndID(map);
	}

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = verficationRecordDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<VerificationRecord> list = verficationRecordDao
					.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<VerificationRecord>());
		}
		return page;
		
	}
	
}

