/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:41:13
 */
package com.aladingshop.periodactivity.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:41:13
 */
public interface ActivityShareRegisterRecordDao {
	
	void add(ActivityShareRegisterRecord activityShareRegisterRecord);
	
	List<ActivityShareRegisterRecord> findByMemberSharesId(Long memberId);
	
	ActivityShareRegisterRecord findBymemberRegisterId(Long registerId);
	
	
	Integer getTotalBySignId(String signId);
	
	List<ActivityShareRegisterRecord> getListPage(Map<String, Object> map);
	
	Integer getTotal(Map<String, Object> map);
}
