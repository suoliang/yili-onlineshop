package com.fushionbaby.member.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dao.MemberFeedbackInfoDao;
import com.fushionbaby.member.model.MemberFeedbackInfo;
import com.fushionbaby.member.service.MemberFeedbackInfoService;
/**
 * 
 * @author King suoliang
 *
 */
@Service
public class MemberFeedbackInfoServiceImpl implements MemberFeedbackInfoService<MemberFeedbackInfo> {

	@Autowired
	private MemberFeedbackInfoDao memberFeedbackInfoDao;
	
	public void add(MemberFeedbackInfo memberFeedbackInfo) {
		memberFeedbackInfoDao.add(memberFeedbackInfo);
	}

	public void deleteById(Long id) {
		memberFeedbackInfoDao.deleteById(id);
	}

	public BasePagination findMemberFeedbackInfo(BasePagination page) {
		int total = memberFeedbackInfoDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			page.setResult(memberFeedbackInfoDao.getListPage(page.getSearchParamsMap()));
		}else {
			page.setResult(new ArrayList<MemberFeedbackInfo>());
		}
		return page;
	}

}
