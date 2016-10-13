package com.fushionbaby.member;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.VerificationRecordService;
/**
 * 
 * @author King
 *
 */
public class verificationRecordTest {
	
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	
	@SuppressWarnings({ "unchecked", "resource" })
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-*-mybatis.xml"
		});
		verificationRecordService = (VerificationRecordService<VerificationRecord>) ac.getBean("verificationRecordServiceImpl"); 
	}

	@Test
	public void testAdd() {
		this.add();
	}
	
	public void add(){
		//for (int i = 0; i < 15; i++) {
		int i=100;
		
			VerificationRecord record=new VerificationRecord();
			record.setIdentityCardNo("123456"+i);
			record.setBankCardNo("654321"+i);
			record.setResponseStatus("10"+i);
			record.setTrueName("%E6%9D%9C%E4%B9%A0%E8%99%8E"+i);
			verificationRecordService.add(record);
		//}
		
		
	}
	@Test
	public void findByBankCardNoAndID(){

		System.out.println(verificationRecordService.findByBankCardNoAndID("", "123456", "").size());
	}



}
