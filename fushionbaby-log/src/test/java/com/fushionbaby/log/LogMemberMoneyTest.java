package com.fushionbaby.log;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogMemberMoney;
import com.fushionbaby.log.service.LogMemberMoneyService;

/**
 * @author 张明亮
 */
public class LogMemberMoneyTest {

	private LogMemberMoneyService logMemberMoneyService;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		logMemberMoneyService = (LogMemberMoneyService) ac.getBean("logMemberMoneyServiceImpl");
	}
	
	@Ignore
	public void add(){
		LogMemberMoney log = new LogMemberMoney();
		log.setId(MyTestConstant.ID);
		log.setMemberId(MyTestConstant.ID);
		log.setAmount(new BigDecimal(0));
		log.setCurrentAmount(new BigDecimal(0));
		log.setTransType(1);
		logMemberMoneyService.add(log);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		logMemberMoneyService.deleteById(MyTestConstant.ID);
	}
}
