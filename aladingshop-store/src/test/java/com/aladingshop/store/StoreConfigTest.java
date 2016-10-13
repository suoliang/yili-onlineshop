package com.aladingshop.store;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.StoreConfig;
import com.aladingshop.store.service.StoreConfigService;
import com.fushionbaby.common.constants.MyTestConstant;
/***
 * 门店配置测试
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年1月12日下午5:16:24
 */
public class StoreConfigTest {

	private StoreConfigService<StoreConfig> storeConfigService;

	@SuppressWarnings({ "resource", "unchecked" })
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeConfigService = (StoreConfigService<StoreConfig>) ac.getBean("storeConfigServiceImpl");
	}

	
	public void add() {
		StoreConfig storeConfig = new StoreConfig();
		storeConfig.setId(MyTestConstant.ID);
		storeConfig.setBusinessEndTime("8:00:00");
		storeConfig.setBusinessStartTime("20:00:00");
		storeConfig.setFreeFreightAmount(0);
		storeConfig.setFreightAmount(0);
		storeConfig.setStoreCode("123456789");
		storeConfigService.add(storeConfig);
	}

	@Test
	public void testAdd() {
		add();
	}



}
