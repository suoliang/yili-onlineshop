package com.fushionbaby.payment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.service.PaymentAppUnionService;

public class PaymentAppUnionTest {

	private PaymentAppUnionService<PaymentAppUnion> pauss;

	@SuppressWarnings({ "unchecked", "resource" })
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-payment-mybatis.xml"
		});
		pauss = (PaymentAppUnionService<PaymentAppUnion>) ac.getBean("paymentAppUnionServiceImpl");
	}
	
	@Ignore
	public void add(){
		PaymentAppUnion paymentAppUnionStatus = new PaymentAppUnion();
		paymentAppUnionStatus.setId(MyTestConstant.ID);
		paymentAppUnionStatus.setMemberId(MyTestConstant.ID);
		paymentAppUnionStatus.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentAppUnionStatus.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentAppUnionStatus.setSettleAmount("0");
		paymentAppUnionStatus.setTradeTime("2014-08-11");
		pauss.add(paymentAppUnionStatus);
	}
	@Test
	public void testAdd() {
		add();
	}

	@Test
	public void testUpdate() {
		add();
		PaymentAppUnion paymentAppUnionStatus = new PaymentAppUnion();
		paymentAppUnionStatus.setId(MyTestConstant.ID);
		paymentAppUnionStatus.setMemberId(MyTestConstant.ID);
		paymentAppUnionStatus.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentAppUnionStatus.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentAppUnionStatus.setOrderDes("sfsdfsdfd");
		paymentAppUnionStatus.setTradeTime("2014-08-11");
		pauss.updateByMemberIdAndOrderCode(paymentAppUnionStatus);
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
	public void testGetByMemberIdAndOrderCode() {
		add();
		Assert.assertNotNull(pauss.getByMemberIdAndOrderCode(MyTestConstant.ID, String.valueOf(MyTestConstant.ID)));
	}

}
