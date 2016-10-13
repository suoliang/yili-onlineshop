package com.fushionbaby.core;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderTraceUser;
import com.fushionbaby.core.service.OrderTraceUserService;

/**
 * @author 张明亮
 *
 */
public class OrderTraceUserTest {
	
	private OrderTraceUserService orderTraceUserService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		orderTraceUserService = (OrderTraceUserService) context.getBean("orderTraceUserServiceImpl");
	}
	
	
	@Test
	public void testAdd(){
		this.add();
	}
	public void add(){
		OrderTraceUser orderTraceUser = new OrderTraceUser();
		orderTraceUser.setId(MyTestConstant.ID);
		orderTraceUser.setMemberId(MyTestConstant.ID);
		orderTraceUser.setOrderCode("d");
		orderTraceUser.setOrderStatus("y");
		orderTraceUser.setOrderStatusDes("d");
		orderTraceUserService.addOrderTraceUser(orderTraceUser);
	}
}
