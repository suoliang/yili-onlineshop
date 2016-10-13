package com.fushionbaby.sms;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.service.EmailService;
/**
 * 
 * @author cyla
 *
 */
public class EmailTest {
	
	private EmailService<Email> emailService;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sms-mybatis.xml"
		});
		emailService = (EmailService) ac.getBean("emailServiceImpl");
	}
	
	@Ignore
	public void add(){
		Email email = new Email();
		email.setId(MyTestConstant.ID);
		email.setEmailContent("tttttttttContent");
		email.setEmailType(1);
		email.setMemberName("tttttMemberName");
		email.setReceiverEmail("ttttttReceiverEamil");
		email.setSourceCode("2");
		
		emailService.add(email);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}
	
	@After
	public void testDeleteById(){
		emailService.deleteById(MyTestConstant.ID);
	}
	
}
