package com.aladingshop.finance;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.finance.model.FinanceRefundRecord;
import com.aladingshop.finance.service.FinanceRefundRecordService;
import com.fushionbaby.common.constants.MyTestConstant;

public class FinanceRefundRecordTest {
	
@Autowired
private FinanceRefundRecordService financeRefundRecordService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		financeRefundRecordService = (FinanceRefundRecordService) context.getBean("financeRefundRecordServiceImpl");
	}


	@Test
	public void testAdd() {
		for (int i = 0; i < 12; i++) 
		this.add();
	}

	@Test
	public void testFindAll(){
		System.out.println(this.financeRefundRecordService.findAll().size());
	}
	

	public void add() {
		FinanceRefundRecord f = new FinanceRefundRecord();
		f.setMemberId(200l);
		f.setOrderCode("123456");
		f.setOrderSource("1");
		f.setOrderPayType("1");
		f.setCompleteTime(new Date());
		f.setOrderAmount(new BigDecimal(100));
		this.financeRefundRecordService.add(f);
	}
}
