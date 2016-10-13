package com.fushionbaby.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fushionbaby.log.model.LogMemberLogin;

/**
 * 
 * @author King suoliang
 *
 */
public interface LogMemberLoginDao {
	
	void add(LogMemberLogin logMemberLogin);
	
	void deleteById(Long id);
	/**通过会员id和登陆时间查询--默认是登陆成功y*/
	List<LogMemberLogin> findByMemberIdAndLoginTime(@Param("memberId")Long memberId,@Param("loginTime")String loginTime);
	
}
   