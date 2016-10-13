package com.fushionbaby.statistics;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.statistics.model.StatisticsOrder;
import com.fushionbaby.statistics.service.StatisticsOrderService;
/**
 * 
 * @author cyla
 *
 */
public class StatisticsOrderTest {
	
	private StatisticsOrderService<StatisticsOrder> statisticsOrderService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-statistics-mybatis.xml"
		});
		statisticsOrderService = (StatisticsOrderService<StatisticsOrder>) ac.getBean("statisticsOrderServiceImpl");
	}
	
	@Ignore
	public void add(){
		StatisticsOrder statisticsOrder = new StatisticsOrder();
		statisticsOrder.setId(MyTestConstant.ID);
		statisticsOrder.setBuyabovetwo(1);
		statisticsOrder.setCreateTime(new Date());
		statisticsOrder.setTodayOrderNumber(2);
		statisticsOrder.setTodaySalesMoney(new BigDecimal(10));
		statisticsOrderService.add(statisticsOrder);;
	}
	
	
	@Test
	public void testAdd() {
		this.add();
	}
	
}
