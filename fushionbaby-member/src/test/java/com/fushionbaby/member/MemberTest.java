package com.fushionbaby.member;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
/**
 * 
 * @author King
 *
 */
public class MemberTest {
	
	private MemberService<Member> maMemberService;
	private Member maMember;
	
	@SuppressWarnings({ "unchecked", "resource" })
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
		});
		maMemberService = (MemberService<Member>) ac.getBean("memberServiceImpl"); 
	}

	@Test
	public void testAdd() {
		this.add();
	}
	
	public void add(){
		maMember = new Member();
		maMember.setId(MyTestConstant.ID);
		maMember.setLoginName(String.valueOf(MyTestConstant.ID));
		maMember.setPassword(String.valueOf(MyTestConstant.ID));
		maMember.setEpoints(BigDecimal.valueOf(12));
		maMember.setTelephone("12121");
		maMember.setVersion(new Date());
		maMember.setCreateTime(new Date());
		maMemberService.add(maMember);
	}

	@Test
	public void testUpdate() {
		this.add();
		maMember = (Member) maMemberService.findById(MyTestConstant.ID);
		maMember.setVersion(new Date());
		maMember.setEpoints(BigDecimal.valueOf(0));
		
		System.out.println(new Integer(0));
		maMember.setTelephone("0");
		maMember.setCreateTime(new Date());
		maMemberService.update(maMember);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(maMemberService.findById(MyTestConstant.ID));
	}
	
	@Test
	public void testLoginByLoginNamePassword(){
		this.add();
		Assert.assertNotNull(maMemberService.loginByLoginNamePassword(String.valueOf(MyTestConstant.ID), String.valueOf(MyTestConstant.ID)));
	}

	@Test
	public void testGetCountByCreateTime(){
		Assert.assertNotNull(maMemberService.findByCreateTime(new Date()));
		Assert.assertNotNull(maMemberService.findByCreateTime(null));
	}
	
	@After
	public void testDeleteById() {
		maMemberService.deleteById(MyTestConstant.ID);
	}
}
