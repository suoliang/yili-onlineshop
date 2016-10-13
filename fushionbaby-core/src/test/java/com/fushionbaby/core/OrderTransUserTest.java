package com.fushionbaby.core;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderTransUser;
import com.fushionbaby.core.service.OrderTransUserService;

/**
 * @author 张明亮
 *
 */
public class OrderTransUserTest {
	
	private OrderTransUserService orderTransUserService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		orderTransUserService = (OrderTransUserService) context.getBean("orderTransUserServiceImpl");
	}
	
	
	@Test
	public void testAdd(){
		this.add();
	}
	public void add(){
		OrderTransUser orderTransUser = new OrderTransUser();
		orderTransUser.setId(MyTestConstant.ID);
		orderTransUser.setMemberId(MyTestConstant.ID);
		orderTransUser.setOrderCode("d");
		orderTransUser.setMemberId(1L);
		orderTransUser.setOrderCode("d");
		orderTransUser.setSendDate("k");
		orderTransUser.setTransName("d");
		orderTransUser.setTransStatus("n");
		orderTransUser.setUpdateId(1L);
		orderTransUserService.addOrderTransUser(orderTransUser);
	}
}
