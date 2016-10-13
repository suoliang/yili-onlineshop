package com.fushionbaby.log;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogSoAdminCancelOrder;
import com.fushionbaby.log.service.LogSoSysCancelOrderService;
/**
 * 
 * @author cyla
 *
 */
public class LogSoSysCancelOrderTest {

	private LogSoSysCancelOrderService service;
	
	@Before
	public void before(){
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
//				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
//		});
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		service = (LogSoSysCancelOrderService) context.getBean("logSoSysCancelOrderServiceImpl");
	}
	

	public void add(){
		LogSoAdminCancelOrder log = new LogSoAdminCancelOrder();
		log.setMemberId(10001L);
		log.setOrderCode("tesssssttttt");
		service.add(log);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

}
