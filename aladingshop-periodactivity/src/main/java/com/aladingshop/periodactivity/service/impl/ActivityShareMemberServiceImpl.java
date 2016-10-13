/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:58:39
 */
package com.aladingshop.periodactivity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.periodactivity.dao.ActivityShareMemberDao;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月28日上午11:58:39
 */
@Service
@Transactional
public class ActivityShareMemberServiceImpl implements
		ActivityShareMemberService<ActivityShareMember> {

	@Autowired
	private ActivityShareMemberDao dao;
	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityShareMemberService#add(com.aladingshop.periodactivity.model.ActivityShareMember)
	 */
	public void add(ActivityShareMember activityShareMember) {
		dao.add(activityShareMember);

	}

	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityShareMemberService#update(com.aladingshop.periodactivity.model.ActivityShareMember)
	 */
	public void update(ActivityShareMember activityShareMember) {
		dao.update(activityShareMember);

	}

	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityShareMemberService#findByMemberId(java.lang.Long)
	 */
	public ActivityShareMember findByMemberId(Long memberId) {
		return dao.findByMemberId(memberId);
	}

	/* (non-Javadoc)
	 * @see com.aladingshop.periodactivity.sevice.ActivityShareMemberService#findByInviteCode(java.lang.String)
	 */
	public ActivityShareMember findByInviteCode(String inviteCode) {
		return dao.findByInviteCode(inviteCode);
	}

	
	
	public BasePagination getListPage(BasePagination page)
			throws DataAccessException {
		
		Integer total = dao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<ActivityShareMember> list = dao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActivityShareMember>());
		}
		return page;
		
	}

}
