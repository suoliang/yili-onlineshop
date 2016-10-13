package com.fushionbaby.auth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.auth.model.AuthRolePrivilege;
import com.fushionbaby.auth.service.AuthRolePrivilegeService;
import com.fushionbaby.common.constants.MyTestConstant;

public class AuthRolePrivilegeTest {

	private AuthRolePrivilegeService<AuthRolePrivilege> authRolePrivilegeService;

	@After
	public void testdeletebyid() {
		authRolePrivilegeService.deleteById(MyTestConstant.ID);
	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-auth-mybatis.xml" });
		authRolePrivilegeService = (AuthRolePrivilegeService) context
				.getBean("authRolePrivilegeServiceImpl");
	}

	String testname = "fushionuser";

	@Test
	public void testadd() {
		this.add();
	}

	@Test
	public void testfindbyRoleid() {
		this.add();
		// System.out.println(this.authRolePrivilegeService.findByRoleId(3l).size());
		Assert.assertNotNull(authRolePrivilegeService
				.findByRoleId(MyTestConstant.ID));
	}

	@Test
	public void testfindall() {
		Assert.assertNotNull(this.authRolePrivilegeService.findAll());
	}

	public void add() {
		AuthRolePrivilege auRolePrivilege = new AuthRolePrivilege();
		auRolePrivilege.setId(MyTestConstant.ID);
		auRolePrivilege.setRoleId(3l);
		auRolePrivilege.setPrivilegeId(3l);
		authRolePrivilegeService.add(auRolePrivilege);
	}

}
