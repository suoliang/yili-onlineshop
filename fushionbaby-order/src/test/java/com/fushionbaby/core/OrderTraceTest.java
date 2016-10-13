package com.fushionbaby.core;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderCardRelation;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderCardRelationService;
import com.fushionbaby.order.service.OrderTraceService;

public class OrderTraceTest {
	private OrderTraceService orderTraceService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		orderTraceService = (OrderTraceService) context
				.getBean("orderTraceServiceImpl");

	}

	
	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		OrderTrace orderTrace=new OrderTrace();
		orderTrace.setId(1L);
		orderTrace.setMemberId(1L);
		orderTrace.setOrderCode("d");
		orderTrace.setOrderStatus("d");
		orderTrace.setOrderStatusDes("d");
		orderTraceService.add(orderTrace);
	}
	
	@Test
	public void testUpdate() {
		OrderTrace orderTrace=new OrderTrace();
		orderTrace.setMemberId(1L);
		orderTrace.setOrderCode("d");
		orderTrace.setOrderStatus("d2");
		orderTrace.setOrderStatusDes("2d");
		orderTraceService.updateByMemberIdAndOrderCode(orderTrace);
	}
	
	@Test
	public void testFindByOrderCode() {
		Assert.assertNotNull(orderTraceService.findByOrderCode("d"));
	}
	
	@Test
	public void testFindByOrderTraceId() {
		Assert.assertNotNull(orderTraceService.findByOrderTraceId(1L));
	}

	
}
