/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:52:00
 */
package com.aladingshop.periodactivity.service;

import org.springframework.dao.DataAccessException;

import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:52:00
 */
public interface ActivityShareMemberService<T extends ActivityShareMember> {

	
	void add(ActivityShareMember activityShareMember);
	
	void update(ActivityShareMember activityShareMember);
	
	ActivityShareMember findByMemberId(Long memberId);
	
	ActivityShareMember findByInviteCode(String inviteCode);

	BasePagination getListPage(BasePagination page) throws DataAccessException;
}
