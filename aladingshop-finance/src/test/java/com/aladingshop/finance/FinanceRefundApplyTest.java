package com.aladingshop.finance;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.fushionbaby.common.constants.MyTestConstant;

public class FinanceRefundApplyTest {
	
@Autowired
private FinanceRefundApplyService financeRefundApplyService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		financeRefundApplyService = (FinanceRefundApplyService) context.getBean("financeRefundApplyServiceImpl");
	}


	@Test
	public void testAdd() {
		for (int i = 0; i < 12; i++) 
		this.add();
	}

	@Test
	public void testFindAll(){
		System.out.println(this.financeRefundApplyService.findAll().size());
	}
	
	@Test
	public void testUpdate(){
		FinanceRefundApply f = new FinanceRefundApply();
		f.setUpdateTime(new Date());
		f.setCreateTime(new Date());
		f.setMemberId(200l);
		f.setOrderCode("123456");
		f.setOrderSource("1");
		f.setRefundReason("just test");
		f.setStatus("1");
		f.setOrderPayType("1");
		this.financeRefundApplyService.updateByMemberIdAndOrderCode(f);
	}

	public void add() {
		FinanceRefundApply f = new FinanceRefundApply();
		f.setUpdateTime(new Date());
		f.setCreateTime(new Date());
		f.setMemberId(200l);
		f.setOrderCode("123456");
		f.setOrderSource("1");
		f.setRefundReason("just test");
		f.setStatus("1");
		f.setOrderPayType("1");
		this.financeRefundApplyService.add(f);
	}
}
