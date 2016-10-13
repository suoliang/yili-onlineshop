/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:52:56
 */
package com.aladingshop.periodactivity.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.aladingshop.periodactivity.model.ActivityShareRegisterRecord;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:52:56
 */
public interface ActivityShareRegisterRecordService<T extends ActivityShareRegisterRecord> {
	
	void add(ActivityShareRegisterRecord activityShareRegisterRecord);
	
	List<ActivityShareRegisterRecord> findByMemberSharesId(Long memberId);
	
	ActivityShareRegisterRecord findBymemberRegisterId(Long registerId);
	
	Integer getTotalBySignId(String signId);
	
	BasePagination getListPage(BasePagination page) throws DataAccessException;
}
