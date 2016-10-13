package com.fushionbaby.log;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogBizException;
import com.fushionbaby.log.service.LogBizExceptionService;

public class LogBizExceptionTest {
	
	private LogBizExceptionService logBizExceptionService;
	
	@SuppressWarnings("resource")
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
			MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
		});
		logBizExceptionService = (LogBizExceptionService) ac.getBean("logBizExceptionServiceImpl");
	}
	
	@Test
	public void testAdd() {
		LogBizException lbe = new LogBizException();
		lbe.setId(MyTestConstant.ID);
		lbe.setName("sfdsds");
		logBizExceptionService.add(lbe);
	}

}
