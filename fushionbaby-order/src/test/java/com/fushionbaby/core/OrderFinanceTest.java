package com.fushionbaby.core;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.service.OrderFinanceService;

public class OrderFinanceTest {
	private OrderFinanceService<OrderFinance> orderFinanceService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		orderFinanceService = (OrderFinanceService<OrderFinance>) context
				.getBean("orderFinanceServiceImpl");

	}



	@Test
	public void testUpdate() {
		OrderFinance orderFinance=new OrderFinance();
		orderFinance.setMemberId(80L);
		orderFinance.setOrderCode("5287775");
		orderFinance.setFinanceStatus("y");;
		orderFinance.setPaymentCompleteTime(new Date());;
		orderFinance.setPaymentType("d");
		orderFinance.setUpdateTime(new Date());
		orderFinanceService.updateByMemberIdAndOrderCode(orderFinance);
	}
	
	@Test
	public void testFindByOrderCode() {
		Assert.assertNotNull(orderFinanceService.findByOrderCode("5287775"));
	}
	
	@Test
	public void testFindByOrderFinanceId() {
		Assert.assertNotNull(orderFinanceService.findByOrderFinanceId(334L));
	}

	@Test
	public void testFindByMemberIdAndOrderCode() {
		Assert.assertNotNull(orderFinanceService.findByMemberIdAndOrderCode(80L,"5287775"));
	}
	
	@Test
	public void testFindByfindListByFinanceStatus() {
		Assert.assertNotNull(orderFinanceService.findListByFinanceStatus("y"));
	}
	
	@Test
	public void testFindByfindListByPaymentType() {
		Assert.assertNotNull(orderFinanceService.findListByPaymentType("y"));
	}
	
	@Test
	public void testGetOneDayCountByCreateTime() {
		Assert.assertNotNull(orderFinanceService.getOneDayCountByCreateTime("2015-01-04 01:10:10"));
	}
	
	@Test
	public void testGetBuyOverOneNumberByCreateTime() {
		//Assert.assertNotNull(orderFinanceService.findListByPaymentType("y"));
		Integer a = orderFinanceService.getBuyOverOneNumberByCreateTime("2015-01-07");
		System.out.println(a);
	}
}
