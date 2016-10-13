package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;


public class MemberCommentTest {

	private MemberSkuCommentService<MemberSkuComment> memberCommentService;

	@After
	public void deleteById() {
		memberCommentService.deleteById(MyTestConstant.ID);
	}

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-member-mybatis.xml" });
		memberCommentService = (MemberSkuCommentService<MemberSkuComment>) ac
				.getBean("memberSkuCommentServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberSkuComment maComment = new MemberSkuComment();
		maComment.setCreateTime(new Date());
		maComment.setCommentContent("我不知道啊 这是什么  是的嘛");
		memberCommentService.update(maComment);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(memberCommentService.findById(MyTestConstant.ID));
	}

	private void add() {
		MemberSkuComment maComment = new MemberSkuComment();
		maComment.setId(MyTestConstant.ID);
		maComment.setScore(2);
		maComment.setMemberName("nihao");
		maComment.setSkuCode("2323");
		maComment.setCreateTime(new Date());
		maComment.setVersion(new Date());
		maComment.setDisable("y");
		maComment.setCommentContent("111111");
		
		memberCommentService.add(maComment);
	}
}
