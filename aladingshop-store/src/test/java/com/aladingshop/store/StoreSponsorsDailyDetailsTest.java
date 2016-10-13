package com.aladingshop.store;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.deser.ValueInstantiators.Base;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreSponsorsDailyDetailsService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreSponsorsDailyDetailsTest {
	
	private StoreSponsorsDailyDetailsService<StoreSponsorsDailyDetails> storeSponsorsDailyDetailsService;
	
	@SuppressWarnings("all")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeSponsorsDailyDetailsService = (StoreSponsorsDailyDetailsService) ac.getBean("storeSponsorsDailyDetailsServiceImpl");
	}
	
	
	@Test
	public void addTest(){
		StoreSponsorsDailyDetails storeSponsorsDailyDetails = new StoreSponsorsDailyDetails();
		
		storeSponsorsDailyDetails.setApplyNumber("12566");
		storeSponsorsDailyDetails.setDailyNumber("15");
		storeSponsorsDailyDetails.setPlatformFee(new BigDecimal(1));
		storeSponsorsDailyDetails.setRealIncomeAmount(new BigDecimal(25));
		storeSponsorsDailyDetails.setRemark("测试");
		storeSponsorsDailyDetails.setSettlementStatus("2");
		storeSponsorsDailyDetails.setStoreCode("568914");
		storeSponsorsDailyDetails.setSettleOrderCount(2);
		storeSponsorsDailyDetails.setSettleOrderAmount(new BigDecimal(26));
		
		storeSponsorsDailyDetailsService.add(storeSponsorsDailyDetails);
		
	}
	
	@Test
	public void updateTest1(){

		BasePagination page=new BasePagination();
		Map<String,String> params=new HashMap<String, String>();
		params.put("storeCode","e8Hy3p2Q3q66hD92400R" );
		params.put("settlementStatus", "3");
		page.setParams(params);
		System.out.println(storeSponsorsDailyDetailsService.getListPage(page).getTotal());
		
	}
	
	@Test
	public void updateTest(){
		StoreSponsorsDailyDetails storeSponsorsDailyDetails = new StoreSponsorsDailyDetails();
		
		storeSponsorsDailyDetails.setId(1L);
		storeSponsorsDailyDetails.setApplyNumber("12566");
		storeSponsorsDailyDetails.setDailyNumber("15");
		storeSponsorsDailyDetails.setPlatformFee(new BigDecimal(15));
		storeSponsorsDailyDetails.setRealIncomeAmount(new BigDecimal(2000));
		storeSponsorsDailyDetails.setRemark("测试256");
		storeSponsorsDailyDetails.setSettlementStatus("2");
		storeSponsorsDailyDetails.setStoreCode("kandychen");
		storeSponsorsDailyDetails.setSettleOrderCount(2);
		storeSponsorsDailyDetails.setSettleOrderAmount(new BigDecimal(2015));
		
		storeSponsorsDailyDetailsService.update(storeSponsorsDailyDetails);
		
	}
}
