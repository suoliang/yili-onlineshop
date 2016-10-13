package com.fushionbaby.member.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.member.model.MemberFeedbackInfo;

/**
 * 
 * @author King suoliang
 *
 */
public interface MemberFeedbackInfoDao {
	
	void add(MemberFeedbackInfo memberFeedbackInfo);
	
	void deleteById(Long id);

	int getTotal(Map<String, Object> searchParamsMap);

	List<MemberFeedbackInfo> getListPage(Map<String, Object> searchParamsMap);
	
}
