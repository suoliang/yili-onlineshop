package com.fushionbaby.config;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;

public class SysmgrDictConfigTest {

	private SysmgrDictConfigService<SysmgrDictConfig> sdcs;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sdcs = (SysmgrDictConfigService<SysmgrDictConfig>) context
				.getBean("sysmgrDictConfigServiceImpl");
	}

	public void add() {
		SysmgrDictConfig sdc = new SysmgrDictConfig();
		sdc.setId(MyTestConstant.ID);
		sdc.setDescription("d");
		sdc.setLabel("d");
		sdc.setSort(1);
		sdc.setType("d");
		sdc.setValue("d");
		sdcs.add(sdc);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		sdcs.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SysmgrDictConfig sdc = sdcs.findById(MyTestConstant.ID);
		sdc.setDescription("d2");
		sdc.setLabel("d2");
		sdc.setSort(2);
		sdc.setType("d2");
		sdc.setValue("d2");
		sdcs.update(sdc);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sdcs.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindByLabelValueType() {
		this.add();
		Assert.assertNotNull(sdcs.findByLabelValueType("d", "d", "d"));
		System.out.println(sdcs.findByLabelValueType("d", "d", "d").get(0).getLabel());
	}

	@Test
	public void testFindByListPage() {
		this.add();
		BasePagination page=new BasePagination();
		Assert.assertNotNull(sdcs.getListPage(page));
		System.out.println(sdcs.getListPage(page).getResult().size());
	}

}
