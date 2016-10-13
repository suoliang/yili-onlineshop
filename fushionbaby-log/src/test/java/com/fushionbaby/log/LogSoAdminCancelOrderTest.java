package com.fushionbaby.log;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogSoAdminCancelOrder;
import com.fushionbaby.log.service.LogSoAdminCancelOrderService;
/**
 * 
 * @author cyla
 *
 */
public class LogSoAdminCancelOrderTest {

	private LogSoAdminCancelOrderService service;
	
	@Before
	public void before(){
//		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
//				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
//		});
		
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		service = (LogSoAdminCancelOrderService) context.getBean("logSoAdminCancelOrderServiceImpl");
	}
	

	public void add(){
		LogSoAdminCancelOrder log = new LogSoAdminCancelOrder();
		log.setMemberId(10001L);
		log.setOrderCode("tesssssttttt");
		log.setSysCancelReason("tui huo yuan yin");
		service.add(log);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

}
