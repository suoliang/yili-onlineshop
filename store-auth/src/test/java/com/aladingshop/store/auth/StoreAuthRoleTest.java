/**
 * 
 */
package com.aladingshop.store.auth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.auth.model.StoreAuthRole;
import com.aladingshop.store.auth.service.StoreAuthRoleService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.RandomNumUtil;

/**
 * @author xu
 */
public class StoreAuthRoleTest {

	private StoreAuthRoleService<StoreAuthRole> storeAuRoleService;

	@After
	public void testdeletebyid() {
		storeAuRoleService.deleteById(MyTestConstant.ID);
	}

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-auth-mybatis.xml" });
		storeAuRoleService = (StoreAuthRoleService<StoreAuthRole>) context.getBean("storeAuthRoleServiceImpl");
	}

	String rolename = "testrole";

	@Test
	public void testadd() {
		this.add();
	}

	@Test
	public void testdupdate() {
		this.add();
		StoreAuthRole auRole2 = this.storeAuRoleService.findById(MyTestConstant.ID);
		auRole2.setName("lwg");
		auRole2.setDescription("http");
		this.storeAuRoleService.update(auRole2);
	}

	@Test
	public void testfindbyid() {
		this.add();
		Assert.assertNotNull(storeAuRoleService.findById(MyTestConstant.ID));
	}



//	@Test
//	public void testfindall() {
//		this.add();
//		Assert.assertNotNull(storeAuRoleService.findAll());
//	}

	private void add() {
		StoreAuthRole auRole = new StoreAuthRole();
		auRole.setId(MyTestConstant.ID);
		auRole.setDescription("123456");
		auRole.setName(rolename);
		auRole.setStoreCode(RandomNumUtil.getCharacterAndNumber(10));
		this.storeAuRoleService.add(auRole);
	}
}
