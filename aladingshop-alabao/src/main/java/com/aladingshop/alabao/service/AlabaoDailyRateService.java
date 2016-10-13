package com.aladingshop.alabao.service;

import com.aladingshop.alabao.model.AlabaoDailyRate;

public interface AlabaoDailyRateService <T extends AlabaoDailyRate>{
	
	public void add(AlabaoDailyRate alabaoDailyRate);
	
	public void deleteById(Long id);
	
	public void deleteByTime(String time);
	
	public void updateById(AlabaoDailyRate alabaoDailyRate);
	
	public void updateByTime(AlabaoDailyRate alabaoDailyRate);
	
	public AlabaoDailyRate findById(Long id);
	
	public AlabaoDailyRate findByTime(String time);
	
}
