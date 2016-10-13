package com.fushionbaby.member.service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberFeedbackInfo;

/**
 * 会员意见反馈
 * @author King suoliang
 *
 */
public interface MemberFeedbackInfoService<T extends MemberFeedbackInfo> {
	
	void add(MemberFeedbackInfo memberFeedbackInfo);
	
	void deleteById(Long id);
	
	/**
	 * 通过版本编号查询反馈内容实现分页
	 * 
	 * @param page
	 * @return
	 */
	BasePagination findMemberFeedbackInfo(BasePagination page);
	
}
