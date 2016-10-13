package com.fushionbaby.log.service;

import java.util.List;

import com.fushionbaby.log.model.LogMemberLogin;

/**
 * 
 * @author King suoliang
 *
 */
public interface LogMemberLoginService {
	
	void add(LogMemberLogin logMemberLogin);
	
	void deleteById(Long id);
	
	/**
	 * 登陆日志的添加
	 * @param id
	 * 			会员id
	 * @param username
	 * 			会员名称
	 * @param ip
	 * 			登陆ip
	 * @param status
	 * 			登陆状态
	 */
	void add(Long id,String username,String ip,String status);
	
	/**通过会员id和登陆时间查询--默认是登陆成功y*/
	List<LogMemberLogin> findByMemberIdAndLoginTime(Long memberId,String loginTime);
	
}
