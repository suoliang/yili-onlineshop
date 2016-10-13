package com.aladingshop.store;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreSponsorsSettlementDetailsService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreSponsorsSettlementDetailsTest {
	
	private StoreSponsorsSettlementDetailsService<StoreSponsorsSettlementDetails> storeSponsorsSettlementDetailsService;
	
	@SuppressWarnings("all")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeSponsorsSettlementDetailsService = (StoreSponsorsSettlementDetailsService) ac.getBean("storeSponsorsSettlementDetailsServiceImpl");
	}
	
	
	@Test
	public void findBystoreCodeTest(){
		List<StoreSponsorsSettlementDetails> list =storeSponsorsSettlementDetailsService.findByStoreCode("12553");
		for (StoreSponsorsSettlementDetails storeSponsorsSettlementDetails : list) {
			System.out.println(storeSponsorsSettlementDetails);
		}
	}
	
	@Test
	public void addTest(){
		StoreSponsorsSettlementDetails storeSponsorsSettlementDetails = new StoreSponsorsSettlementDetails();
		
		storeSponsorsSettlementDetails.setStoreCode("125555");
		storeSponsorsSettlementDetails.setOrderCode("kjdskd2566");
		storeSponsorsSettlementDetails.setSettleAmount(new BigDecimal(157));
		storeSponsorsSettlementDetails.setPlatformFee(new BigDecimal(1));
		storeSponsorsSettlementDetails.setRealIncomeAmount(new BigDecimal(156));
		
		storeSponsorsSettlementDetailsService.add(storeSponsorsSettlementDetails);
		
	}
	
	
	@Test
	public void updateTest(){
		StoreSponsorsSettlementDetails storeSponsorsSettlementDetails = new StoreSponsorsSettlementDetails();
		
		storeSponsorsSettlementDetails.setId(2L);
		storeSponsorsSettlementDetails.setStoreCode("125555");
		storeSponsorsSettlementDetails.setOrderCode("kjJames");
		storeSponsorsSettlementDetails.setSettleAmount(new BigDecimal(158));
		storeSponsorsSettlementDetails.setPlatformFee(new BigDecimal(1));
		storeSponsorsSettlementDetails.setRealIncomeAmount(new BigDecimal(157));
		
		storeSponsorsSettlementDetailsService.update(storeSponsorsSettlementDetails);
		
	}
}
