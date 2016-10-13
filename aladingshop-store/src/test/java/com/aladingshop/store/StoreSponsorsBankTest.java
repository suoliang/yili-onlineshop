package com.aladingshop.store;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.StoreSponsorsBank;
import com.aladingshop.store.service.StoreSponsorsBankService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreSponsorsBankTest {
	
	private StoreSponsorsBankService ssbs;

	@SuppressWarnings("all")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		 ssbs = (StoreSponsorsBankService) ac.getBean("storeSponsorsBankServiceImpl");
	}

	
	@Test
	public void add(){
		StoreSponsorsBank ssb = new StoreSponsorsBank();
		
		ssb.setBankName("中国银行");
		ssb.setCardHolder("zhansan");
		ssb.setStoreCode("111");
		ssb.setBankBranchName("浦江支行");
		ssb.setCardNo("62220355477465444");
		ssb.setIsValidate("1");
		ssb.setRemark("打款");
		ssb.setUpdateId(25666L);
		
		ssbs.add(ssb);
	}
	
	@Test
	public void update(){
		StoreSponsorsBank ssb = new StoreSponsorsBank();
		
		ssb.setBankName("中国银行1111");
		ssb.setCardHolder("zhansan2222");
		ssb.setStoreCode("1112222");
		ssb.setBankBranchName("浦江支行2222");
		ssb.setCardNo("62220355477465444");
		ssb.setIsValidate("3");
		ssb.setRemark("打款222");
		ssb.setUpdateId(256L);
		
		
		ssb.setId(1L);
		
		ssbs.update(ssb);
	}
	
}
