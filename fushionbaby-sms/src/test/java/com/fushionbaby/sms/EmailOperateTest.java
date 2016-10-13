/*package com.fushionbaby.sms;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sms.model.EmailOperate;
import com.fushionbaby.sms.service.EmailOperateService;

public class EmailOperateTest {

	private EmailOperateService  emailOperateService;
	
	@SuppressWarnings("resource")
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sms-mybatis.xml"
			}
		);
		emailOperateService = (EmailOperateService) ac.getBean("emailOperateServiceImpl");
	}
	
	@Test
	public void testAdd() {
		EmailOperate eo = new EmailOperate();
		eo.setId(MyTestConstant.ID);
		eo.setCreateId(MyTestConstant.ID);
		eo.setCreateTime(new Date());
		eo.setEmailContent("aaa");
		eo.setEmailSubject("sddd");
		emailOperateService.add(eo);
	}

}
*/