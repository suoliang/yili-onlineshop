/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:53:23
 */
package com.aladingshop.periodactivity.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:53:23
 */
public interface ActivityRedEnvlopeUseRecordService<T extends ActivityRedEnvlopeUseRecord> {
	
	void add(ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord);
	
	List<ActivityRedEnvlopeUseRecord> findByMemberId(Long memberId);
	
	ActivityRedEnvlopeUseRecord findByOrderCode(String orderCode);

	void updateUseStatus(ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord);
	
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;
}
