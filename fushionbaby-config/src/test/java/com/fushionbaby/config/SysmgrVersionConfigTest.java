package com.fushionbaby.config;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrVersionConfig;
import com.fushionbaby.config.service.SysmgrVersionConfigService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysmgrVersionConfigTest {

	private SysmgrVersionConfigService sysVersionService;

	@After
	public void testDeleteById() {
		sysVersionService.deleteById(MyTestConstant.ID);

	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sysVersionService = (SysmgrVersionConfigService) ac
				.getBean("sysmgrVersionConfigServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		SysmgrVersionConfig sysVersion = (SysmgrVersionConfig) sysVersionService.findById(MyTestConstant.ID);
		sysVersion.setName("android v5.5");
		sysVersion.setVersionNum(5);
		sysVersion.setSourceCode("3");
		sysVersion.setCreateTime(new Date());
		sysVersion.setUpdateTime(new Date());
		sysVersionService.update(sysVersion);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sysVersionService.findById(MyTestConstant.ID));
	}

	@Test
	public void testgetlistpage() {
		this.add();
		BasePagination page = new BasePagination();
		Assert.assertNotNull(this.sysVersionService.getListPage(page));

	}

	@Test
	public void testCheckIsNeedUpdateOrNot(){
		add();
		SysmgrVersionConfig sysVersion = new SysmgrVersionConfig();
		sysVersion.setVersionNum(2);
		List list = sysVersionService.checkIsNeedUpdateOrNot(sysVersion);
		System.out.println(list);
	}
	
	@Test
	public void testGetLatestVersionNum(){
		add();
		System.out.println(sysVersionService.getLatestVersionNum("1"));
	}
	
	private void add() {
		SysmgrVersionConfig sysVersion = new SysmgrVersionConfig();
		sysVersion.setId(MyTestConstant.ID);
		sysVersion.setCreateTime(new Date());
		sysVersion.setUpdateTime(new Date());
		sysVersionService.add(sysVersion);
	}

}
