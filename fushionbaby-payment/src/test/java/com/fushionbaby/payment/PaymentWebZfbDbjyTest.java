package com.fushionbaby.payment;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.model.PaymentWebZfbDbjy;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.fushionbaby.payment.service.PaymentWebZfbDbjyService;

public class PaymentWebZfbDbjyTest {

	private PaymentWebZfbDbjyService pauss;

	@SuppressWarnings("resource")
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-payment-mybatis.xml"
		});
		pauss = (PaymentWebZfbDbjyService) ac.getBean("paymentWebZfbDbjyServiceImpl");
	}
	
	@Ignore
	public void add(){
		PaymentWebZfbDbjy paymentWebZfbDbjy = new PaymentWebZfbDbjy();
		paymentWebZfbDbjy.setId(MyTestConstant.ID);
		paymentWebZfbDbjy.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebZfbDbjy.setSoCode(String.valueOf(MyTestConstant.ID));
		paymentWebZfbDbjy.setLogisticsFee("3");
		paymentWebZfbDbjy.setLogisticsPayment("2");
		paymentWebZfbDbjy.setTradeTime("2014-08-11");
		paymentWebZfbDbjy.setNoFreightAmount("8");
		paymentWebZfbDbjy.setCreatetime(new Date());
		paymentWebZfbDbjy.setTotalAmount("ttt");
		paymentWebZfbDbjy.setRemoteAddr("9");
		pauss.add(paymentWebZfbDbjy);
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
		PaymentWebZfbDbjy paymentWebZfbDbjy = new PaymentWebZfbDbjy();
		paymentWebZfbDbjy.setId(MyTestConstant.ID);
		paymentWebZfbDbjy.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebZfbDbjy.setSoCode(String.valueOf(MyTestConstant.ID));
		paymentWebZfbDbjy.setLogisticsFee("11111");
		paymentWebZfbDbjy.setTradeTime("2014-08-11");
		pauss.update(paymentWebZfbDbjy);
	}


	@Test
	public void updateByOrderNumberAndMemberId() {
		add();
		PaymentWebZfbDbjy paymentWebZfbDbjy = new PaymentWebZfbDbjy();
		paymentWebZfbDbjy.setId(MyTestConstant.ID);
		paymentWebZfbDbjy.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebZfbDbjy.setSoCode(String.valueOf(MyTestConstant.ID));
		paymentWebZfbDbjy.setLogisticsFee("111121");
		paymentWebZfbDbjy.setTradeTime("2014-08-11");
		pauss.updateByOrderNumberAndMemberId(paymentWebZfbDbjy);;
	}

	@Test
	public void queryBySoCode() {
		add();
		pauss.queryBySoCode("22");
	}

	@Test
	public void queryByOrderNumber() {
		add();
		pauss.queryByOrderNumber("00");
	}

}
