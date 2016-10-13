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
import com.fushionbaby.member.model.MemberGradeConfig;
import com.fushionbaby.member.service.MemberEmailService;
import com.fushionbaby.member.service.MemberGradeConfigService;
/**
 * 
 * @author cyla
 *
 */
public class MemberGradeConfigTest {
	
	private MemberGradeConfigService memberGradeService;
	
	@After
	public void testDeleteById() {
		
		memberGradeService.deleteById(MyTestConstant.ID);
	}
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
				});
		memberGradeService = context.getBean(MemberGradeConfigService.class);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberGradeConfig memberGrade = this.memberGradeService.findById(MyTestConstant.ID);
		memberGrade.setGradeCode("ttt");
		memberGradeService.update(memberGrade);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(memberGradeService.findById(MyTestConstant.ID));
	}
	
	@Test
	public void testFindByGradeCode() {
		this.add();
		Assert.assertNotNull(memberGradeService.findByGradeCode("set"));
	}

	@Test
	public void testFindAll() {
		Assert.assertNotNull(memberGradeService.findAll());
	}

	
	public void add(){
		MemberGradeConfig memberGrade=new MemberGradeConfig();
		memberGrade.setGradeCode("set");
		memberGrade.setGradeIcon("ttt");
		memberGrade.setGradeName("ttttt");
		memberGrade.setGradeValue("tttttttttt");
		memberGrade.setRemark("remark");
		memberGrade.setId(MyTestConstant.ID);
		memberGradeService.add(memberGrade);
	}
}
