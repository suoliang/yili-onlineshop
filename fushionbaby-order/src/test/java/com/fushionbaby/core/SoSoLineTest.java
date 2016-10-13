package com.fushionbaby.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.service.OrderLineService;

/**
 * @author 张明亮
 *
 */
public class SoSoLineTest {
	
	private OrderLineService<OrderLine> SoSoLineService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		SoSoLineService = (OrderLineService<OrderLine>) context.getBean("soSoLineServiceImpl");
	}
	
	@After
	public void testDeleteById(){
		SoSoLineService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testAdd(){
		this.add();
	}
	public void add(){
		OrderLine soSoLine = new OrderLine();
		soSoLine.setId(MyTestConstant.ID);
		SoSoLineService.add(soSoLine);
	}
}
