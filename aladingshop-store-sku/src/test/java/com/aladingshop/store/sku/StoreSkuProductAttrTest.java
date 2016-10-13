package com.aladingshop.store.sku;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSkuProductAttr;
import com.aladingshop.store.sku.service.StoreSkuProductAttrService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreSkuProductAttrTest {

	private StoreSkuProductAttrService storeSkuProductAttrService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuProductAttrService = (StoreSkuProductAttrService) ac.getBean("storeSkuProductAttrServiceImpl");
	}

	
	public void add() {
		StoreSkuProductAttr storeSkuProductAttr = new StoreSkuProductAttr();
		storeSkuProductAttr.setId(MyTestConstant.ID);
		storeSkuProductAttr.setProductCode(MyTestConstant.ID+"");
		storeSkuProductAttr.setStoreCode(MyTestConstant.ID+"");
		storeSkuProductAttr.setUpdateId(MyTestConstant.ID);
		storeSkuProductAttrService.add(storeSkuProductAttr);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuProductAttrService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCodeAndProductCode() {
		storeSkuProductAttrService.deleteByStoreCodeAndProductCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

	@Test
	public void testUpdate() {
		add();
		StoreSkuProductAttr storeSkuProductAttr = new StoreSkuProductAttr();
		storeSkuProductAttr.setId(MyTestConstant.ID);
		storeSkuProductAttr.setProductCode(MyTestConstant.ID+"");
		storeSkuProductAttr.setStoreCode(MyTestConstant.ID+"");
		storeSkuProductAttr.setUpdateId(MyTestConstant.ID);
		storeSkuProductAttrService.updateById(storeSkuProductAttr);
	}


	
	@Test
	public void testFindById() {
		add();
		storeSkuProductAttrService.findById(MyTestConstant.ID);
		storeSkuProductAttrService.findByStoreCodeAndProductCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

}
