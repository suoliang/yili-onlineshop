package com.fushionbaby.auth;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.common.constants.MyTestConstant;

/**
 * @author xupeijun
 * @version
 */
public class AuthUserTest {
	private AuthUserService auUserService;
	@After
	public void testdeletebyid() {
		auUserService.deleteById(MyTestConstant.ID);
	}
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-auth-mybatis.xml"});
		auUserService =(AuthUserService) context.getBean("authUserServiceImpl");
	}
	String testname="fushionuser";
	@Test
	public void testadd() {
		this.add();
	}
	@Test
	public void testupdate() {
		this.add();
		AuthUser auUser = (AuthUser) auUserService.findById(MyTestConstant.ID);
		auUser.setEmail("sdgsdg");
		auUserService.update(auUser);
	}
	@Test
	public void testfindbyid() {
		this.add();
		System.out.println(this.auUserService.findById(MyTestConstant.ID));
	Assert.assertNotNull(auUserService.findById(MyTestConstant.ID));
		
	}
	@Test
	public void testfindall() {
	
		Assert.assertNotNull(this.auUserService.findAll());
	}
	@Test
	public void testisSystemUser() {
		this.add();
		this.auUserService.isSystemUser(MyTestConstant.ID);
	}
	
	public void add(){
		AuthUser auUser=new AuthUser();
		auUser.setId(MyTestConstant.ID);
		auUser.setLoginName(testname);
		auUser.setPassword("user123");
		auUser.setEmail("123456@qq.com");
		auUser.setPhone("12345678900");
		auUser.setUserType(2);
		auUserService.add(auUser);
	}
	
}
