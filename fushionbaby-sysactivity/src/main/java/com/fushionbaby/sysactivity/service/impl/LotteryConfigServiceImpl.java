package com.fushionbaby.sysactivity.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.sysactivity.dao.LotteryConfigDao;
import com.fushionbaby.sysactivity.model.LotteryConfig;
import com.fushionbaby.sysactivity.service.LotteryConfigService;

/**
 * @author cyla
 * 
 */
@Service
public class LotteryConfigServiceImpl implements LotteryConfigService<LotteryConfig> {
	
	@Autowired
	private LotteryConfigDao lotteryConfigDao;

	public void add(LotteryConfig lotteryConfig) {
		lotteryConfigDao.add(lotteryConfig);

	}

	public void deleteById(Long id) {
		lotteryConfigDao.deleteById(id);

	}

	public void update(LotteryConfig lotteryConfig) {
		lotteryConfigDao.update(lotteryConfig);

	}

	public LotteryConfig findById(Long id) {
		return lotteryConfigDao.findById(id);
	}

	public List<LotteryConfig> findAll() {
		return lotteryConfigDao.findAll();
	}

	public LotteryConfig findByLotteryCode(String lotterCode) {
		return lotteryConfigDao.findByLotteryCode(lotterCode);
	}
}
