package com.fushionbaby.core;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderEpoint;
import com.fushionbaby.order.service.OrderEpointService;

public class OrderEpointTest {
	private OrderEpointService orderEpointService;
	
	@SuppressWarnings("resource")
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
			MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-epoint-mybatis.xml"
		});
		orderEpointService=(OrderEpointService) ac.getBean("orderEpointServiceImpl");
	}
	@Ignore
	public void add(){
		OrderEpoint orderEpoint = new OrderEpoint();
		orderEpoint.setId(MyTestConstant.ID);
		orderEpoint.setOrderCode(MyTestConstant.ID+"");
		orderEpoint.setSkuCode(MyTestConstant.ID+"");
		orderEpoint.setMemberId(1l);
		orderEpoint.setOrderStatus("1");
		orderEpointService.add(orderEpoint);
	}
	
	
	@Test
	public void testadd(){
		add();
		//selectById();
		//findAll();
	}
	
	@After
	public void testDeleteById() {
		orderEpointService.deleteById(MyTestConstant.ID);
	}

	
	
}
