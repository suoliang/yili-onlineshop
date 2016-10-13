package com.aladingshop.store.sku;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSkuImage;
import com.aladingshop.store.sku.service.StoreSkuImageService;
import com.fushionbaby.common.constants.MyTestConstant;

public class StoreSkuImageTest {

	private StoreSkuImageService storeSkuImageService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuImageService = (StoreSkuImageService) ac.getBean("storeSkuImageServiceImpl");
	}

	
	public void add() {
		StoreSkuImage storeSkuImage = new StoreSkuImage();
		storeSkuImage.setId(MyTestConstant.ID);
		storeSkuImage.setSkuCode(MyTestConstant.ID+"");
		storeSkuImage.setStoreCode(MyTestConstant.ID+"");
		storeSkuImage.setUpdateId(MyTestConstant.ID);
		storeSkuImageService.add(storeSkuImage);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuImageService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCodeAndSkuCode() {
		storeSkuImageService.deleteByStoreCodeAndSkuCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

	@Test
	public void testUpdate() {
		add();
		StoreSkuImage storeSkuImage = new StoreSkuImage();
		storeSkuImage.setId(MyTestConstant.ID);
		storeSkuImage.setSkuCode(MyTestConstant.ID+"");
		storeSkuImage.setStoreCode(MyTestConstant.ID+"");
		storeSkuImage.setUpdateId(MyTestConstant.ID);
		storeSkuImageService.updateById(storeSkuImage);
	}


	
	@Test
	public void testFindById() {
		add();
		storeSkuImageService.findById(MyTestConstant.ID);
	}

}
