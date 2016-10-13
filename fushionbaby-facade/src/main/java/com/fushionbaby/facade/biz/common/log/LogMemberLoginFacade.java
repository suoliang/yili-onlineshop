package com.fushionbaby.facade.biz.common.log;

import java.util.List;

import com.fushionbaby.log.model.LogMemberLogin;

/***
 * 
 * @description 类描述...会员登录日志记录
 * @author 徐培峻
 * @date 2015年8月5日上午9:30:03
 */
public interface LogMemberLoginFacade {

	void add(LogMemberLogin logMemberLogin);

	void addToLog(Long memberId, String userName, String ipAddr, String status);

	List<LogMemberLogin> findByMemberIdAndLoginTime(Long memberId, String loginTime);

}
