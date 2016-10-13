package com.fushionbaby.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderLineUserService;

/**
 * @author 张明亮
 *
 */
public class SoSoLineUserTest {
	
	private OrderLineUserService<OrderLineUser> SoSoLineService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		SoSoLineService = (OrderLineUserService<OrderLineUser>) context.getBean("soSoLineUserServiceImpl");
	}
	
	//@After
	public void testDeleteById(){
		SoSoLineService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testAdd(){
		this.add();
	}
	public void add(){
		OrderLineUser soSoLine = new OrderLineUser();
		soSoLine.setId(MyTestConstant.ID);
		SoSoLineService.add(soSoLine);
	}
}
