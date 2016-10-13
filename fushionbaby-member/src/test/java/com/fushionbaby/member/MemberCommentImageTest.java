package com.fushionbaby.member;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberSkuCommentImage;
import com.fushionbaby.member.service.MemberSkuCommentImageService;

public class MemberCommentImageTest {
	private MemberSkuCommentImageService<MemberSkuCommentImage> memberCommentImageService;
	private MemberSkuCommentImage memberCommentImage;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
		});
		memberCommentImageService = (MemberSkuCommentImageService) ac.getBean("memberCommentImageServiceImpl"); 
	}
	
	@Test
	public void testAdd() {
		this.add();
	}
	
	public void add(){
		memberCommentImage = new MemberSkuCommentImage();
		memberCommentImage.setId(MyTestConstant.ID);
		memberCommentImage.setCommentId(MyTestConstant.ID);
		memberCommentImage.setSourceCode(1);
		memberCommentImage.setImgUrl("xpf");
		memberCommentImage.setIndex(1);
		memberCommentImageService.add(memberCommentImage);
	}

	@Test
	public void testUpdate() {
		memberCommentImage = (MemberSkuCommentImage) memberCommentImageService.findById(1L);
		memberCommentImage.setImgUrl("xpf2");;
		memberCommentImageService.update(memberCommentImage);
	}

	@Test
	public void testDeleteById() {
		
		memberCommentImageService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testFindById() {
		Assert.assertNotNull(memberCommentImageService.findById(1L));
	}
	

	@Test
	public void testFindAll() {
		Assert.assertNotNull(memberCommentImageService.findAll());
	}
	
	@Test
	public void testGetListPage() {
		BasePagination page = new BasePagination();
		System.out.println(this.memberCommentImageService.getListPage(page).getTotal());
	}
	
	@Test
	public void testGetByCommentId() {
		System.out.println(this.memberCommentImageService.findByCommentId(MyTestConstant.ID));
	}
}
