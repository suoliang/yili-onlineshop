/**
 * 
 */
package com.fushionbaby.auth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.auth.service.AuthRoleService;
import com.fushionbaby.common.constants.MyTestConstant;

/**
 * @author xu
 */
public class AuthRoleTest {

	private AuthRoleService<AuthRole> auRoleService;

	@After
	public void testdeletebyid() {
		auRoleService.deleteById(MyTestConstant.ID);
	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-auth-mybatis.xml" });
		auRoleService = (AuthRoleService) context
				.getBean("authRoleServiceImpl");
	}

	String rolename = "testrole";

	@Test
	public void testadd() {
		this.add();
	}

	@Test
	public void testdupdate() {
		this.add();
		AuthRole auRole2 = this.auRoleService.findById(MyTestConstant.ID);
		auRole2.setName("lwg");
		auRole2.setDescription("http");
		this.auRoleService.update(auRole2);
	}

	@Test
	public void testfindbyid() {
		this.add();
		Assert.assertNotNull(auRoleService.findById(MyTestConstant.ID));
	}

	@Test
	public void testfindbyname() {
		this.add();
		Assert.assertNotNull(auRoleService.findByName(this.rolename));
	}

	@Test
	public void testfindall() {
		this.add();
		Assert.assertNotNull(auRoleService.findAll());
	}

	private void add() {
		AuthRole auRole = new AuthRole();
		auRole.setId(MyTestConstant.ID);
		auRole.setDescription("123456");
		auRole.setName(rolename);
		this.auRoleService.add(auRole);
	}
}
