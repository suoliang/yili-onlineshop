/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日下午12:00:02
 */
package com.aladingshop.periodactivity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.periodactivity.dao.ActivityRedEnvlopeUseRecordDao;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日下午12:00:02
 */
@Service
public class ActivityRedEnvlopeUseRecordServiceImpl implements
		ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> {

	@Autowired
	private ActivityRedEnvlopeUseRecordDao dao;
	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityRedEnvlopeUseRecordService#add(com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord)
	 */
	public void add(ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord) {
		dao.add(activityRedEnvlopeUseRecord);

	}

	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityRedEnvlopeUseRecordService#findByMemberId(java.lang.Long)
	 */
	public List<ActivityRedEnvlopeUseRecord> findByMemberId(Long memberId) {
		return dao.findByMemberId(memberId);
	}

	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityRedEnvlopeUseRecordService#findByOrderCode(java.lang.String)
	 */
	public ActivityRedEnvlopeUseRecord findByOrderCode(String orderCode) {
		return dao.findByOrderCode(orderCode);
	}

	public void updateUseStatus(
			ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord) {
		dao.updateUseStatus(activityRedEnvlopeUseRecord);
	}

	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		Integer total = dao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<ActivityRedEnvlopeUseRecord> list = dao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActivityRedEnvlopeUseRecord>());
		}
		return page;
	}

}
