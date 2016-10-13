package com.aladingshop.store.auth;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.RandomNumUtil;

/**
 * @author xupeijun
 * @version
 */
public class StoreAuthUserTest {
	private StoreAuthUserService<StoreAuthUser> storeAuUserService;
	@After
	public void testdeletebyid() {
		storeAuUserService.deleteById(MyTestConstant.ID);
	}
	@SuppressWarnings("unchecked")
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-store-auth-mybatis.xml"});
		storeAuUserService =(StoreAuthUserService<StoreAuthUser>) context.getBean("storeAuthUserServiceImpl");
	}
	String testname="fushionuser";
	@Test
	public void testadd() {
		this.add();
	}
	@Test
	public void testupdate() {
		this.add();
		StoreAuthUser auUser = (StoreAuthUser) storeAuUserService.findById(MyTestConstant.ID);
		auUser.setEmail("sdgsdg");
		storeAuUserService.update(auUser);
	}
	@Test
	public void testfindbyid() {
		this.add();
		System.out.println(this.storeAuUserService.findById(MyTestConstant.ID));
	Assert.assertNotNull(storeAuUserService.findById(MyTestConstant.ID));
		
	}
	@Test
	public void testfindall() {
	
		Assert.assertNotNull(this.storeAuUserService.findAll());
	}
	@Test
	public void testisSystemUser() {
		this.add();
		this.storeAuUserService.isSystemUser(MyTestConstant.ID);
	}
	
	public void add(){
		StoreAuthUser auUser=new StoreAuthUser();
		auUser.setId(MyTestConstant.ID);
		auUser.setStoreCode(RandomNumUtil.getCharacterAndNumber(10));
		auUser.setName(testname);
		auUser.setPassword("user123");
		auUser.setEmail("123456@qq.com");
		auUser.setPhone("12345678900");
		auUser.setUserType(2);
		storeAuUserService.add(auUser);
	}
	
}
