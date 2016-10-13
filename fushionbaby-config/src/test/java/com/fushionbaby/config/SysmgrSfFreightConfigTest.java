package com.fushionbaby.config;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;

/**
 * 
 * @author cyla
 *
 */
public class SysmgrSfFreightConfigTest {
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sss;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sss = (SysmgrSfFreightConfigService<SysmgrSfFreightConfig>) ac
				.getBean("sysmgrSfFreightConfigServiceImpl");
	}
	
//	@Ignore
//	public void add() {
//		SysmgrSfFreight ss = new SysmgrSfFreight();
//		ss.setId(MyTestConstant.ID);
//		ss.setAmount(new BigDecimal("30.2"));
//		ss.setAreaCode("sh");
//		ss.setCity("sh");
//		sss.add(ss);
//	}
//
//	@Test
//	public void testAdd() {
//		this.add();
//	}
	
	@Test
	public void testFindByAreaCode(){
		SysmgrSfFreightConfig ss = sss.findByAreaCode("sh");
		System.out.println(ss);
	}
}
