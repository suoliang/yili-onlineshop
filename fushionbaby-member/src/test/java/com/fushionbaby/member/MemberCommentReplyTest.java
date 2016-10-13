package com.fushionbaby.member;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberSkuCommentReply;
import com.fushionbaby.member.service.MemberSkuCommentReplyService;

public class MemberCommentReplyTest {
	private MemberSkuCommentReplyService<MemberSkuCommentReply> memberCommentReplyService;
	private MemberSkuCommentReply memberCommentReply;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
		});
		memberCommentReplyService = (MemberSkuCommentReplyService) ac.getBean("memberCommentReplyServiceImpl"); 
	}
	@After
	public void testDeleteById() {
		
		memberCommentReplyService.deleteById(MyTestConstant.ID);
	}
	@Test
	public void testAdd() {
		this.add();
	}
	
	public void add(){
		memberCommentReply = new MemberSkuCommentReply();
		memberCommentReply.setId(MyTestConstant.ID);
		memberCommentReply.setCommentId(MyTestConstant.ID);
		memberCommentReply.setIpAddress("192.168.1.1");
		memberCommentReply.setIsAnonymous("n");;
		memberCommentReply.setMemberId(MyTestConstant.ID);
		memberCommentReply.setMemberName("xpf");
		memberCommentReply.setReplyContent("xpfsay");
		memberCommentReply.setSourceCode("1");
		memberCommentReply.setStatus(1);
		memberCommentReplyService.add(memberCommentReply);
	}

	@Test
	public void testUpdate() {
		this.add();
		memberCommentReply = (MemberSkuCommentReply) memberCommentReplyService.findById(MyTestConstant.ID);
		memberCommentReply.setMemberName("xpf2");;
		memberCommentReplyService.update(memberCommentReply);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(memberCommentReplyService.findById(1L));
	}
	


	@Test
	public void testGetListPage() {
		BasePagination page = new BasePagination();
		System.out.println(this.memberCommentReplyService.getListPage(page).getTotal());
	}
}
