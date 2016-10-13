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
import com.fushionbaby.order.model.OrderFee;
import com.fushionbaby.order.service.OrderCardRelationService;
import com.fushionbaby.order.service.OrderFeeService;

public class OrderFeeTest {
	private OrderFeeService orderFeeService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		orderFeeService = (OrderFeeService) context
				.getBean("orderFeeServiceImpl");

	}

	
	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		OrderFee orderFee=new OrderFee();
		orderFee.setId(1L);
		orderFee.setMemberId(1L);
		orderFee.setOrderCode("d");
		orderFee.setActualTransferFee(new BigDecimal(1));
		orderFee.setCardAmount(new BigDecimal(1));
		orderFee.setCardno("d");
		orderFee.setEpointsRedeemMoney(new BigDecimal(1));
		orderFee.setPayableTransferFee(new BigDecimal(1));
		orderFee.setTaxPrice(new BigDecimal(1));
		orderFee.setTotalActual(new BigDecimal(1));
		orderFee.setTotalAfDiscount(new BigDecimal(1));
		orderFee.setTotalBfDiscount(new BigDecimal(1));
		orderFee.setUseWalletMoney(new BigDecimal(1));
		orderFeeService.add(orderFee);
	}
	
	
	
	@Test
	public void testFindByOrderCode() {
		Assert.assertNotNull(orderFeeService.findByOrderCode("d"));
	}
	
	@Test
	public void testFindByOrderFeeId() {
		Assert.assertNotNull(orderFeeService.findByOrderFeeId(1L));
	}

	
}
