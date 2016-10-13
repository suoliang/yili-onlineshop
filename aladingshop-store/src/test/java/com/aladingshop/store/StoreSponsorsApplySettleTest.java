package com.aladingshop.store;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.StoreSponsorsApplySettle;
import com.aladingshop.store.service.StoreSponsorsApplySettleService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreSponsorsApplySettleTest {
		
	
	private StoreSponsorsApplySettleService<StoreSponsorsApplySettle> storeSponsorsApplySettleService;
	
	@SuppressWarnings("all")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeSponsorsApplySettleService = (StoreSponsorsApplySettleService) ac.getBean("storeSponsorsApplySettleServiceImpl");
	}
	
	
	@Test
	public void addTest(){
		StoreSponsorsApplySettle storeSponsorsApplySettle = new StoreSponsorsApplySettle();
		
		
		storeSponsorsApplySettle.setApplyNumber("12");
		storeSponsorsApplySettle.setApplyTotalAmount(new BigDecimal(125));
		storeSponsorsApplySettle.setRemark("测试");
		storeSponsorsApplySettle.setSettlementStatus("2");
		storeSponsorsApplySettle.setSettleMethod("银行卡");
		storeSponsorsApplySettle.setUpdateId(256L);
		
		
		storeSponsorsApplySettleService.add(storeSponsorsApplySettle);
		
	}
	
	
	@Test
	public void updateTest(){
		StoreSponsorsApplySettle storeSponsorsApplySettle = new StoreSponsorsApplySettle();		
		
		storeSponsorsApplySettle.setId(1L);
		storeSponsorsApplySettle.setStoreCode("2567812");
		storeSponsorsApplySettle.setApplyNumber("10000");
		storeSponsorsApplySettle.setApplyTotalAmount(new BigDecimal(9527));
		storeSponsorsApplySettle.setRemark("测试gengxin");
		storeSponsorsApplySettle.setSettlementStatus("2");
		storeSponsorsApplySettle.setSettleMethod("银行卡");
		storeSponsorsApplySettle.setUpdateId(256L);
		
		storeSponsorsApplySettleService.update(storeSponsorsApplySettle);
		
	}
}
