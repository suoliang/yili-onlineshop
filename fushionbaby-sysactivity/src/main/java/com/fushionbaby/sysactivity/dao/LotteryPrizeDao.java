package com.fushionbaby.sysactivity.dao;

import com.fushionbaby.sysactivity.model.LotteryPrize;

public interface LotteryPrizeDao {
	
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
}