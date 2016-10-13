package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberEmail;
import com.fushionbaby.member.service.MemberEmailService;

public class MemberEmailTest {
	
	private MemberEmailService mmes;
	
	@After
	public void testDeleteById() {
		
		mmes.deleteById(MyTestConstant.ID);
	}
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
				});
		mmes = (MemberEmailService) context.getBean("memberEmailServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberEmail maMemberEmail = (MemberEmail) this.mmes.findById(MyTestConstant.ID);
		maMemberEmail.setEmail("321@qq.com");
		mmes.update(maMemberEmail);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(mmes.findById(MyTestConstant.ID));
	}

	

	
	public void add(){
		MemberEmail mme = new MemberEmail();
		mme.setId(MyTestConstant.ID);
		mme.setCreateTime(new Date());
		mme.setEmail("123@qq.com");
		mme.setMemberId(1l);
		mmes.add(mme);
	}
}
