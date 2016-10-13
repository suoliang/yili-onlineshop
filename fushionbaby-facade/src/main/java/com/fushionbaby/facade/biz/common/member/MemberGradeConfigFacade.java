package com.fushionbaby.facade.biz.common.member;

import com.fushionbaby.member.model.MemberGradeConfig;

/***
 * 
 * @description 类描述... 会员等级
 * @author 徐培峻
 * @date 2015年8月5日上午10:01:30
 */
public interface MemberGradeConfigFacade {

	MemberGradeConfig findByGradeCode(String gradeCode);

}
