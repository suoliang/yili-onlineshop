package com.fushionbaby.log;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogOrderPayment;
import com.fushionbaby.log.service.LogOrderPaymentService;
/**
 * 
 * @author King suoliang
 *
 */
public class LogOrderPaymentTest {

	private LogOrderPaymentService lmls;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
		});
		lmls = (LogOrderPaymentService) ac.getBean("logOrderPaymentServiceImpl");
	}
	
	@Ignore
	public void add(){
		LogOrderPayment lml = new LogOrderPayment();
		lml.setAmount(12.2f);
		lml.setId(MyTestConstant.ID);
		lml.setMemberId(MyTestConstant.ID);
		lml.setOrderCode("d");
		lml.setPaymentType("d");
		lmls.add(lml);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	
}
