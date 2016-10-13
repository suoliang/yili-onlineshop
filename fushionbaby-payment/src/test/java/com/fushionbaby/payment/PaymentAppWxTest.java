package com.fushionbaby.payment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.service.PaymentAppWxService;

public class PaymentAppWxTest {

	private PaymentAppWxService<PaymentAppWx> pauss;

	@SuppressWarnings({ "resource", "unchecked" })
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-payment-mybatis.xml"
		});
		pauss = (PaymentAppWxService<PaymentAppWx>) ac.getBean("paymentAppWxServiceImpl");
	}
	
	@Ignore
	public void add(){
		PaymentAppWx paymentAppWxStatus = new PaymentAppWx();
		paymentAppWxStatus.setId(MyTestConstant.ID);
		paymentAppWxStatus.setMemberId(MyTestConstant.ID);
		paymentAppWxStatus.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentAppWxStatus.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentAppWxStatus.setSettleAmount("0");
		paymentAppWxStatus.setTradeTime("2014-08-11");
		pauss.add(paymentAppWxStatus);
	}
	@Test
	public void testAdd() {
		add();
	}

	@Test
	public void testUpdate() {
		add();
		PaymentAppWx paymentAppWxStatus = new PaymentAppWx();
		paymentAppWxStatus.setId(MyTestConstant.ID);
		paymentAppWxStatus.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentAppWxStatus.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentAppWxStatus.setMemberId(MyTestConstant.ID);
		paymentAppWxStatus.setOrderDes("sdfsddsf");
		paymentAppWxStatus.setTradeTime("2014-08-11");
		pauss.updateByMemberIdAndOrderCode(paymentAppWxStatus);
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
