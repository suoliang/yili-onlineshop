package com.fushionbaby.sysactivity.service;

import java.util.List;

import com.fushionbaby.sysactivity.model.LotteryConfig;

/**
 * @author cyla
 * 
 */
public interface LotteryConfigService <T extends LotteryConfig>{
	
	void add(LotteryConfig lotteryConfig);

	void deleteById(Long id);

	void update(LotteryConfig lotteryConfig);

	LotteryConfig findById(Long id);

	List<LotteryConfig> findAll();

	LotteryConfig findByLotteryCode(String lotterCode);
}
