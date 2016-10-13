package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberTelephoneService;

public class MemberTelephoneTest {
	
	private MemberTelephoneService<MemberTelephone> mmts;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
				});
		mmts = (MemberTelephoneService<MemberTelephone>) context.getBean("memberTelephoneServiceImpl");
	}
	@After
	public void testDeleteById() {
		mmts.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberTelephone mmt = mmts.findById(MyTestConstant.ID);
		mmt.setMemberId(2l);
		mmt.setTelephone("18963777324");
		mmts.update(mmt);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(mmts.findById(MyTestConstant.ID));
	}

	
	
	 public void add(){
		MemberTelephone mmt = new MemberTelephone();
		mmt.setId(MyTestConstant.ID);
		mmt.setMemberId(1l);
		mmt.setCreateTime(new Date());
		mmt.setTelephone("18963777323");
		mmts.add(mmt);
	 }

}
