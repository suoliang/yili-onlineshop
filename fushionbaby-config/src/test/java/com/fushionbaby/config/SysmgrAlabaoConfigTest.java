package com.fushionbaby.config;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.config.model.SysmgrAlabaoConfig;
import com.fushionbaby.config.service.SysmgrAlabaoConfigService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

public class SysmgrAlabaoConfigTest {

	private SysmgrAlabaoConfigService<SysmgrAlabaoConfig> alabaoConfigService;
	
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		alabaoConfigService = ac.getBean(SysmgrAlabaoConfigService.class);
		
	}
	
	@Test
	public void testFindByMinMaxValue() {
		SysmgrAlabaoConfig alabaoConfig = alabaoConfigService.findByMinMaxValue("200.01");
		System.out.println(alabaoConfig.getDiscountValue());
	}

}
