/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:54:39
 */
package com.aladingshop.periodactivity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.periodactivity.dao.ActivityShareRegisterRecordDao;
import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;
import com.aladingshop.periodactivity.service.ActivityShareRegisterRecordService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:54:39
 */
@Service
public class ActivityShareRegisterRecordServiceImpl implements ActivityShareRegisterRecordService<ActivityShareRegisterRecord> {

	@Autowired
	private ActivityShareRegisterRecordDao dao;
	public void add(ActivityShareRegisterRecord activityShareRegisterRecord) {
		dao.add(activityShareRegisterRecord);
		
	}

	public List<ActivityShareRegisterRecord> findByMemberSharesId(Long memberId) {
		return dao.findByMemberSharesId(memberId);
	}

	public ActivityShareRegisterRecord findBymemberRegisterId(Long registerId) {
		return dao.findBymemberRegisterId(registerId);
	}

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = dao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<ActivityShareRegisterRecord> list = dao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActivityShareRegisterRecord>());
		}
		return page;
	}

	public Integer getTotalBySignId(String signId) {
		
		return dao.getTotalBySignId(signId);
	}

	

}
