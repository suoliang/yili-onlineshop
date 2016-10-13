package com.fushionbaby.payment;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;

public class PaymentWebZfbJsdzTest {

	private PaymentWebZfbJsdzService<PaymentWebZfbJsdz> pauss;

	@SuppressWarnings({ "resource", "unchecked" })
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-payment-mybatis.xml"
		});
		pauss = (PaymentWebZfbJsdzService<PaymentWebZfbJsdz>) ac.getBean("paymentWebZfbJsdzServiceImpl");
	}
	
	@Ignore
	public void add(){
		PaymentWebZfbJsdz paymentWebZfbJsdz = new PaymentWebZfbJsdz();
		paymentWebZfbJsdz.setId(MyTestConstant.ID);
		paymentWebZfbJsdz.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebZfbJsdz.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentWebZfbJsdz.setTradeTime("2014-08-11");
		paymentWebZfbJsdz.setCreateTime(new Date());
		paymentWebZfbJsdz.setSettleAmount("ttt");
		paymentWebZfbJsdz.setRemoteAddr("9");
		pauss.add(paymentWebZfbJsdz);
	}
	@Test
	public void testAdd() {
		add();
	}
	
	@After
	public void testDeleteById() {
		pauss.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		add();
		PaymentWebZfbJsdz paymentWebZfbJsdz = new PaymentWebZfbJsdz();
		paymentWebZfbJsdz.setId(MyTestConstant.ID);
		paymentWebZfbJsdz.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebZfbJsdz.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentWebZfbJsdz.setTradeTime("2014-08-11");
		paymentWebZfbJsdz.setCreateTime(new Date());
		paymentWebZfbJsdz.setSettleAmount("tt");
		paymentWebZfbJsdz.setRemoteAddr("9");
		pauss.updateByMemberIdAndOrderCode(paymentWebZfbJsdz);
	}


	@Test
	public void updateByOrderNumberAndMemberId() {
		add();
		PaymentWebZfbJsdz paymentWebZfbJsdz = new PaymentWebZfbJsdz();
		paymentWebZfbJsdz.setId(MyTestConstant.ID);
		paymentWebZfbJsdz.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebZfbJsdz.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentWebZfbJsdz.setTradeTime("2014-08-16");
		pauss.updateByMemberIdAndOrderCode(paymentWebZfbJsdz);
	}

	@Test
	public void queryBySoCode() {
		add();
		pauss.queryByMemberIdAndOrderCode(1L, "22");
	}

	@Test
	public void queryByOrderNumber() {
		add();
		pauss.queryByOrderNumber("00");
	}

}
