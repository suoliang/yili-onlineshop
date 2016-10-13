package com.aladingshop.alabao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.alabao.dao.AlabaoDailyRateDao;
import com.aladingshop.alabao.model.AlabaoDailyRate;
import com.aladingshop.alabao.service.AlabaoDailyRateService;

@Service
public class AlabaoDailyRateServiceImpl implements AlabaoDailyRateService<AlabaoDailyRate> {

	@Autowired
	private AlabaoDailyRateDao alabaoDailyRateDao;
	
	public void add(AlabaoDailyRate alabaoDailyRate) {
		alabaoDailyRateDao.add(alabaoDailyRate);
		
	}

	public void deleteById(Long id) {
		alabaoDailyRateDao.deleteById(id);
		
	}

	public AlabaoDailyRate findById(Long id) {
		return alabaoDailyRateDao.findById(id);
	}

	public void updateById(AlabaoDailyRate alabaoDailyRate) {
		alabaoDailyRateDao.updateById(alabaoDailyRate);
		
	}

	public void deleteByTime(String time) {
		alabaoDailyRateDao.deleteByTime(time);
	}

	public void updateByTime(AlabaoDailyRate alabaoDailyRate) {
		alabaoDailyRateDao.updateByTime(alabaoDailyRate);
		
	}

	public AlabaoDailyRate findByTime(String time) {
		return alabaoDailyRateDao.findByTime(time);
		 
	}

	


}
