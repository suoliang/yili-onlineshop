package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberFeedbackInfo;
import com.fushionbaby.member.service.MemberFeedbackInfoService;
/**
 * 
 * @author King suoliang
 *
 */
public class MemberFeedbackInfoTest {

	private MemberFeedbackInfoService<MemberFeedbackInfo> mfis;
	
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	@Before
	public void before(){
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
				});
		mfis = (MemberFeedbackInfoService) context.getBean("memberFeedbackInfoServiceImpl");
	}
	
	@Ignore
	public void add(){
		MemberFeedbackInfo mfi = new MemberFeedbackInfo();
		mfi.setId(MyTestConstant.ID);
		mfi.setMemberId(1L);
		mfi.setSourceCode("3");
		mfi.setAddTime(new Date());
		mfi.setContent("sfsfsd");
		mfis.add(mfi);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		mfis.deleteById(MyTestConstant.ID);
	}

}
