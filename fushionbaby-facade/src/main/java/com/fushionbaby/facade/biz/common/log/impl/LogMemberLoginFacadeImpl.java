package com.fushionbaby.facade.biz.common.log.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.facade.biz.common.log.LogMemberLoginFacade;
import com.fushionbaby.log.model.LogMemberLogin;
import com.fushionbaby.log.service.LogMemberLoginService;

/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月5日上午9:29:43
 */
@Service
public class LogMemberLoginFacadeImpl implements LogMemberLoginFacade {

	
	@Autowired
	private  LogMemberLoginService  logMemberLoginService;
	
	public void add(LogMemberLogin logMemberLogin) {
		logMemberLoginService.add(logMemberLogin);
	}

	public void addToLog(Long memberId, String userName, String ipAddr,String status) {
		logMemberLoginService.add(memberId, userName, ipAddr, status);
		
	}
	public List<LogMemberLogin> findByMemberIdAndLoginTime(Long memberId,String loginTime) {
		return logMemberLoginService.findByMemberIdAndLoginTime(memberId, loginTime);
	}

}
