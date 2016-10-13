package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.service.MemberExtraInfoService;

public class MemberExtraInfoTest {
	private MemberExtraInfoService<MemberExtraInfo> mmis;
	@SuppressWarnings("unchecked")
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
				});
		mmis = (MemberExtraInfoService<MemberExtraInfo>) context.getBean("memberExtraInfoServiceImpl");
	}
	@After
	public void testDeleteById() {
		mmis.deleteById(MyTestConstant.ID);
	}
	@Test
	public void testAdd() {
	     this.add();
	}


	@Test
	public void testUpdate() {
		this.add();
		MemberExtraInfo mmi = mmis.findById(MyTestConstant.ID);
		mmi.setBabyBirthday(new Date());
		mmi.setNickname("hello beauty");
		mmis.update(mmi);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(this.mmis.findById(MyTestConstant.ID));
	}

	

	public void add(){
		MemberExtraInfo mmi = new MemberExtraInfo();
		mmi.setId(MyTestConstant.ID);
		mmi.setMemberId(1l);
		mmi.setBabyFm(1);
		mmi.setBabyBirthday(new Date());
		mmi.setNickname("hello world");
		mmi.setBabyGender(0);
		mmis.add(mmi);
	}
}
