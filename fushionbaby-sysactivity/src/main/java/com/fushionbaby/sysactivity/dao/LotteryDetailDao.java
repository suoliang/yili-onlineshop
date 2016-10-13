package com.fushionbaby.sysactivity.dao;

import java.util.List;
import java.util.Map;

import com.fushionbaby.sysactivity.model.LotteryDetail;

/***
 * 抽奖明细
 * 
 * @author xupeijun
 * 
 */
public interface LotteryDetailDao {
	void add(LotteryDetail lotteryDetail);
	void deleteById(Long id);
	void update(LotteryDetail lotteryDetail);
	LotteryDetail findById(Long id);
	List<LotteryDetail> findAll();
	
	/***
	 * 通过条件查询 ，可能是member_id 也可能是loginName
	 * @param memberId
	 * @param loginName
	 * @return
	 */
	List<LotteryDetail> findWhereList(Map<String, Object> map);
	int getTotal(Map<String, Object> params);
	/**
	 * 分页查询
	 * 
	 * @author
	 */
	List<LotteryDetail> getPageList(Map<String, Object> map);

}
