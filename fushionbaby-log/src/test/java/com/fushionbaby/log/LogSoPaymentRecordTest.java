package com.fushionbaby.log;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogSoPaymentRecord;
import com.fushionbaby.log.service.LogSoPaymentRecordService;

public class LogSoPaymentRecordTest {

	private LogSoPaymentRecordService service;
	
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
		});
		service = (LogSoPaymentRecordService) ac.getBean("logSoPaymentRecordServiceImpl");
	}
	
	@Ignore
	public void add(){
		LogSoPaymentRecord log = new LogSoPaymentRecord();
		log.setId(MyTestConstant.ID);
		log.setSoCode("123456");
		service.add(log);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

}
