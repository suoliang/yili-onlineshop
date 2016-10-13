package com.fushionbaby.sysactivity.service;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.sysactivity.model.LotteryInfo;

/**
 * 
 * @author King 索亮
 *
 */
public interface LotteryInfoService <T extends LotteryInfo> {
	void add(LotteryInfo lotteryInfo);
	/**查询:1.根据member_id查询 返回封装类对象*/
	LotteryInfo findByMemberId(Long memberId);
	/**2.根据login_name查询  返回封装类对象*/
	LotteryInfo findByLoginName(String loginName);
	
	/**
	 * 根据memberId更新会员抽奖已经使用过的抽奖次数
	 * @param lotteryInfo
	 */
	void updateLotteryInfoNum(LotteryInfo lotteryInfo);
	
	/**test*/
	void deleteById(Long id);
	
	/**
	 * 更新或者新增会员抽奖记录
	 * @param user
	 * @param lotteryInfo
	 */
	void updateOrSaveLotteryInfo(UserDto user, LotteryInfo lotteryInfo);
}
