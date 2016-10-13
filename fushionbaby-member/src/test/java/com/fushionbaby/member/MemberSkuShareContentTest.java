/**
 * 
 */
package com.fushionbaby.member;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.member.service.MemberSkuShareContentService;

/**
 * @author cyla
 * 
 */
public class MemberSkuShareContentTest {

	@Autowired
	private MemberSkuShareContentService skuShareContentService;
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-member-mybatis.xml" });
		skuShareContentService = (MemberSkuShareContentService) ac.getBean("memberSkuShareContentServiceImpl");
	}

	public void add() {
		MemberSkuShareContent shareContent=new MemberSkuShareContent();
		shareContent.setId(MyTestConstant.ID);
		shareContent.setMemberId(MyTestConstant.ID);
		shareContent.setShareContent("d");
		shareContent.setShareUrl("d");
		shareContent.setSourceCode("d");
		shareContent.setSkuCode("d");
		skuShareContentService.add(shareContent);
	}
	
	@Test
	public void testAdd(){
		add();
	}
	
	@Test
	public void testDelete(){
		skuShareContentService.deleteById(MyTestConstant.ID);
	}
	
}
