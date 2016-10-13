package com.fushionbaby.auth;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.auth.model.AuthUserRole;
import com.fushionbaby.auth.service.AuthUserRoleService;
import com.fushionbaby.common.constants.MyTestConstant;

public class AuthUserRoleTest {

	private AuthUserRoleService<AuthUserRole> authUserRoleService;

	/*
	 * @After public void testdeletebyid() {
	 * authUserRoleService.deleteById(MyTestConstant.ID); }
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-auth-mybatis.xml" });
		authUserRoleService = (AuthUserRoleService) context
				.getBean("authUserRoleServiceImpl");
	}

	String testname = "fushionuser";

	@Test
	public void testadd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		this.authUserRoleService.deleteById(MyTestConstant.ID);
	}
	/*
	 * @Test public void testupdate() { this.add(); AuthUserRole auUserRole =
	 * (AuthUserRole) authUserRoleService.findById(MyTestConstant.ID);
	 * auUserRole.setVersion(new Date());
	 * authUserRoleService.update(auUserRole); }
	 */

	/*
	 * @Test public void testfindbyid() { this.add();
	 * Assert.assertNotNull(authUserRoleService.findById(MyTestConstant.ID));
	 * 
	 * }
	 */
	@Test
	public void testfindbyRoleid() {
		this.add();
		// System.out.println(this.authUserRoleService.findByRoleId(3l));
		Assert.assertNotNull(authUserRoleService
				.findByRoleId(MyTestConstant.ID));

	}

	@Test
	public void testfindall() {
		this.add();
		Assert.assertNotNull(this.authUserRoleService.findAll());
	}

	/*
	 * @Test public void testdeleteByUserId() { this.add();
	 * this.authUserRoleService.deleteByUserId(6l);
	 * 
	 * }
	 */

	public void add() {
		AuthUserRole auUserRole = new AuthUserRole();
		auUserRole.setId(MyTestConstant.ID);
		auUserRole.setRoleId(300l);
		auUserRole.setUserId(300l);
		auUserRole.setVersion(new Date());
		authUserRoleService.add(auUserRole);
	}

}
