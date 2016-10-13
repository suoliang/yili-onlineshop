package com.fushionbaby.facade.biz.common.member;

import java.math.BigDecimal;

import com.fushionbaby.common.dto.UserDto;

/**
 * 会员信息
 * @author King 索亮
 *
 */
public interface UserFacade {
	/**
	 * 验证用户登录
	 * @param sid
	 * @return
	 */
	boolean checkUserLogin(String sid);
	
	/**
	 * 获取最新的用户信息 
	 * @param sid
	 * @return
	 */
	UserDto getLatestUserBySid(String sid);
	
	
	/** 得到可用的积分
	 * @param user
	 * 
	 * @return 
	 * */
	BigDecimal getCanEpoints(UserDto user);
	
}
