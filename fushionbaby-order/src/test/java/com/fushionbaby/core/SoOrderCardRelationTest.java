package com.fushionbaby.core;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderCardRelation;
import com.fushionbaby.order.service.OrderCardRelationService;

/**
 * @author cyla
 */
public class SoOrderCardRelationTest {
	private OrderCardRelationService soOrderCardRelationService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		soOrderCardRelationService = (OrderCardRelationService) context
				.getBean("soOrderCardRelationServiceImpl");

	}

	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		OrderCardRelation soOrderCardRelation = new OrderCardRelation();
		soOrderCardRelation.setAmount(new BigDecimal(1425));
		soOrderCardRelation.setCardNo("111");
		soOrderCardRelation.setMemberId(222L);
		soOrderCardRelation.setOrderCode("orderCode");
		soOrderCardRelationService.add(soOrderCardRelation);
	}
}
