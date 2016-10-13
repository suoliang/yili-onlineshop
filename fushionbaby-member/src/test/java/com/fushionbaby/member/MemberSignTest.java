package com.fushionbaby.member;

import java.math.BigDecimal;
import java.util.Date;











import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberSign;
import com.fushionbaby.member.service.MemberSignService;

public class MemberSignTest {

	private MemberSignService memberSignService;

	@SuppressWarnings("resource")
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
		});
		memberSignService = (MemberSignService) ac.getBean("memberSignServiceImpl");
	}
	
	@Ignore
	public void add(){
		MemberSign memberSign = new MemberSign();
		memberSign.setId(MyTestConstant.ID);
		memberSign.setGetEpoint(new BigDecimal("1.00"));
		memberSign.setMemberId(MyTestConstant.ID+"");
		memberSignService.add(memberSign);
	}
	@Test
	public void testAdd() {
		add();
	}
	
	@After
	public void testDeleteById() {
		memberSignService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testFindByMemberId() {
		add();
		List<MemberSign> memberSignList=memberSignService.findByMemberId(MyTestConstant.ID);
		System.out.println(memberSignList.size());
	}


}
