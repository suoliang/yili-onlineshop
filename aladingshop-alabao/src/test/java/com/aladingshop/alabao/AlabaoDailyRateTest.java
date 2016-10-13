package com.aladingshop.alabao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.aladingshop.alabao.model.AlabaoDailyRate;
import com.aladingshop.alabao.service.AlabaoDailyRateService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateFormat;

public class AlabaoDailyRateTest {

	private AlabaoDailyRateService alabaoDailyRateService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoDailyRateService = (AlabaoDailyRateService) ac.getBean("alabaoDailyRateServiceImpl");
	}

	
	public void add() {
		AlabaoDailyRate alabao = new AlabaoDailyRate();
		alabao.setId(MyTestConstant.ID);
		alabao.setRate(BigDecimal.valueOf(12.5));
		alabao.setTime(new Date());
		alabaoDailyRateService.add(alabao);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		alabaoDailyRateService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByTime() {
		add();
		alabaoDailyRateService.deleteByTime("2015-09-08");
		add();
	}
	

	@Test
	public void testUpdate() {
		add();
		AlabaoDailyRate alabao = new AlabaoDailyRate();
		alabao.setId(MyTestConstant.ID);
		alabao.setRate(BigDecimal.valueOf(12.5));
		alabao.setTime(new Date());
		alabao.setUpdateId(MyTestConstant.ID);
		alabao.setUpdateTime(new Date());
		alabaoDailyRateService.updateById(alabao);
	}

	@Test
	public void testUpdateByTime() {
		add();
		AlabaoDailyRate alabao = new AlabaoDailyRate();
		alabao.setId(MyTestConstant.ID);
		alabao.setRate(BigDecimal.valueOf(12.5));
		alabao.setTime(new Date());
		alabao.setUpdateId(MyTestConstant.ID);
		alabao.setUpdateTime(new Date());
		alabaoDailyRateService.updateByTime(alabao);
	}
	
	@Test
	public void testFindById() {
		add();
		Assert.notNull(alabaoDailyRateService.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindByTime() {
		add();
		Assert.notNull(alabaoDailyRateService.findByTime("2015-09-08"));
	}

}
