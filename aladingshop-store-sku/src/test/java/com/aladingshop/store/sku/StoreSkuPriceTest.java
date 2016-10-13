package com.aladingshop.store.sku;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSkuPrice;
import com.aladingshop.store.sku.service.StoreSkuPriceService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreSkuPriceTest {

	private StoreSkuPriceService storeSkuPriceService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuPriceService = (StoreSkuPriceService) ac.getBean("storeSkuPriceServiceImpl");
	}

	
	public void add() {
		StoreSkuPrice storeSkuPrice = new StoreSkuPrice();
		storeSkuPrice.setId(MyTestConstant.ID);
		storeSkuPrice.setSkuCode(MyTestConstant.ID+"");
		storeSkuPrice.setStoreCode(MyTestConstant.ID+"");
		storeSkuPrice.setUpdateId(MyTestConstant.ID);
		storeSkuPrice.setAladingPrice(new BigDecimal(1));
		storeSkuPrice.setCostPrice(new BigDecimal(1));
		storeSkuPrice.setCurrentPrice(new BigDecimal(1));
		storeSkuPrice.setMarketPrice(new BigDecimal(1));
		storeSkuPrice.setRetailPrice(new BigDecimal(1));
		storeSkuPriceService.add(storeSkuPrice);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuPriceService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCodeAndSkuCode() {
		storeSkuPriceService.deleteByStoreCodeAndSkuCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

	@Test
	public void testFindByStoreCodeAndSkuCode() {
		add();
		storeSkuPriceService.findByStoreCodeAndSkuCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}
	
	@Test
	public void testUpdate() {
		add();
		StoreSkuPrice storeSkuPrice = new StoreSkuPrice();
		storeSkuPrice.setId(MyTestConstant.ID);
		storeSkuPrice.setSkuCode(MyTestConstant.ID+"");
		storeSkuPrice.setStoreCode(MyTestConstant.ID+"");
		storeSkuPrice.setUpdateId(MyTestConstant.ID);
		storeSkuPrice.setAladingPrice(new BigDecimal(1));
		storeSkuPrice.setCostPrice(new BigDecimal(1));
		storeSkuPrice.setCurrentPrice(new BigDecimal(1));
		storeSkuPrice.setMarketPrice(new BigDecimal(1));
		storeSkuPrice.setRetailPrice(new BigDecimal(1));
		storeSkuPriceService.updateById(storeSkuPrice);
		storeSkuPriceService.updateByStoreCodeAndSkuCode(storeSkuPrice);
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		storeSkuPriceService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		storeSkuPriceService.findById(MyTestConstant.ID);
	}

}
