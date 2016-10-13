package com.fushionbaby.payment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.service.PaymentAppZfbService;

public class PaymentAppZfbTest {

	private PaymentAppZfbService<PaymentAppZfb> pauss;

	@SuppressWarnings({ "resource", "unchecked" })
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-payment-mybatis.xml"
		});
		pauss = (PaymentAppZfbService<PaymentAppZfb>) ac.getBean("paymentAppZfbServiceImpl");
	}
	
	@Ignore
	public void add(){
		PaymentAppZfb paymentAppZfbStatus = new PaymentAppZfb();
		paymentAppZfbStatus.setId(MyTestConstant.ID);
		paymentAppZfbStatus.setMemberId(MyTestConstant.ID);
		paymentAppZfbStatus.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentAppZfbStatus.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentAppZfbStatus.setSettleAmount("0");
		paymentAppZfbStatus.setTradeTime("2014-08-11");
		pauss.add(paymentAppZfbStatus);
	}
	@Test
	public void testAdd() {
		add();
	}

	@Test
	public void testUpdate() {
		add();
		PaymentAppZfb paymentAppZfbStatus = pauss.getById(MyTestConstant.ID);
		paymentAppZfbStatus.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentAppZfbStatus.setSettleAmount("11111");
		paymentAppZfbStatus.setTradeTime("2014-09-11");
		pauss.updateByMemberIdAndOrderCode(paymentAppZfbStatus);
		System.out.println(paymentAppZfbStatus);
	}

	@After
	public void testDeleteById() {
		pauss.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testGetById() {
		add();
		Assert.assertNotNull(pauss.getById(MyTestConstant.ID));
	}

	@Test
	public void testGetByOrderNumber() {
		add();
		Assert.assertNotNull(pauss.getByOrderNumber(String.valueOf(MyTestConstant.ID)));
	}

	@Test
	public void testGetBySoCode() {
		add();
		Assert.assertNotNull(pauss.getByMemberIdAndOrderCode(MyTestConstant.ID,String.valueOf(MyTestConstant.ID)));
	}

}
