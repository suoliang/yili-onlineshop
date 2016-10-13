/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:50:20
 */
package com.aladingshop.periodactivity.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:50:20
 */
public interface ActivityRedEnvlopeUseRecordDao {

	void add(ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord);
	
	List<ActivityRedEnvlopeUseRecord> findByMemberId(Long memberId);
	
	ActivityRedEnvlopeUseRecord findByOrderCode(String orderCode);
	
	void updateUseStatus(ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord);
	
	List<ActivityRedEnvlopeUseRecord> getListPage(Map<String, Object> map);

	Integer getTotal(Map<String, Object> map);
}
