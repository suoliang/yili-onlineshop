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

import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.service.AuthPrivilegeService;
import com.fushionbaby.common.constants.MyTestConstant;
/**
 * @author xu
 * 
 */
public class AuthPrivilegeTest {
	private AuthPrivilegeService<AuthPrivilege> auPrivilegeService;

	@After
	public void testdelete() {
		this.auPrivilegeService.deleteById(MyTestConstant.ID);
	}
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		auPrivilegeService = (AuthPrivilegeService) context.getBean("authPrivilegeServiceImpl");
	}
	String testData  ="fushionbabyTest";
	@Test
	public void testAdd() {
		this.add();
	}
	
	@Test
    public void testupdate() {
		this.add();
		AuthPrivilege auPrivilege=this.auPrivilegeService.findById(MyTestConstant.ID);
		auPrivilege.setLevel(2);
		auPrivilege.setName("lwg");
		auPrivilege.setUrl("http");
		this.auPrivilegeService.update(auPrivilege);
	}

	@Test
    public void testall() {
		Assert.assertNotNull(auPrivilegeService.findAll());
	}
	
	@Test
    public void testfindById() {
		this.add();
		Assert.assertNotNull(auPrivilegeService.findById(MyTestConstant.ID));
	}
	@Test
    public void testfindLevel() {
	
		Assert.assertNotNull(this.auPrivilegeService.findByLevel(1));
	}


	private void add(){
		AuthPrivilege auPrivilege=new AuthPrivilege();
		auPrivilege.setId(MyTestConstant.ID);
		auPrivilege.setName(testData);
		auPrivilege.setSortNo(25);
		auPrivilege.setLevel(1);
		auPrivilege.setUrl("www.baidu.com");
		auPrivilegeService.add(auPrivilege);
		
	}
}
