package com.aladingshop.store.auth;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.auth.model.StoreAuthUserRole;
import com.aladingshop.store.auth.service.StoreAuthUserRoleService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreAuthUserRoleTest {

	private StoreAuthUserRoleService<StoreAuthUserRole> storeAuthUserRoleService;


	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-store-auth-mybatis.xml" });
		storeAuthUserRoleService = (StoreAuthUserRoleService<StoreAuthUserRole>) context.getBean("storeAuthUserRoleServiceImpl");
	}

	String testname = "aldmtuser";

	@Test
	public void testadd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		this.storeAuthUserRoleService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testfindbyRoleid() {
		this.add();
		Assert.assertNotNull(storeAuthUserRoleService.findByRoleId(MyTestConstant.ID));

	}

	@Test
	public void testfindall() {
		this.add();
		Assert.assertNotNull(this.storeAuthUserRoleService.findAll());
	}



	public void add() {
		StoreAuthUserRole auUserRole = new StoreAuthUserRole();
		auUserRole.setId(MyTestConstant.ID);
		auUserRole.setRoleId(300l);
		auUserRole.setUserId(300l);
		auUserRole.setUpdateTime(new Date());
		storeAuthUserRoleService.add(auUserRole);
	}

}
