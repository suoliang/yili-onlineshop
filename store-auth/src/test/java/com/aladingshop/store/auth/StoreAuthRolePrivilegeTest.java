package com.aladingshop.store.auth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.auth.model.StoreAuthRolePrivilege;
import com.aladingshop.store.auth.service.StoreAuthRolePrivilegeService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreAuthRolePrivilegeTest {

	private StoreAuthRolePrivilegeService<StoreAuthRolePrivilege> storeAuthRolePrivilegeService;

	@After
	public void testdeletebyid() {
		storeAuthRolePrivilegeService.deleteById(MyTestConstant.ID);
	}

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-auth-mybatis.xml" });
		storeAuthRolePrivilegeService = (StoreAuthRolePrivilegeService<StoreAuthRolePrivilege>) context
				.getBean("storeAuthRolePrivilegeServiceImpl");
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
		Assert.assertNotNull(storeAuthRolePrivilegeService.findByRoleId(MyTestConstant.ID));
	}

	@Test
	public void testfindall() {
		this.add();
		Assert.assertNotNull(this.storeAuthRolePrivilegeService.findAll());
	}

	public void add() {
		StoreAuthRolePrivilege auRolePrivilege = new StoreAuthRolePrivilege();
		auRolePrivilege.setId(MyTestConstant.ID);
		auRolePrivilege.setRoleId(MyTestConstant.ID);
		auRolePrivilege.setPrivilegeId(3l);
		storeAuthRolePrivilegeService.add(auRolePrivilege);
	}

}
