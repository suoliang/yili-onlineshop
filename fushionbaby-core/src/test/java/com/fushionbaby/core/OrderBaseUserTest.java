package com.fushionbaby.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderBaseUser;
import com.fushionbaby.core.service.OrderBaseUserService;

public class OrderBaseUserTest {
	
private OrderBaseUserService<OrderBaseUser> orderBaseUserService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		orderBaseUserService = (OrderBaseUserService<OrderBaseUser>) context.getBean("orderBaseUserServiceImpl");
	}
	@Test
	public void testGetListPage() {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("aa");
		map.put("evaluateStatus", "1");
		List<OrderBaseUser> page = orderBaseUserService.getListPage(map);
		System.out.println(page.size());
		for (OrderBaseUser orderBaseUser : page) {
			System.out.println(orderBaseUser);
		}
		System.out.println("bb");
		
	}
	@Test
	public void testAdd(){
		OrderBaseUser orderBaseUser = new OrderBaseUser();
		orderBaseUser.setSourceCode("1");
		orderBaseUser.setOrderCode("wew");
		orderBaseUser.setMemberId(1L);
		orderBaseUser.setOrderStatus("1");
		orderBaseUserService.addOrderBase(orderBaseUser);
	}
	
	@Test
	public void testGetWaitingListByMemberId(){
		List<OrderBaseUser> list = orderBaseUserService.getWaitingListByMemberId(102L);
		System.out.println(list.size());
	}

}
