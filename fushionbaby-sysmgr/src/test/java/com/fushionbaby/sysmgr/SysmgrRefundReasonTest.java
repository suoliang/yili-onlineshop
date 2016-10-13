package com.fushionbaby.sysmgr;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.sysmgr.service.SysmgrRefundReasonService;
/**
 * 
 * @author cyla
 *
 */
public class SysmgrRefundReasonTest {
	SysmgrRefundReasonService srr;
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { "classpath:conf/spring-sysmgr-test.xml",
						"classpath:conf/spring-sysmgr-mybatis.xml" });
		srr = (SysmgrRefundReasonService) ac
				.getBean("sysmgrRefundReasonServiceImpl");
	}
	@Test
	public void findAll() {
		srr.findAll();
	}
}
