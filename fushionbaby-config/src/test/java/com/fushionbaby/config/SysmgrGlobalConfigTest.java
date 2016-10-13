package com.fushionbaby.config;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;

	/**
	*
	* @author cyla
	* 
	*/
public class SysmgrGlobalConfigTest {
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	
	@After
	public void testDeleteById() {
		sysmgrGlobalConfigService.deleteSysmgrGlobalConfigById(MyTestConstant.ID);
	}
	@Before
	public void before() {
	@SuppressWarnings("resource")
	ApplicationContext ac = new ClassPathXmlApplicationContext(
			new String[] { MyTestConstant.TEST_SPRING_CONFIG,
					"classpath:conf/spring-config-mybatis.xml" });
	sysmgrGlobalConfigService = (SysmgrGlobalConfigService) ac
			.getBean(SysmgrGlobalConfigService.class);
}
	
	@Test
	public void testAdd() {
		this.add();
	}
	@Test
	public void testUpdate() {
		this.add();
		SysmgrGlobalConfig sysmgrGlobalConfig = new SysmgrGlobalConfig();
		sysmgrGlobalConfig.setCode("11");
		sysmgrGlobalConfig.setCreateTime(new Date());
		sysmgrGlobalConfig.setId(MyTestConstant.ID);
		sysmgrGlobalConfig.setName("hao");
		sysmgrGlobalConfig.setRemark("22");
		sysmgrGlobalConfig.setUpdateTime(new Date());
		sysmgrGlobalConfig.setValue("33");
		sysmgrGlobalConfigService.update(sysmgrGlobalConfig);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sysmgrGlobalConfigService.getSysmgrGlobalConfigById(MyTestConstant.ID));
	}
	@Test
	public void testFindByCode(){
		SysmgrGlobalConfig sysmgrGlobalConfig = sysmgrGlobalConfigService.findByCode(GlobalConfigConstant.FREE_FREIGHT_CODE);
		String a = sysmgrGlobalConfig.getValue();
		Assert.assertNotNull(a);
	}
	private void add() {
		SysmgrGlobalConfig sysmgrGlobalConfig = new SysmgrGlobalConfig();
		sysmgrGlobalConfig.setCode("11");
		sysmgrGlobalConfig.setCreateTime(new Date());
		sysmgrGlobalConfig.setId(MyTestConstant.ID);
		sysmgrGlobalConfig.setName("hao");
		sysmgrGlobalConfig.setRemark("22");
		sysmgrGlobalConfig.setUpdateTime(new Date());
		sysmgrGlobalConfig.setValue("33");
		sysmgrGlobalConfigService.add(sysmgrGlobalConfig);
	}

	
	}

