package com.fushionbaby.facade.biz.common.member.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.facade.biz.common.member.MemberGradeConfigFacade;
import com.fushionbaby.member.model.MemberGradeConfig;
import com.fushionbaby.member.service.MemberGradeConfigService;
/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月5日上午10:39:14
 */
@Service
public class MemberGradeConfigFacadeImpl implements MemberGradeConfigFacade{

	
	@Autowired
	private MemberGradeConfigService MemberGradeConfigService;
	
	public MemberGradeConfig findByGradeCode(String gradeCode) {
		
		return MemberGradeConfigService.findByGradeCode(gradeCode);
	}

}
