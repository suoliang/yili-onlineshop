package com.fushionbaby.config;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.config.model.SysmgrImportanceConfig;
//import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

/**
 * 
 * @author cyla
 * 
 */
public class SysmgrImportanceConfigTest {

	private SysmgrImportanceConfigService sysmgrImportanceConfigService;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sysmgrImportanceConfigService = ac
				.getBean(SysmgrImportanceConfigService.class);
		
	}

	@Test
	public void testFindByCode() {
		SysmgrImportanceConfig sysmgrImportanceConfig = sysmgrImportanceConfigService
				.findByCode("JSDZ_NOTIFY_URL");
		String a = sysmgrImportanceConfig.getValue();
		System.out.println(a);
	}
	@Test
	public void testFindByCode2() {
		SysmgrImportanceConfig sysmgrImportanceConfig = sysmgrImportanceConfigService
				.findByCode("JSDZ_NOTIFY_URL");
		String a = sysmgrImportanceConfig.getValue();
		 Integer  SMS_COMPANY_FLAG= Integer.valueOf(sysmgrImportanceConfigService.findByCode("SMS_COMPANY_FLAG").getValue());
		System.out.println(SMS_COMPANY_FLAG);
		 System.out.println(a);
	}

}
