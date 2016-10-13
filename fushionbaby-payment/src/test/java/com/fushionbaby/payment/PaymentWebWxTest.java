package com.fushionbaby.payment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.service.PaymentWebWxService;
/***
 *
 * @author cyla
 *
 */
public class PaymentWebWxTest {

	private PaymentWebWxService<PaymentWebWx> pauss;

	@SuppressWarnings({"resource", "unchecked"})
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-payment-mybatis.xml"
		});
		pauss = (PaymentWebWxService<PaymentWebWx>) ac.getBean("paymentWebWxServiceImpl");
	}
	
	@Ignore
	public void add(){
		PaymentWebWx paymentWebWx = new PaymentWebWx();
		paymentWebWx.setId(MyTestConstant.ID);
		paymentWebWx.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebWx.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentWebWx.setSettleAmount("0");
		paymentWebWx.setTradeTime("2014-08-11");
		pauss.add(paymentWebWx);
	}
	@Test
	public void testAdd() {
		add();
	}

	@Test
	public void testUpdate() {
		add();
		PaymentWebWx paymentWebWx = new PaymentWebWx();
		paymentWebWx.setId(MyTestConstant.ID);
		paymentWebWx.setOrderNumber(String.valueOf(MyTestConstant.ID));
		paymentWebWx.setOrderCode(String.valueOf(MyTestConstant.ID));
		paymentWebWx.setSettleAmount("11111");
		paymentWebWx.setTradeTime("2014-08-11");
		pauss.updateByMemberIdAndOrderCode(paymentWebWx);
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
		Assert.assertNotNull(pauss.getByMemberIdAndOrderCode(1L, ""));
	}

}
