package com.fushionbaby.config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysmgrSourceConfigTest {

	private SysmgrSourceConfigService<SysmgrSourceConfig> sss;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sss = (SysmgrSourceConfigService<SysmgrSourceConfig>) ac
				.getBean("sysmgrSourceConfigServiceImpl");
	}

	@Ignore
	public void add() {
		SysmgrSourceConfig ss = new SysmgrSourceConfig();
		ss.setId(MyTestConstant.ID);
		ss.setCode("2");
		ss.setName("促销信息");
		sss.add(ss);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		sss.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SysmgrSourceConfig ss = sss.findById(MyTestConstant.ID);
		ss.setCode("1");
		ss.setName("购买成功");
		sss.update(ss);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sss.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sss.findAll());
	}

	/*
	 * @Test public void testfindByCode() { this.add();
	 * System.out.println(this.sss.findByCode("3")); }
	 */

}
