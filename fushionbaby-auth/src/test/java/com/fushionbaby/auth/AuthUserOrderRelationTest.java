package com.fushionbaby.auth;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.auth.service.AuthUserOrderRelationService;
import com.fushionbaby.common.constants.MyTestConstant;
/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月28日上午10:18:12
 */
public class AuthUserOrderRelationTest {
	private AuthUserOrderRelationService<AuthUserOrderRelation> authUserOrderRelationService;
	@SuppressWarnings("unchecked")
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")	
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-auth-mybatis.xml"});
		authUserOrderRelationService =(AuthUserOrderRelationService<AuthUserOrderRelation>) context.getBean("authUserOrderRelationServiceImpl");
	}
	String testname="fushionuser";
	@Test
	public void testadd() {
		this.add();
	}

	@Test
	public void testfindall() {
	
		Assert.assertNotNull(this.authUserOrderRelationService.findAll());
	}

	public void add(){
		
		



			AuthUserOrderRelation auUser=new AuthUserOrderRelation();
			auUser.setCreateTime(new Date());
			auUser.setMemberId(6l);
			auUser.setOrderCode("7153050");
			auUser.setUpdateId(6l);
			auUser.setUpdateTime(new Date());
			auUser.setUserId(3l);
			auUser.setUserName("admin");
			authUserOrderRelationService.add(auUser);
	
		
	}
	
}
