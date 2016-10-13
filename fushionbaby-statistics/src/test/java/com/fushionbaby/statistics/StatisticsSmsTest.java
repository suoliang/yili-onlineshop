package com.fushionbaby.statistics;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.statistics.model.StatisticsSms;
import com.fushionbaby.statistics.service.StatisticsSmsService;
/**
 * 
 * @author King
 *
 */
public class StatisticsSmsTest {
	
	private StatisticsSmsService<StatisticsSms> statisticsSmsService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-statistics-mybatis.xml"
		});
		statisticsSmsService = (StatisticsSmsService<StatisticsSms>) ac.getBean("statisticsSmsServiceImpl");
	}
	
	@Ignore
	public void add(){
		StatisticsSms statisticsSms = new StatisticsSms();
		statisticsSms.setId(MyTestConstant.ID);
		BigDecimal bd = new BigDecimal("234.568");
		statisticsSms.setLeftAmount(bd);
		statisticsSms.setCreateTime(new Date());
		statisticsSmsService.add(statisticsSms);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}
	
}
