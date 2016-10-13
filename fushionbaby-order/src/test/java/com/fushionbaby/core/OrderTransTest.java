package com.fushionbaby.core;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderTrans;
import com.fushionbaby.order.service.OrderTransService;

public class OrderTransTest {
	private OrderTransService orderTransService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		orderTransService = (OrderTransService) context
				.getBean("orderTransServiceImpl");

	}

	
	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		OrderTrans orderTrans=new OrderTrans();
		orderTrans.setMemberId(1L);
		orderTrans.setOrderCode("d");
		orderTrans.setSendDate("199101");
		orderTrans.setTransCode("d");
		orderTrans.setTransName("d");
		orderTrans.setTransStatus("y");
		orderTrans.setUpdateId(1l);
		orderTrans.setUpdateTime(new Date());
		orderTransService.add(orderTrans);
	}
	
	@Test
	public void testUpdate() {
		OrderTrans orderTrans=new OrderTrans();
		orderTrans.setMemberId(1L);
		orderTrans.setOrderCode("d");
		orderTrans.setSendDate("19910101");
		orderTrans.setTransCode("2d");
		orderTrans.setTransName("2d");
		orderTrans.setTransStatus("n");
		orderTrans.setUpdateId(1l);
		orderTrans.setUpdateTime(new Date());
		orderTransService.updateByMemberIdAndOrderCode(orderTrans);
	}
	
	@Test
	public void testFindByOrderCode() {
		Assert.assertNotNull(orderTransService.findByOrderCode("d"));
	}
	
	@Test
	public void testFindByOrderTransId() {
		Assert.assertNotNull(orderTransService.findByOrderTransId(2330L));
	}

	@Test
	public void testFindByMemberIdAndOrderCode() {
		Assert.assertNotNull(orderTransService.findByMemberIdAndOrderCode(80L,"5287775"));
	}
}
