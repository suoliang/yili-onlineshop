package com.fushionbaby.sms;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;
import com.fushionbaby.sms.service.impl.SmsServiceImpl;
/**
 * 
 * @author King
 *
 */
public class SmsTest {

	private SmsService smsService;
	
	
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sms-mybatis.xml"
		});
		smsService = (SmsService) ac.getBean("smsServiceImpl");
	}
	
	public void add(){
		Sms sms = new Sms();
		sms.setId(MyTestConstant.ID);
		smsService.add(sms);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		smsService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testUpdate(){
		this.add();
		Sms sms = (Sms) smsService.findById(MyTestConstant.ID);
		sms.setMemberName("wobuzhdiao");;
		smsService.update(sms);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(smsService.findById(MyTestConstant.ID));
	}
	

	@Test
	public void testFindByCreateTime(){	
		smsService.findByCreateTime(new Date());
	}

	

}
