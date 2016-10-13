/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:48:13
 */
package com.aladingshop.periodactivity.dao;

import java.util.List;
import java.util.Map;

import com.aladingshop.periodactivity.model.ActivityShareMember;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:48:13
 */
public interface ActivityShareMemberDao {

	void add(ActivityShareMember activityShareMember);
	
	void update(ActivityShareMember activityShareMember);
	
	ActivityShareMember findByMemberId(Long memberId);
	
	ActivityShareMember findByInviteCode(String inviteCode);
	
	List<ActivityShareMember> getListPage(Map<String, Object> map);

	Integer getTotal(Map<String, Object> map);
}
