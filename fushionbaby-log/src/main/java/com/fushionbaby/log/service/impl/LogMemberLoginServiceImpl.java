package com.fushionbaby.log.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.log.dao.LogMemberLoginDao;
import com.fushionbaby.log.model.LogMemberLogin;
import com.fushionbaby.log.service.LogMemberLoginService;
/**
 * 
 * @author King suoliang
 *
 */
@Service
public class LogMemberLoginServiceImpl implements LogMemberLoginService {

	@Autowired
	private LogMemberLoginDao logMemberLoginDao;
	
	public void add(LogMemberLogin logMemberLogin) throws DataAccessException {
		logMemberLoginDao.add(logMemberLogin);
	}

	public void deleteById(Long id) throws DataAccessException {
		logMemberLoginDao.deleteById(id);
	}
	
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
	public void add(Long id, String username, String ip, String status) {
		/**登录信息添加到日志*/
		LogMemberLogin logMemberLogin = new LogMemberLogin();
		logMemberLogin.setMemberId(id);
		logMemberLogin.setMemberName(username);
		logMemberLogin.setIpAddress(ip);
		logMemberLogin.setLoginStatus(status);
		logMemberLoginDao.add(logMemberLogin);
	}
	/**通过会员id和登陆时间查询--默认是登陆成功y*/
	public List<LogMemberLogin> findByMemberIdAndLoginTime(Long memberId, String loginTime) {
		return logMemberLoginDao.findByMemberIdAndLoginTime(memberId, loginTime);
	}
	
}
