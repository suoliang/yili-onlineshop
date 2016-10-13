/**
 * 
 */
package com.aladingshop.store.auth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.service.StoreAuthPrivilegeService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.RandomNumUtil;
/**
 * @author xupeijun
 * 
 */
public class StoreAuthPrivilegeTest {
	
	@Autowired
	private StoreAuthPrivilegeService<StoreAuthPrivilege> storeAuthPrivilegeService;

	
	@After
	public void testdelete() {
		this.storeAuthPrivilegeService.deleteById(MyTestConstant.ID);
	}
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-store-auth-mybatis.xml"});
		storeAuthPrivilegeService = (StoreAuthPrivilegeService<StoreAuthPrivilege>) context.getBean("storeAuthPrivilegeServiceImpl");
	}
	String testData  ="aldmtTest";
	@Test
	public void testAdd() {
		this.add();
	}
	
	@Test
    public void testupdate() {
		this.add();
		StoreAuthPrivilege auPrivilege=this.storeAuthPrivilegeService.findById(MyTestConstant.ID);
		auPrivilege.setLevel(2);
		auPrivilege.setName("lwg");
		auPrivilege.setUrl("http");
		this.storeAuthPrivilegeService.update(auPrivilege);
	}

	@Test
    public void testall() {
		Assert.assertNotNull(storeAuthPrivilegeService.findAll());
	}
	
	@Test
    public void testfindById() {
		this.add();
		Assert.assertNotNull(storeAuthPrivilegeService.findById(MyTestConstant.ID));
	}
	@Test
    public void testfindLevel() {
	
		Assert.assertNotNull(this.storeAuthPrivilegeService.findByLevel(1));
	}


	private void add(){
		StoreAuthPrivilege auPrivilege=new StoreAuthPrivilege();
		auPrivilege.setId(MyTestConstant.ID);
		auPrivilege.setStoreCode(RandomNumUtil.getCharacterAndNumber(10));
		auPrivilege.setName(testData);
		auPrivilege.setSortNo(25);
		auPrivilege.setLevel(1);
		auPrivilege.setUrl("www.baidu.com");
		storeAuthPrivilegeService.add(auPrivilege);
		
	}
}
