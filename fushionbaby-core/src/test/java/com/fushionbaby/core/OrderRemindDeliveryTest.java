package com.fushionbaby.core;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderRemindDelivery;
import com.fushionbaby.core.service.OrderRemindDeliveryService;

public class OrderRemindDeliveryTest {
	private OrderRemindDeliveryService orderRemindDeliveryService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		orderRemindDeliveryService = (OrderRemindDeliveryService) context
				.getBean("orderRemindDeliveryServiceImpl");

	}

	
	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		OrderRemindDelivery orderRemindDelivery=new OrderRemindDelivery();
		orderRemindDelivery.setMemberId(1L);
		orderRemindDelivery.setOrderCode("d");
		orderRemindDelivery.setMemberName("d");
		orderRemindDelivery.setCount(1);
		orderRemindDeliveryService.add(orderRemindDelivery);
	}
	
	@Test
	public void testUpdate() {
		OrderRemindDelivery orderRemindDelivery=new OrderRemindDelivery();
		orderRemindDelivery.setMemberId(1L);
		orderRemindDelivery.setOrderCode("d2");
		orderRemindDelivery.setCount(3);
		orderRemindDelivery.setUpdateTime(new Date());
		orderRemindDeliveryService.updateByMemberIdAndOrderCode(orderRemindDelivery);
	}
	

	@Test
	public void testFindByMemberIdAndOrderCode() {
		Assert.assertNotNull(orderRemindDeliveryService.findByMemberIdAndOrderCode(1L,"d"));
	}
}
