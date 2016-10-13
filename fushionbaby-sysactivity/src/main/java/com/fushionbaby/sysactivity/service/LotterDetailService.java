package com.fushionbaby.sysactivity.service;

import java.util.List;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.service.BaseService;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.LotteryDetail;

/***
 * 抽奖明细的服务
 * 
 * @author xupeijun
 * 
 */
public interface LotterDetailService<T extends LotteryDetail> extends
		BaseService<T> {

	/**查询:1.根据member_id查询 返回封装类对象集合*/
	List<LotteryDetail> findByMemberId(Long memberId);
	
	/**2.根据login_name查询  返回封装类对象集合*/
	BasePagination findByLoginName(BasePagination page);
	
	/**
	 * 保存抽奖明细记录
	 * @param user
	 * @param lotteryPrizeId
	 */
	void saveLotteryDetail(UserDto user,long lotteryPrizeId);
}
