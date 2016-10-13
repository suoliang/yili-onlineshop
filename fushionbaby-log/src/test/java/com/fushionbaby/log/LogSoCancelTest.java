package com.fushionbaby.log;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogSoCancelOrder;
import com.fushionbaby.log.service.LogSoCancelOrderService;
/**
 * 
 * @author glc
 *
 */
public class LogSoCancelTest {

	private LogSoCancelOrderService service;
	
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
		});
		service = (LogSoCancelOrderService) ac.getBean("logSoCancelOrderServiceImpl");
	}
	
	@Ignore
	public void add(){
		LogSoCancelOrder log = new LogSoCancelOrder();
		log.setId(MyTestConstant.ID);
		log.setCancelReason("aaaaaaaaaaaaa");
		log.setSendStatus(1);
		service.add(log);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		service.deleteById(MyTestConstant.ID);
		System.out.println("*********************");
	}

}
