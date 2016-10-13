package com.fushionbaby.sysactivity;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.SysActivityFamily;
import com.fushionbaby.sysactivity.service.SysActivityFamilyService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityFamilyTest {

	private SysActivityFamilyService sysActivityFamilyService;
	private SysActivityFamily sysActivityFamily;

	@After
	public void testDeleteById() {
		sysActivityFamilyService.deleteById(MyTestConstant.ID);
	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysactivity-mybatis.xml" });
		sysActivityFamilyService = (SysActivityFamilyService) ac
				.getBean("sysActivityFamilyServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		SysActivityFamily sysActivityFamily = new SysActivityFamily();
		sysActivityFamily.setId(MyTestConstant.ID);


		sysActivityFamily.setTitle("国庆了1");
		sysActivityFamily.setPublishTime(new Date());
		sysActivityFamilyService.update(sysActivityFamily);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sysActivityFamilyService.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sysActivityFamilyService.findAll().size());
	}

	/*
	 * @Test public void testFindByFamilyType() { this.add();
	 * Assert.assertNotNull(this.sysActivityFamilyService.findByFamilyType(1));
	 * }
	 */

	@Test
	public void testlistpageone() {
		this.add();
		BasePagination page = new BasePagination();
		Assert.assertNotNull(this.sysActivityFamilyService.getListPage(page));

	}



	public void add() {
		SysActivityFamily sysActivityFamily = new SysActivityFamily();
		sysActivityFamily.setId(MyTestConstant.ID);

		sysActivityFamily.setTitle("国庆了3");
		sysActivityFamilyService.add(sysActivityFamily);
	}
}
