package com.fushionbaby.config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;

public class SysmgrAdvertisementConfigTest {

	private SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> sacs;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sacs = (SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig>) context
				.getBean("sysmgrAdvertisementConfigServiceImpl");
	}

	public void add() {
		SysmgrAdvertisementConfig sac = new SysmgrAdvertisementConfig();
		sac.setId(MyTestConstant.ID);
		sac.setIsUse("y");
		sac.setSourceCode("55");
		sac.setAdCode("15456");
		/*
		 * sac.setPictureHeight(150); sac.setPictureWeight(220);
		 */
		sacs.add(sac);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		sacs.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SysmgrAdvertisementConfig sac = sacs.findById(MyTestConstant.ID);
		sac.setAdCode("12545");
		sac.setSourceCode("55");
		sac.setRemark("12345545");
		sacs.update(sac);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sacs.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sacs.findAll());
	}

	@Test
	public void testlistpage() {
		this.add();
		BasePagination page = new BasePagination();
		Assert.assertNotNull(sacs.getListPage(page));
	}

	@Test
	public void testFindByCode() {
		this.add();
		Assert.assertNotNull(sacs.findByAdCode("web_will"));
		System.out.println(sacs.findByAdCode("web_will").getAdCode());
	}

}
