package com.fushionbaby.push;


import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.push.model.SysmgrJpush;
import com.fushionbaby.push.service.SysmgrJpushService;

public class SysmgrJpushTest {

	@Autowired
	private SysmgrJpushService<SysmgrJpush> jpushService;
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-push-mybatis.xml" });
		jpushService = (SysmgrJpushService<SysmgrJpush>) context
				.getBean("sysmgrJpushServiceImpl");
	}                  

	public void add(){
		SysmgrJpush jpush=new SysmgrJpush();
	    jpush.setId(MyTestConstant.ID);
        jpush.setPushIsOk("y");
		jpush.setPushAlias("ios");
		jpush.setPushContent("nihao--test");
		jpush.setPushTag("shouji");
		jpush.setPushTime(new Date());
		jpush.setPushTitle("test");
		jpush.setPushUrl("www.fushionbaby.com");
		this.jpushService.add(jpush);
		
	}
	@Test
	public void testAdd(){
		this.add();
	}
	@After
	public void testDelete(){
		this.jpushService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate(){
		this.add();
		SysmgrJpush jpush=jpushService.findById(MyTestConstant.ID);
		jpush.setPushIsOk("n");
		jpush.setPushTag("shahuo");
		this.jpushService.update(jpush);
	}
	
	@Test
	public void testFindById(){
		this.add();
		Assert.assertNotNull(jpushService.findById(MyTestConstant.ID));
	}
	
	@Test
	public void testFindAll(){
		this.add();
		Assert.assertNotNull(jpushService.findAll());
	}
}
