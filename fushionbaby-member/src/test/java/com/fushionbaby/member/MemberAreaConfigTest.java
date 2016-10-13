package com.fushionbaby.member;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.service.MemberAreaConfigService;
/**
 * 
 * @author King
 *
 */
public class MemberAreaConfigTest {

	private MemberAreaConfigService sas;
	
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] {
						MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
					});
		sas = (MemberAreaConfigService) ac.getBean("memberAreaConfigServiceImpl");
	}
	
	@Test
	public void testFindByParentAreaCode() {
		Assert.assertNotNull(sas.findByParentAreaCode("0"));
	}

}
