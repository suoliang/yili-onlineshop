package com.fushionbaby.sysactivity.dao;

import com.fushionbaby.sysactivity.model.LotteryInfo;

/**
 * 
 * @author King 索亮
 *
 */
public interface LotteryInfoDao {
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
}
