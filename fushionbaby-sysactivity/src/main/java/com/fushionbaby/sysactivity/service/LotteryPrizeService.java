package com.fushionbaby.sysactivity.service;

import com.fushionbaby.sysactivity.model.LotteryPrize;

/**
 * @author 张明亮
 */
public interface LotteryPrizeService <T extends LotteryPrize>{
	
	/**
	 * 查询得到对应等级的奖品对象
	 * @param id
	 * @return
	 */
    LotteryPrize getByLevel(Long level);
    
    /**
     * 更新奖池对应级别的奖品剩余数量 
     * @param lotteryPrize
     */
    void updateLotteryRemainPrize(LotteryPrize lotteryPrize);
    
    /**
     * 开始抽奖业务、返回奖品对象
     * @param orderStint
     * @param orderCount
     * @return
     */
    LotteryPrize getRandomLotteryPrize(int orderStint, int orderCount);
}
